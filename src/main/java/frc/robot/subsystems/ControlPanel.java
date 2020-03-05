/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.io.IOException;
import java.nio.file.Path;

// import frc.robot.commands.CPSpin;
import com.revrobotics.CANSparkMax;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ControlPanel.
   */

  // REMEMBER THAT THE COLOR SENSOR IS NOT DIRECTLY ABOVE THE CORRECT COLOR, IT
  // WILL BE ONE SPACE OFF

  ColorSensorV3 colorSense;
  Color detectedC;

  String trajectoryJSON = "paths/Test.wpilib.json";

  private final ColorMatch colorMatch = new ColorMatch();

  private final Color blueMatch = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color redMatch = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color yellowMatch = ColorMatch.makeColor(0.361, 0.524, 0.113);
  private final Color greenMatch = ColorMatch.makeColor(0.197, 0.561, 0.240);

  public String searchingColor;

  public boolean foundColor = false;
  public boolean currentlySpinning = false;
  public boolean spinnerUp = false;

  CANSparkMax panelSpinner;
  DoubleSolenoid spinnerSolenoid;

  public ControlPanel() {
    panelSpinner = new CANSparkMax(Constants.controlPanelSpinner, MotorType.kBrushed);
    spinnerSolenoid = new DoubleSolenoid(Constants.pcm1ID, Constants.controlPanelSolenoidF, Constants.controlPanelSolenoidR);

  /*  try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }
    */
    
    currentlySpinning = false;
    foundColor = false;

    colorSense = new ColorSensorV3(Constants.i2c);

    colorMatch.addColorMatch(blueMatch);
    colorMatch.addColorMatch(redMatch);
    colorMatch.addColorMatch(yellowMatch);
    colorMatch.addColorMatch(greenMatch);

    SmartDashboard.putBoolean("Found color", false);
    SmartDashboard.putBoolean("Spinning", false);
  }

  public void StartCPSPin() {
    spinnerSolenoid.set(Value.kForward);
    panelSpinner.set(0.4);
  }
  
  public void EndCPSpin() {
    spinnerSolenoid.set(Value.kReverse);
    panelSpinner.set(0);
  }

  public void raiseSpinner() {
    spinnerSolenoid.set(Value.kForward);
    spinnerUp = true;
  }

  public void lowerSpinner() {
    spinnerSolenoid.set(Value.kReverse);
    spinnerUp = false;
  }

  public void spinLeft(double speed) {
    if (spinnerUp == false) return;
    panelSpinner.set(speed);
  }

  public void spinRight(double speed) {
    if (spinnerUp == false) return;
    panelSpinner.set(-speed);
  }

  //Always looping
  public void ColorDetect() {
    detectedC = colorSense.getColor();

    ColorMatchResult match = colorMatch.matchClosestColor(detectedC);
    String colorString;

    if (match.color == blueMatch) {
      colorString = "B";
    } else if (match.color == redMatch) {
      colorString = "R";
    } else if (match.color == greenMatch) {
      colorString = "G";
    } else if (match.color == yellowMatch) {
      colorString = "Y";
    } else {
      colorString = "Unknown";
    }

    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putNumber("Confidence", match.confidence);

    SmartDashboard.putNumber("red: ", detectedC.red);
    SmartDashboard.putNumber("green: ", detectedC.green);
    SmartDashboard.putNumber("blue: ", detectedC.blue);

    if (currentlySpinning == true) {
      SmartDashboard.putBoolean("Spinning", true);
      panelSpinner.set(.3);
      if (colorString != "Unknown") {
        if (match.confidence >= .7) {
          if (colorString == searchingColor) {
            currentlySpinning = false;
            System.out.println("Found color " + searchingColor + " after searching");
            SmartDashboard.putBoolean("Found color", true);
          }
        } 
      }
    } else {
      SmartDashboard.putBoolean("Spinning", false);
      panelSpinner.set(0);
    }
  }

  public void PositionControl() {
    // Retrieving the color sent to the robot by the field messaging system
    String gameData = DriverStation.getInstance().getGameSpecificMessage();

    // Tell if the robot is already trying to spin the control panel
    if (currentlySpinning == true) {
      System.out.println("Control panel mechanism is already spinning");
      return;
    }

    if (gameData.length() > 0) {
      currentlySpinning = true;
      SmartDashboard.putBoolean("Found color", false);
      switch (gameData.charAt(0)) {
      case 'G':
        SmartDashboard.putString("Spining to color", "Green");
        searchingColor = "G";
        break;
      case 'B':
        SmartDashboard.putString("Spining to color", "Blue");
        searchingColor = "B";
        break;
      case 'Y':
        SmartDashboard.putString("Spining to color", "Yellow");
        searchingColor = "Y";
        break;
      case 'R':
        SmartDashboard.putString("Spining to color", "Red");
        searchingColor = "R";
        break;
      }
    } else {
      System.out.println("No gamedata");
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
