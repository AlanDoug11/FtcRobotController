package org.firstinspires.ftc.teamcode.pedro.Tuners;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class flywheelTest {
    private DcMotorEx m1;
    private double encoderCPM = 28;
    private double kV = 0.000166, kS = 0.04348, kP = 0.08;
    private double gearRatio = 1;

    public void init(HardwareMap hwMap) {
        m1 = hwMap.get(DcMotorEx.class, "flywheelPollen");
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m1.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setMotorPower(double power) {
        m1.setPower(power);
    }

    public double getTicksperSec(){
        return m1.getVelocity();
    }

    public double getRPM(){
        return ((getTicksperSec()/encoderCPM)* 60) / gearRatio;
    }



}