package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class DriveOpMode extends OpMode {


    MecanumDrive mecanumDrive = new MecanumDrive();
    motors motors = new motors();
    String driveMode = "Field";
    float test = 0;
    @Override
    public void init() {
        //Initialize classes for motors
        mecanumDrive.init(hardwareMap, gamepad1);
        motors.init(hardwareMap);
    }

    @Override
    public void loop() {
        double ForwardBack = -gamepad1.left_stick_y;
        double Strafe = gamepad1.left_stick_x;
        double Turn = gamepad1.right_stick_x;

        //Gamepad 1 for Driver Controls
        if (gamepad1.a){driveMode = "Field";}
        if (gamepad1.b){driveMode = "Robot";}
        //Sets current forward position to forwards for field relative drive
        if (gamepad1.y){mecanumDrive.resetIMU();}

        if (driveMode.equals("Field")) {
            mecanumDrive.driveFieldRelative(ForwardBack, Strafe, Turn, 1);
        }
        if (driveMode.equals("Robot")) {
            mecanumDrive.drive(ForwardBack, Strafe, Turn, 1);
        }
        telemetry.addData("Mode", driveMode);


        //Gamepad 2 for Shooter/Intake Controls
        if (gamepad2.right_trigger > 0.1){motors.turnMotor("intake", 1);}
        else if (gamepad2.left_trigger > 0.1) { motors.turnMotor("intake", -1);}
        else {motors.turnMotor("intake", 0);}

        motors.turnMotor("pollen flywheel", 1);
        if (gamepad2.a){ motors.setFlywheelSpeed("pollen flywheel", 5500);}
        if (gamepad2.x){ motors.setFlywheelSpeed("pollen flywheel", 3400);}
        if (gamepad2.right_bumper){ motors.setFlywheelSpeed("pollen flywheel", 3200);}
        if (gamepad2.b){ motors.setFlywheelSpeed("pollen flywheel", 0);}

        if (gamepad2.y){motors.turnMotor("pollen switch", 1);}
        else{motors.turnMotor("pollen switch", 0);}

        //telemetry.addData("position", test);
        //if (gamepad2.dpad_up){test += 0.01F;}
        //if (gamepad2.dpad_down){test -= 0.01F;}
        
    }
}
