package org.firstinspires.ftc.teamcode.roadrunner;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMechanism;
import org.firstinspires.ftc.teamcode.mechanisms.LiftMechanism;
import org.firstinspires.ftc.teamcode.utilities.BlueColorPipeline;
import org.firstinspires.ftc.teamcode.utilities.DriveOpMode;
import org.firstinspires.ftc.teamcode.utilities.Hardware;
import org.firstinspires.ftc.teamcode.utilities.RedColorPipeline;

// TODO: USE TEMPLATE TO CREATE REAL TRAJECTORIES

@Config
@Autonomous(name = "Template", group = "Autonomous")
public class Template extends DriveOpMode {
    private final Hardware robot = new Hardware();

    // Define public, static, and non-final variables.
    // These can be edited directly through the FTC Dashboard (hover over @Config for more details).
    public static Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
    public static int imageNum = 0;
    public static double liftHeight = 5;
    public static double backBoardX = 50;
    public static double parkY = 0;

    @Override
    public void runOpMode() {
//        telemetry.setMsTransmissionInterval(10);

        // Initialize hardware, drive, and mechanisms.
        robot.init(hardwareMap);
        MecanumDrive drive = new MecanumDrive(robot.getHardwareMap(), startPose);
        IntakeMechanism intake = new IntakeMechanism(robot);
        LiftMechanism lift = new LiftMechanism(robot);

        boolean[] driveVariables = initWithController(false);

        if (driveVariables[2]) {
            parkY = 8;
        }
        else {
            parkY = 60;
        }

        // Create trajectories for either blue side or red side.
        Action purple1;
        Action purple2;
        Action purple3;
        Action yellow1;
        Action yellow2;
        Action yellow3;
        Action pathPark;
        if (driveVariables[0]) {
            purple1 = drive.actionBuilder(startPose)
                .build();
            purple2 = drive.actionBuilder(startPose)
                .build();
            purple3 = drive.actionBuilder(startPose)
                .build();
            yellow1 = drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            yellow2 = drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            yellow3 = drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            pathPark = drive.actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
        }
        else {
            purple1 = drive.mirroredActionBuilder(startPose)
                .build();
            purple2 = drive.mirroredActionBuilder(startPose)
                .build();
            purple3 = drive.mirroredActionBuilder(startPose)
                .build();
            yellow1 = drive.mirroredActionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            yellow2 = drive.mirroredActionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            yellow3 = drive.mirroredActionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
            pathPark = drive.mirroredActionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .build();
        }

        // Find imageNum for either blue capstone or red capstone.
        if (driveVariables[0]) {
            BlueColorPipeline pipeline = startBlueCamera();
            while (!isStopRequested() && !opModeIsActive()) {
                imageNum = pipeline.getImageNum();
                telemetry.addData("imageNum", imageNum);
                telemetry.update();
            }
        }
        else {
            RedColorPipeline pipeline = startRedCamera();
            while (!isStopRequested() && !opModeIsActive()) {
                imageNum = pipeline.getImageNum();
                telemetry.addData("imageNum", imageNum);
                telemetry.update();
            }
        }

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        Actions.runBlocking(lift.lockCarriage());

        // Select which paths to follow.
        Action pathPurple = null;
        Action pathYellow = null;
        if (imageNum == 1) {
            pathPurple = purple1;
            pathYellow = yellow1;
        }
        else if (imageNum == 2 || imageNum == 3 || imageNum == 4) {
            pathPurple = purple2;
            pathYellow = yellow2;
        }
        else if (imageNum == 5) {
            pathPurple = purple3;
            pathYellow = yellow3;
        } else {
            // If no imageNum was received, pick imageNum as 1 by default.
            pathPurple = purple1;
            pathYellow = yellow1;
        }

        // Follow path to score purple pixel.
        Actions.runBlocking(
            new SequentialAction(
                pathPurple,
                intake.dropPurplePixel()
            )
        );

        // Follow path to score yellow pixel and then park.
        if (driveVariables[1]) {
            Actions.runBlocking(
                new SequentialAction(
                    new ParallelAction(
                        pathYellow,
                        lift.liftUp(1, liftHeight, 3)
                    ),
                    lift.swivelOut(),
                    lift.unlockCarriage(),
                    pathPark,
                    lift.swivelIn(),
                    lift.liftDown(0.75, liftHeight - 0.5, 0)
                )
            );
        }
    }
}
