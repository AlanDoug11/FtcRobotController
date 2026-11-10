package org.firstinspires.ftc.teamcode.pedro.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class kVTuner extends OpMode {

    flywheelTest flywheel = new flywheelTest();
    public double kV = 0.000170;
    public double kS = 0.04348;
    public double goalRPM = 2000;

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01,};
    int incrementIdx = 4; //Start at 0.01

    @Override
    public void init() {
        flywheel.init(hardwareMap);
    }

    @Override
    public void loop() {

        if (gamepad1.dpadRightWasPressed() && incrementIdx < 4) {
            incrementIdx++;
        } else if (gamepad1.dpadLeftWasPressed() && incrementIdx > 0) {
            incrementIdx--;
        }
        if (gamepad1.aWasPressed()) {
            goalRPM = 2000;
        } else if (gamepad1.bWasPressed()) {
            goalRPM = 1200;
        }

        double currentStep = increments[incrementIdx];
        //change KS
        if (gamepad1.dpadUpWasPressed()) {kV += currentStep; }
        if (gamepad1.dpadDownWasPressed()) {kV -= currentStep; }

        double power = (kV * goalRPM)+kS;
        flywheel.setMotorPower(power);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kV", "%.6f", kV);
        telemetry.addData("RPM", flywheel.getRPM());
        telemetry.addData("Ticks per Sec", flywheel.getTicksperSec());
        telemetry.addData("Motor Power", power);
    }
}