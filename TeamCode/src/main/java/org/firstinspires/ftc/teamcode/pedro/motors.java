package org.firstinspires.ftc.teamcode.pedro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class motors {
    private DcMotor intake1;
    private DcMotor intake2;
    private DcMotorEx flywheelPollen;
    private double encoderCPM = 28;
    private double kV = 0.000170, kS = 0.04348, kP = 0.002;
    private double gearRatio = 1;
    private double goalRPM = 0;
    public double pollenFlywheelSpeed = 0;
    private Servo pollenSwitch;

    public void init(HardwareMap hwMap){
        intake1 = hwMap.get(DcMotor.class, "intake1");
        intake2 = hwMap.get(DcMotor.class, "intake2");
        intake2.setDirection(DcMotorSimple.Direction.REVERSE);
        intake1.setDirection(DcMotorSimple.Direction.FORWARD);

        pollenSwitch = hwMap.get(Servo.class, "pollenSwitch");

        flywheelPollen = hwMap.get(DcMotorEx.class, "flywheelPollen");
        flywheelPollen.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelPollen.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void turnMotor(String motor, float speed){
        if (motor.equals("intake")){
            intake1.setPower(speed);
            intake2.setPower(speed);
        }
        else if (motor.equals("pollen switch")){

            pollenSwitch.setPosition((speed*0.33)+0.54);

        }
        else if (motor.equals("pollen flywheel")){
            goalRPM = pollenFlywheelSpeed;
        }
        if (motor.equals("pollen flywheel")){
            double feedForward = (kV * goalRPM)+kS;
            double error = goalRPM - ((flywheelPollen.getVelocity()/encoderCPM)* 60) / gearRatio;
            double feedBack = error * kP;

            goalRPM = pollenFlywheelSpeed;
            if (goalRPM==0){flywheelPollen.setPower(0);}
            else {flywheelPollen.setPower(feedForward+feedBack);}
        }
    }
    public void setFlywheelSpeed(String motor, float speed){
        if (motor.equals("pollen flywheel")) {pollenFlywheelSpeed = speed;}
    }
}
