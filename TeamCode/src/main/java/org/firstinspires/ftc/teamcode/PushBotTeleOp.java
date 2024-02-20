package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utilities.Hardware;

@TeleOp(name="PushBotTeleOp", group="Drive")
public class PushBotTeleOp extends LinearOpMode {

    // Class Gamepad Documentation:

    // https://ftctechnh.github.io/ftc_app/doc/javadoc/com/qualcomm/robotcore/hardware/Gamepad.html

    Hardware robot = new Hardware();

    @Override

    public void runOpMode() {
        robot.init(hardwareMap);

        robot.FLD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        robot.FRD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        robot.BLD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        robot.BRD.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        robot.LL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        robot.RL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        // Initialize Servo Positions
        robot.LFS.setPosition(0.95);

        robot.RFS.setPosition(0.05);

        robot.FPS.setPosition(0);

        robot.BPS.setPosition(0);

        double speed = 0.5;

        double lefty;

        double righty;

        double leftx;

        double rightx;


        boolean buttonL;

        boolean buttonR;


        double triggerR;

        double triggerL;


        double intakeSpeed;

        double intakeSpeedMultiplier = 0.85;


        double armPower;

        double armSpeed = 1.0;


        boolean dpadUp;

        boolean dpadDown;

        boolean dpadRight;

        boolean dpadLeft;


        boolean a;

        boolean b;

        boolean x;

        boolean y;


        long nextLBTimestamp = 0;

        long nextRBTimestamp = 0;

        boolean isBPSUp = true;

        boolean isFPSUp = true;

        while (opModeIsActive()) {

            // Gamepad 1

            lefty = -gamepad1.left_stick_y;

            righty = -gamepad1.right_stick_y;

            leftx = gamepad1.left_stick_x;

            rightx = gamepad1.right_stick_x;


            // Gamepad 2

            armPower = gamepad2.left_stick_y;

            intakeSpeed = gamepad2.right_stick_y;

            buttonL = gamepad2.left_bumper;

            buttonR = gamepad2.right_bumper;

            triggerL = gamepad2.left_trigger;

            triggerR = gamepad2.right_trigger;

            dpadUp = gamepad2.dpad_up;

            dpadLeft = gamepad2.dpad_left;

            dpadRight = gamepad2.dpad_right;

            dpadDown = gamepad2.dpad_down;

            y = gamepad2.y;
            x = gamepad2.x;

            a = gamepad2.a;
            b = gamepad2.b;


            if(gamepad1.a)

            {

                speed = 0.9;

            }

            else if(gamepad1.b)

            {

                speed = 0.75;

            }

            else if(gamepad1.x)

            {

                speed = 0.5;

            }

            else if(gamepad1.y)

            {

                speed = 0.3;

            }


            // Gamepad 2 Code

            if (buttonR && gamepad2.timestamp > nextRBTimestamp)

            {

                // Control BPS: Back Pixel Servo

                nextRBTimestamp = gamepad2.timestamp + 300;


                if (isBPSUp)

                {

                    // Set BPS to down position

                    robot.BPS.setPosition(0);



                }

                else {

                    // Set BPS to up position

                    robot.BPS.setPosition(.4);

                }


                isBPSUp = !isBPSUp;

            }


            if (buttonL && gamepad2.timestamp > nextLBTimestamp)

            {

                // Control BPS: Back Pixel Servo

                nextLBTimestamp = gamepad2.timestamp + 300;


                if (isFPSUp)

                {

                    // Set FPS to down position

                    robot.FPS.setPosition(0);

                }

                else {

                    // Set FPS to up position

                    robot.FPS.setPosition(.4);

                }


                isFPSUp = !isFPSUp;

            }


            if (triggerL > 0)
            {
                // Swivel in
                robot.LFS.setPosition(0.95); // To swivel in more, increase this
                robot.RFS.setPosition(0.05);// To swivel in more, decrease this
            }


            if (triggerR > 0)
            {
                // Swivel out
                robot.LFS.setPosition(0.58); // To swivel out more, decrease this
                robot.RFS.setPosition(0.42); // To swivel out more, increase this
            }

//            Known Issue: Lift Speed Controls are probably not working
            if (dpadUp)

            {

                armSpeed = 1.0;

            }

            else if (dpadLeft)

            {

                armSpeed = 0.75;

            }

            else if (dpadRight)

            {

                armSpeed = 0.5;

            }

            else if (dpadDown)

            {

                armSpeed = 0.25;

            }

//            Known Issue: Intake Speed Controls are probably not working
            if (y)

            {

                intakeSpeedMultiplier = 1;

            }

            else if (x)

            {

                intakeSpeedMultiplier = 0.7;

            }

            if (a)
            {
                robot.AP.setDirection(Servo.Direction.REVERSE);
                robot.AP.setPosition(0);
            }

            if (b)
            {
                robot.AP.setDirection(Servo.Direction.REVERSE);
                robot.AP.setPosition(1);
            }

            double FLDp = lefty + leftx;
            double FRDp = righty - rightx;
            double BLDp = lefty - leftx;
            double BRDp = righty + rightx;

            robot.FLD.setPower(speed * FLDp);
            robot.FRD.setPower(speed * FRDp);
            robot.BLD.setPower(speed * BLDp);
            robot.BRD.setPower(speed * BRDp);

            robot.IN.setPower(intakeSpeed*intakeSpeedMultiplier);
            robot.LL.setPower(armSpeed*armPower);
            robot.RL.setPower(armSpeed*armPower);
            telemetry.update();


        }

    }

}

