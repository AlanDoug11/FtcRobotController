package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.JavaUtil;


public class MechanumDrive {
    private DcMotor FLM;
    private DcMotor FRM;
    private DcMotor RLM;
    private DcMotor RRM;
    private Gamepad gamepad;

    public void init(HardwareMap hwMap, Gamepad gamepad){
        FLM = hwMap.get(DcMotor.class, "FLM");
        FRM = hwMap.get(DcMotor.class, "FRM");
        RLM = hwMap.get(DcMotor.class, "RLM");
        RRM = hwMap.get(DcMotor.class, "RRM");

        FLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RLM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RRM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        RLM.setDirection(DcMotor.Direction.REVERSE);
        RRM.setDirection(DcMotor.Direction.REVERSE);
        FLM.setDirection(DcMotor.Direction.FORWARD);
        FRM.setDirection(DcMotor.Direction.FORWARD);

        this.gamepad = gamepad;
    }
    public void drive (double speed){
        float ForwardBack;
        float Strafe;
        float Turn;
        float FLM_Power;
        float FRM_Power;
        float RLM_Power;
        float RRM_Power;
        double max;


        ForwardBack = gamepad.left_stick_x;
        Strafe = gamepad.left_stick_y;
        Turn = -gamepad.right_stick_x;
        FLM_Power = (ForwardBack + Strafe) + Turn;
        FRM_Power = (ForwardBack - Strafe) - Turn;
        RLM_Power = (ForwardBack - Strafe)  + Turn;
        RRM_Power = (ForwardBack + Strafe) - Turn;
        max = JavaUtil.maxOfList(JavaUtil.createListWith(Math.abs(FLM_Power), Math.abs(FRM_Power), Math.abs(RLM_Power), Math.abs(RRM_Power)));
        if (max > 1) {
            max = FLM_Power + max;
            max = FRM_Power + max;
            max = RLM_Power + max;
            max = RRM_Power + max;
        }
        FLM.setPower(FLM_Power * speed);
        FRM.setPower(RLM_Power * speed);
        RLM.setPower(FRM_Power * speed);
        RRM.setPower(RRM_Power * speed);

    }
}
