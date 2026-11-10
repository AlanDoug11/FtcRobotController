package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("FLM");
                c.backLeftName.set("RLM");
                c.frontRightName.set("FRM");
                c.backRightName.set("RRM");

                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);

                c.manualBrakeMode.set(true);
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(0.4159261673454225);
        c.yPodOffset.set(4.4905150406003935);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.3204710678141112);
                Controller secondaryTranslationalForward = Controller.proportional(0.11840549641267241);
                Controller primaryTranslationalLateral = Controller.proportional(0.323694729624971);
                Controller secondaryTranslationalLateral = Controller.proportional(0.134310125017147);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016226649637859716));
                c.brake.set(Controller.proportionalFeedforward(0.013792652192180758));

                c.headingFeedback.set(Controller.proportional(5.446550976186784));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05220952889648263, 0.008298422484330275));

                c.linearBrakeCoefficients.set(Matrix.diag(0.11550461422686861, 0.19091570747575484));
                c.quadraticBrakeCoefficients.set(Matrix.diag(7.539480155397406E-4, -8.104490345605328E-4));

                c.maxAchievableForwardVelocity.set(63.661498442373706);
                c.maxAchievableStrafeVelocity.set(37.55173860289324);
                c.naturalForwardDeceleration.set(30.866989857974847);
                c.naturalStrafeDeceleration.set(54.57713985960213);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}