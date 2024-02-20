package org.firstinspires.ftc.teamcode.mechanisms;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utilities.Hardware;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


public class LiftMechanism {
    // Lift Motors
    private DcMotorEx LL;
    private DcMotorEx RL;

    // Lift Servos
    private Servo LFS;
    private Servo RFS;

    // Carriage Servos
    private Servo FPS;
    private Servo BPS;

    public LiftMechanism(Hardware robot) {
        LL = robot.LL;
        RL = robot.RL;
        LFS = robot.LFS;
        RFS = robot.RFS;
        FPS = robot.FPS;
        BPS = robot.BPS;
    }

    public class Lift implements Action {
        private double power = 0;
        private double ticksToMove = 0;
        private long endTime = 0;

        public Lift(double power, double inches, long secondsToWait) {
            double ticksPerInch = 83.34;

            this.power = power;
            this.ticksToMove = (int) (inches * ticksPerInch);

            LL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            RL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            LL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            RL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            this.endTime = System.currentTimeMillis() + secondsToWait * 1000;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (System.currentTimeMillis() < endTime)
            {
                return true;
            }

            if (Math.abs(LL.getCurrentPosition()) < ticksToMove && Math.abs(RL.getCurrentPosition()) < ticksToMove) {
                RL.setPower(power);
                LL.setPower(power);
                return true;
            }
            RL.setPower(0);
            LL.setPower(0);
            return false;
        }
    }

    public Action liftUp(double power, double inches, long secondsToWait) {
        return new Lift(power, inches, secondsToWait);
    }

    public Action liftDown(double power, double inches, long secondsToWait) {
        return new Lift(-power, inches, secondsToWait);
    }

    public class SwivelOut implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LFS.setPosition(0.55);
            RFS.setPosition(0.45);
            return false;
        }
    }

    public Action swivelOut() {
        return new SwivelOut();
    }

    public class SwivelIn implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            LFS.setPosition(0.95);
            RFS.setPosition(0.05);
            return false;
        }
    }

    public Action swivelIn() {
        return new SwivelIn();
    }

    public class LockCarriage implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Lock carriage servos
            BPS.setPosition(0);
            FPS.setPosition(0);
            return false;
        }
    }

    public Action lockCarriage() {
        return new LockCarriage();
    }

    public class UnlockCarriage implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // Lock carriage servos
            BPS.setPosition(0.40);
            FPS.setPosition(0.40);
            return false;
        }
    }

    public Action unlockCarriage() {
        return new UnlockCarriage();
    }
}
