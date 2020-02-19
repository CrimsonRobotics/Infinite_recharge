/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoDrive extends SequentialCommandGroup {
  /**
   * Creates a new AutoDrive.
   */
  public AutoDrive() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new DriveAutonomous(0, .3),
      new WaitCommand(1),
      new DriveStop()
      );

    /*
    addCommands(
      sequence(
        new DriveAutonomous(0, 0.3),
        new WaitCommand(1),
        // System.out.println("It should stop"),
        new DriveStop(),
        new WaitCommand(1)
        // System.out.println("If this doesn't stop, run"),
        
      ),
      new DriveStop()
      
    );
    */
  }
}