package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class DriveOpMode extends OpMode {


    MechanumDrive mechanumDrive = new MechanumDrive();
    String driveMode = "Field";
    @Override
    public void init() {
        mechanumDrive.init(hardwareMap, gamepad1);

    }

    @Override
    public void loop() {
        double ForwardBack = -gamepad1.left_stick_y;
        double Strafe = gamepad1.left_stick_x;
        double Turn = gamepad1.right_stick_x;


        if (gamepad1.a){
            driveMode = "Field";
        }
        if (gamepad1.b){
            driveMode = "Robot";
        }
        if (driveMode.equals("Field")) {
            mechanumDrive.driveFieldRelative(ForwardBack, Strafe, Turn, 0.9);
        }
        if (driveMode.equals("Robot")) {
            mechanumDrive.drive(ForwardBack, Strafe, Turn, 0.75);
        }
        telemetry.addData("Mode", driveMode);

    }
}
