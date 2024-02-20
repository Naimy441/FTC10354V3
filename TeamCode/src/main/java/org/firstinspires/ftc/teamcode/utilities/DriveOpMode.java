package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/**
 * This class was created to host all sizeable functions for use in actual op modes. In other words, this is an FTC library for real autonomous and/or teleop programs.
 */
@Disabled
@Autonomous
public class DriveOpMode extends LinearOpMode {
    final OpenCvCameraRotation CAMERA_ROTATION = OpenCvCameraRotation.UPSIDE_DOWN;

    /**
     * LinearOpMode requires a runOpMode function, but this method should be overridden in all other scripts that use DriveOpMode.
     */
    @Override
    public void runOpMode() {}

    /**
     * To access the pipeline returned from this function, use: BlueColorPipeline pipeline = startBlueCamera();
     */
    public BlueColorPipeline startBlueCamera()
    {
        BlueColorPipeline pipeline = new BlueColorPipeline();
        OpenCvWebcam CAM;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        CAM = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        CAM.setMillisecondsPermissionTimeout(2500);

        CAM.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                CAM.setPipeline(pipeline);
                CAM.startStreaming(1280, 720, CAMERA_ROTATION);
            }

            @Override
            public void onError(int errorCode) {}
        });

        return pipeline;
    }

    /**
     * To access the pipeline returned from this function, use: RedColorPipeline pipeline = startRedCamera();
     */
    public RedColorPipeline startRedCamera()
    {
        RedColorPipeline pipeline = new RedColorPipeline();
        OpenCvWebcam CAM;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        CAM = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        CAM.setMillisecondsPermissionTimeout(2500);

        CAM.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                CAM.setPipeline(pipeline);
                CAM.startStreaming(1280, 720, CAMERA_ROTATION);
            }

            @Override
            public void onError(int errorCode) {}
        });

        return pipeline;
    }

    public boolean[] initWithController(boolean park)
    {
        boolean blueSide = true;
        boolean dropYellowPixel = true;
        boolean parkInside = park;

        while (!gamepad1.right_bumper)
        {
            if (gamepad1.x) {
                dropYellowPixel = true;
            }

            if (gamepad1.y) {
                dropYellowPixel = false;
            }

            if (gamepad1.a) {
                parkInside = true;
            }

            if (gamepad1.b) {
                parkInside = false;
            }

            if (gamepad1.dpad_up) {
                blueSide = true;
            }

            if (gamepad1.dpad_down) {
                blueSide = false;
            }

            telemetry.addLine("Press right bumper to end initialization.");
            telemetry.addLine("blueSide: up -> true, down -> false");
            telemetry.addLine("dropYellowPixel: x - > true, y -> false");
            telemetry.addLine("parkInside: a - > true, b -> false");
            telemetry.addLine(" ");
            telemetry.addData("blueSide", blueSide);
            telemetry.addLine(" ");
            telemetry.addData("dropYellowPixel", dropYellowPixel);
            telemetry.addLine(" ");
            telemetry.addData("parkInside", parkInside);
            telemetry.update();

            sleep(50);
        }

        return new boolean[]{blueSide, dropYellowPixel, parkInside};
    }
}
