package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

@Config
@Autonomous(name = "Strafe Test", group = "Autonomous")
public class StrafeTest extends LinearOpMode {
    public static double distance = 24;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .lineToYConstantHeading(distance)
                            .build());
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .lineToYConstantHeading(distance)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
