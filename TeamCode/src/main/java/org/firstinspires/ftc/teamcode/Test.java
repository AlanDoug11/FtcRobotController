package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class Test extends OpMode {



    @Override
    public void init() {
        double strafe = 8;
        int teamnumber = 23668;
    }

    @Override
    public void loop() {
        telemetry.addData("Hello", "World");
        telemetry.addData("Team Number", teamnumber);
    }
}
