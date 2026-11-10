package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class Testing extends OpMode {

    private DcMotor FLM;
    private DcMotor FRM;
    private DcMotor RRM;
    private DcMotor RLM;

    public void init() {
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        RLM = hardwareMap.get(DcMotor.class, "RLM");
        RRM = hardwareMap.get(DcMotor.class, "RRM");

        FLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RLM.setDirection(DcMotor.Direction.REVERSE);
        RRM.setDirection(DcMotor.Direction.FORWARD);
        FLM.setDirection(DcMotor.Direction.REVERSE);
        FRM.setDirection(DcMotor.Direction.FORWARD);
    }


    public void loop() {
        if (gamepad1.a) {RLM.setPower(1);}
        if (gamepad1.b) {RLM.setPower(0);}
        if (gamepad1.y) {FLM.setPower(1);}
        if (gamepad1.x) {FLM.setPower(0);}
        if (gamepad1.dpad_down ) {FRM.setPower(1);}
        if (gamepad1.dpad_right) {FRM.setPower(0);}
        if (gamepad1.dpad_up) {RRM.setPower(1);}
        if (gamepad1.dpad_left) {RRM.setPower(0);}

    }
}

