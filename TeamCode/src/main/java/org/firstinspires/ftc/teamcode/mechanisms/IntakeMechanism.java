package org.firstinspires.ftc.teamcode.mechanisms;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.utilities.Hardware;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class IntakeMechanism {
    private DcMotorEx intake;

    public IntakeMechanism(Hardware robot)
    {
        intake = robot.IN;
    }

    public class DropPurplePixel implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (intake.getCurrentPosition() > -33) {
                intake.setPower(-0.2);
                return true;
            }
            intake.setPower(0);
            return false;
        }
    }

    public Action dropPurplePixel() {
        return new DropPurplePixel();
    }
}
