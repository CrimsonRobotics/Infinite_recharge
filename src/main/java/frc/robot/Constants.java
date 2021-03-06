/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Intake
    public final static int INTAKE_TOP = 56;
    public final static int INTAKE_BOTTOM = 45;

    public final static double INTAKE_SPEED = .35;

    public final static int OUTTAKE_SPARK = 59;
    public final static int OUTTAKE_SOLENOID = 2;

    public final static int INTAKE_SOLENOID1 = 0;
    public final static int INTAKE_SOLENOID2 = 1;
    
    public final static int OUTTAKE_PCM1 = 0;

    public final static int INTAKE_IN_BUTTON = 8;
    public final static int INTAKE_ARM_BUTTON = 9;
    public final static int OUTTAKE_BUTTON = 10;

    // Drivetrain
    public final static int fLID = 60;
    public final static int mLID = 61;
    public final static int bLID = 62;
    
    public final static int fRID = 49;
    public final static int mRID = 50;
    public final static int bRID = 51;

    public final static int mod1ID = 0;
    public final static int mod2ID = 1;
    public final static int shiftyLIDF = 0;
    public final static int shiftyLIDR = 1;
    public final static int shiftyRIDF = 2;
    public final static int shiftyRIDR = 3;

    public static int ELEVATOR_MOTOR = 58;
    public static int WINCH_MOTOR = 47;
    public static int lateralMotor = 46;

    // Control Panel Variables
    public static final int controlPanelSpinner = 57;
    public static final int controlPanelSolenoidF = 6;
    public static final int controlPanelSolenoidR = 7;

    public static final int pcm1ID = 0;
    public static final I2C.Port i2c = I2C.Port.kOnboard;
}
