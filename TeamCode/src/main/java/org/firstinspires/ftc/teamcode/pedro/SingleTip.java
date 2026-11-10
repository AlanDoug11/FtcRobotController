package org.firstinspires.ftc.teamcode.pedro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous

public class SingleTip extends OpMode{

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    //POSES
    private final Pose startPose = p.of(60, 8.2, 90);
    private final Pose shootClose = p.of(60, 30, 90);
    private final Pose gardenCurve = p.of(7.48, 28.51, 270);
    private final Pose garden = p.of(11.48, 13.4, 270);
    private final Pose park = p.of(15.57, 86.85, 0);
    private final Pose parkCurve = p.of(23.84, 19.29, 0);


    private DcMotor intake1;
    private DcMotor intake2;
    private DcMotorEx flywheelPollen;
    private double encoderCPM = 28;
    private double kV = 0.000170, kS = 0.04348, kP = 0.002;
    private double gearRatio = 1;
    private double goalRPM = 0;
    public double pollenFlywheelSpeed = 0;
    double feedForward = 0;
    double feedBack = 0;
    double error = 0;
    private Servo pollenSwitch;


    //PATHS
    private Path moveStartShoot() {
        return line(startPose, shootClose).linear(startPose, shootClose);
    }
    private Path moveShootGarden() {
        return curve(shootClose, gardenCurve, garden).linear(shootClose, garden);
    }
    private Path moveGardenShoot() {
        return curve(garden, gardenCurve, shootClose).linear(garden, shootClose);
    }
    private Path moveShootPark() {
        return curve(shootClose, parkCurve, park).linear(shootClose, park);
    }


    private Command autoRoutine() {
        return sequential(
                follow(follower, moveStartShoot()),
                startShooter(),
                waitMs(50),
                intakeOn1(),
                intakeOn2(),
                pollenSwitchOn(),
                waitMs(1500),
                pollenSwitchOff(),
                intakeOff1(),
                intakeOff2(),
                stopShooter(),
                follow(follower, moveShootGarden()),
                intakeOn1(),
                intakeOn2(),
                waitMs(1000),
                follow(follower, moveGardenShoot()),
                startShooter(),
                waitMs(50),
                pollenSwitchOn(),
                waitMs(3000),
                pollenSwitchOff(),
                intakeOff1(),
                intakeOff2(),
                stopShooter(),
                follow(follower, moveShootPark())
        );
    }
    public Command pollenSwitchOn() {
        return instant(() -> pollenSwitch.setPosition((1*0.33)+0.54));
    }
    public Command intakeOn1() {
        return Command.build()
                // Called once when the command begins
                .setStart(() -> intake1.setPower(1))

                .setDone(() -> {
                    return Math.abs(goalRPM - ((flywheelPollen.getVelocity()/encoderCPM)* 60) / gearRatio) < 50; // Tolerance of 50 ticks
                })
                .requiring(this);
    }
    public Command intakeOn2() {
        return instant(() -> intake2.setPower(1));
    }
    public Command intakeOff1() {
        return instant(() -> intake1.setPower(0));
    }
    public Command intakeOff2() {
        return instant(() -> intake2.setPower(0));
    }
    public Command pollenSwitchOff() {
        return instant(() -> pollenSwitch.setPosition((0*0.33)+0.54));
    }
    public Command startShooter() {
        return Command.build()
                // Called once when the command begins
                .setStart(() -> pollenFlywheelSpeed = 3200)

                .setDone(() -> {
                    return Math.abs(goalRPM - ((flywheelPollen.getVelocity()/encoderCPM)* 60) / gearRatio) < 50; // Tolerance of 50 ticks
                })
                .requiring(this);
    }
    public Command stopShooter() {
        return instant(() -> pollenFlywheelSpeed = 0);
    }

    public void update() {
            feedForward = (kV * goalRPM)+kS;
            error = goalRPM - ((flywheelPollen.getVelocity()/encoderCPM)* 60) / gearRatio;
            feedBack = error * kP;

            goalRPM = pollenFlywheelSpeed;
            if (goalRPM==0){flywheelPollen.setPower(0);}
            else {flywheelPollen.setPower(feedForward+feedBack);}

    }


    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();

        intake1 = hardwareMap.get(DcMotor.class, "intake1");
        intake2 = hardwareMap.get(DcMotor.class, "intake2");
        intake2.setDirection(DcMotorSimple.Direction.REVERSE);
        intake1.setDirection(DcMotorSimple.Direction.FORWARD);

        pollenSwitch = hardwareMap.get(Servo.class, "pollenSwitch");

        flywheelPollen = hardwareMap.get(DcMotorEx.class, "flywheelPollen");
        flywheelPollen.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelPollen.setDirection(DcMotorSimple.Direction.FORWARD);

    }
    @Override
    public void start() {
        schedule(autoRoutine());
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        update();



    }
}


