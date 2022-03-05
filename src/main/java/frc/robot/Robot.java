// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.Ultrasonic;


public class Robot extends TimedRobot {
   // Mechanical
   Joystick controller;
   Joystick controller2;
   DriveTrain driveTrain;
   Intake intake;

   // Programming
   NetworkTableInstance table = NetworkTableInstance.getDefault();
   AHRS ahrs;
   double rotateToAngle;
   double currentAngle;
   Autonomous useAuto;
   AutoRobotAction useRobot;
   //Ultrasonic ultrasonic;

   // Auto
   SendableChooser<String> autoChooser = new SendableChooser<>();
   String selectedAutonomous;
   Timer timer;
   int i;
   

  @Override
  public void robotInit() {

    // Mechanical
    controller = new Joystick(0);
    controller2 = new Joystick(1);
    driveTrain = new DriveTrain(0, 1, 2, 3, 4, 5, controller);
    intake = new Intake(6, controller2);
    
    i=0;
    // Programming
    ahrs = new AHRS(SPI.Port.kMXP);
    //ultrasonic = new Ultrasonic(9, 10);
    useRobot = new AutoRobotAction(intake, driveTrain, ahrs);
    useAuto = new Autonomous(useRobot, ahrs, timer/*, ultrasonic*/);
    //ahrs.calibrate();
    ahrs.reset();

    autoChooser.setDefaultOption("Do Nothing", "Do Nothing");
    autoChooser.addOption("Dump & Escape DR", "Dump & Escape DR");
    autoChooser.addOption("Dump & Escape", "Dump & Escape");
    autoChooser.addOption("terminalHubStart", "terminalHubStart");
    autoChooser.addOption("hangerHubStart", "hangerHubStart");
    autoChooser.addOption("terminalOuterLeftBall", "terminalOuterLeftBall");
    autoChooser.addOption("terminalInnerRightBall", "terminalInnerRightBall");
    autoChooser.addOption("testDriveForward", "testDriveForward");
    autoChooser.addOption("testIntakeForward", "testIntakeForward");
    autoChooser.addOption("testRotateToAngle", "testRotateToAngle");
    autoChooser.addOption("testDriveBack", "testDriveBack");
    autoChooser.addOption("testDriveForwardSlow", "testDriveForwardSlow");
    autoChooser.addOption("testDriveS3", "testDriveS3");
    autoChooser.addOption("testDriveStraightBackward", "testDriveStraightBackward");

    SmartDashboard.putData(autoChooser);
  }


  @Override
  public void disabledPeriodic() {

    

  }

  @Override
  public void robotPeriodic() {
    driveTrain.MagEncoder();
    SmartDashboard.putNumber("leftPosition", driveTrain.leftEncoder);
    SmartDashboard.putNumber("rightPosition", driveTrain.rightEncoder);
    SmartDashboard.putNumber("leftDistanceInches", driveTrain.leftEncoderDistance());
    SmartDashboard.putNumber("rightDistanceInches", driveTrain.rightEncoderDistance());
    SmartDashboard.putNumber("Yaw Angle: ", ahrs.getAngle());
    SmartDashboard.putNumber("Pitch Angle: ", ahrs.getPitch());
    SmartDashboard.putNumber("Roll Angle: ", ahrs.getRoll());

    //SmartDashboard.putNumber("ultrasonicDistanceInches", ultrasonic.getRangeInches());
    //SmartDashboard.putNumber("ultrasonicDistanceMM", ultrasonic.getRangeMM());

    SmartDashboard.putNumber("loop num: ", i++);
  }

  @Override
  public void autonomousInit() {
    selectedAutonomous = autoChooser.getSelected();
    timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    switch(selectedAutonomous){
      case ("Dump & Escape DR"):
        useAuto.dumpEscapeDR();
        break;
      case ("Dump & Escape"):
        useAuto.dumpEscape();
        break;
      case("terminalHubStart"):
        useAuto.terminalHubStart();
        break;
      case("hangerHubStart"):
        useAuto.terminalHubStart();
        break;
      case("terminalOuterLeftBall"):
        useAuto.terminalOuterLeftBall();
        break;
      case("terminalInnerRightBall"):
        useAuto.terminalInnerRightBall();
        break;
      case("testDriveForward"):
        useAuto.testDriveForward();
        break;
      case("testIntakeForward"):
        useAuto.testIntakeForward();
        break;
      case("testRotateToAngle"):
        useAuto.testRotateToAngle();
        break;
      case("testDriveBack"):
        useAuto.testDriveBack();
        break;
      case("testDriveForwardSlow"):
        useAuto.testDriveForwardSlow();
        break;
      case("testDriveS3"):
        useAuto.testDriveS3();
        break;
      case("testDriveStraightBackward"):
        useAuto.testDriveStraightBackward();
        break;
    }
  }


  int driveTrainMode = 0;
  @Override
  public void teleopPeriodic() {
    System.out.println("teleop loop");
    //driveTrain.TalonDrive();
    intake.intakeControl();

    if(controller.getRawButton(7)) {
      driveTrainMode = 0;
    }
    else if(controller.getRawButton(8)) {
      driveTrainMode = 1;
    }

    if(driveTrainMode == 0) {
      driveTrain.TalonDriveNoLimiter();
    }
    else if(driveTrainMode == 1) {
      driveTrain.talonDriveThrottle();
    }

    

    /*
    if(controller.getRawAxis(2) > .5) {
      driveTrain.TalonDriveNoLimiter();

    } else {
      driveTrain.TalonDrive();
    }
    */
  }

  
  @Override
  public void testPeriodic() {

  }
  
}
