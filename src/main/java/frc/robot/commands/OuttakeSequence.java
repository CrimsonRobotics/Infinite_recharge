/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class OuttakeSequence extends SequentialCommandGroup {
  /**
   * Creates a new OuttakeSequence.
   */
  //Open door
  //Wait .25 seconds for door to open
  //Start running outtake conveyor
  //Wait 3 seconds for outtake to finish unloading
  //Stop running outtake conveyor
  //Close outtake door
  public OuttakeSequence() {
    super(
    new OuttakeDoorOpen(), 
    new WaitCommand(.25), 
    new OuttakeShoot(), 
    new WaitCommand(3), 
    new OuttakeStop(), 
    new OuttakeDoorClose());
  }
}
