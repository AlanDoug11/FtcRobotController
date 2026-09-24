package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class MechanumDrive {
    private DcMotor FLM;
    private DcMotor FRM;
    private DcMotor RLM;
    private DcMotor RRM;
    private IMU imu;

    public void init(HardwareMap hwMap, Gamepad gamepad){
        FLM = hwMap.get(DcMotor.class, "FLM");
        FRM = hwMap.get(DcMotor.class, "FRM");
        RLM = hwMap.get(DcMotor.class, "RLM");
        RRM = hwMap.get(DcMotor.class, "RRM");

        FLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RLM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RRM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RLM.setDirection(DcMotor.Direction.REVERSE);
        RRM.setDirection(DcMotor.Direction.FORWARD);
        FLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);

        imu = hwMap.get(IMU.class , "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT);
        imu.initialize(new IMU.Parameters(RevOrientation));
        imu.resetYaw();
    }
    public void drive (double ForwardBack, double Strafe, double Turn, double speed){
        double FLM_Power;
        double FRM_Power;
        double RLM_Power;
        double RRM_Power;
        double max;

        FLM_Power = (ForwardBack + Strafe) + Turn;
        FRM_Power = (ForwardBack - Strafe) - Turn;
        RLM_Power = (ForwardBack - Strafe) + Turn;
        RRM_Power = (ForwardBack + Strafe) - Turn;
        max = JavaUtil.maxOfList(JavaUtil.createListWith(Math.abs(FLM_Power), Math.abs(FRM_Power), Math.abs(RLM_Power), Math.abs(RRM_Power)));
        if (max > 1) {
            max = FLM_Power + max;
            max = FRM_Power + max;
            max = RLM_Power + max;
            max = RRM_Power + max;
        }
        FLM.setPower(FLM_Power * speed);
        FRM.setPower(FRM_Power * speed);
        RLM.setPower(RLM_Power * speed);
        RRM.setPower(RRM_Power * speed);

    }

    public void driveFieldRelative(double forward, double strafe, double rotate, double speed){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r  * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);
        this.drive(newForward, newStrafe, rotate, speed);
    }
}
