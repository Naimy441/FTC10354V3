package org.firstinspires.ftc.teamcode.roadrunner;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeMechanism;
import org.firstinspires.ftc.teamcode.mechanisms.LiftMechanism;
import org.firstinspires.ftc.teamcode.utilities.BlueColorPipeline;
import org.firstinspires.ftc.teamcode.utilities.DriveOpMode;
import org.firstinspires.ftc.teamcode.utilities.Hardware;

@Config
@Autonomous(name = "Template", group = "Autonomous")
public class Template extends DriveOpMode {
    private Hardware robot = new Hardware();
    public static Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
    public static int imageNum = 0;
    public static double liftHeight = 5;

    @Override
    public void runOpMode() {
        // telemetry.setMsTransmissionInterval(10);

        robot.init(hardwareMap);
        MecanumDrive drive = new MecanumDrive(robot.getHardwareMap(), startPose);
        IntakeMechanism intake = new IntakeMechanism(robot);
        LiftMechanism lift = new LiftMechanism(robot);

        boolean[] driveVariables = initWithController(false);

        Action traj = drive.actionBuilder(drive.pose)
                .build();

        Actions.runBlocking(lift.lockCarriage());

        BlueColorPipeline pipeline = startBlueCamera();
        while (!isStopRequested() && !opModeIsActive()) {
            imageNum = pipeline.getImageNum();
            telemetry.addData("imageNum", imageNum);
            telemetry.update();
        }

        waitForStart();

        if (isStopRequested()) {
            stop();
        }
        
        if (imageNum == 1) {

        } else if (imageNum == 2 || imageNum == 3 || imageNum == 4) {

        } else if (imageNum == 5) {

        }
    }
}
