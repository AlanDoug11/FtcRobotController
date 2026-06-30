package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class Test extends OpMode {


    MechanumDrive mechanumDrive = new MechanumDrive();

    @Override
    public void init() {
        mechanumDrive.init(hardwareMap, gamepad1);
    }

    @Override
    public void loop() {

        mechanumDrive.drive(0.75);

    }
}
