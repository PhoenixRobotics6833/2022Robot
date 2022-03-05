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
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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
   Thread m_visionThread;
   

  @Override
  public void robotInit() {

    // Mechanical
    controller = new Joystick(0);
    controller2 = new Joystick(1);
    driveTrain = new DriveTrain(0, 1, 2, 3, 4, 5, controller);
    intake = new Intake(6, controller2);
    
    timer = new Timer();
    i=0;
    // Programming
    ahrs = new AHRS(SPI.Port.kMXP);
    //ultrasonic = new Ultrasonic(9, 10);
    useRobot = new AutoRobotAction(intake, driveTrain, ahrs);
    useAuto = new Autonomous(useRobot, ahrs, timer/*, ultrasonic*/);
    //ahrs.calibrate();
    //ahrs.reset();

    //Camera vision
    m_visionThread =
        new Thread(
            () -> {
              // Get the UsbCamera from CameraServer
              UsbCamera camera = CameraServer.startAutomaticCapture();
              // Set the resolution
              camera.setResolution(1280, 720);

              // Get a CvSink. This will capture Mats from the camera
              CvSink cvSink = CameraServer.getVideo();
              // Setup a CvSource. This will send images back to the Dashboard
              CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

              // Mats are very memory expensive. Lets reuse this Mat.
              Mat mat = new Mat();

              // This cannot be 'true'. The program will never exit if it is. This
              // lets the robot stop this thread when restarting robot code or
              // deploying.
              while (!Thread.interrupted()) {
                // Tell the CvSink to grab a frame from the camera and put it
                // in the source mat.  If there is an error notify the output.
                if (cvSink.grabFrame(mat) == 0) {
                  // Send the output the error.
                  outputStream.notifyError(cvSink.getError());
                  // skip the rest of the current iteration
                  continue;
                }
                // Put a rectangle on the image
                Imgproc.rectangle(
                    mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
                // Give the output stream a new image to display
                outputStream.putFrame(mat);
              }
            });
    m_visionThread.setDaemon(true);
    m_visionThread.start();

    autoChooser.setDefaultOption("Do Nothing", "Do Nothing");
    autoChooser.addOption("Dump & Escape DR", "Dump & Escape DR");
    autoChooser.addOption("Dump & Escape", "Dump & Escape");
    autoChooser.addOption("terminalHubStart", "terminalHubStart");
    autoChooser.addOption("hangerHubStart", "hangerHubStart");
    autoChooser.addOption("terminalOuterLeftBall", "terminalOuterLeftBall");
    autoChooser.addOption("terminalInnerRightBall", "terminalInnerRightBall");
    autoChooser.addOption("hangerOuterTarmacBall", "hangerOuterTarmacBall");
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
    //driveTrain.MagEncoder();
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
    ahrs.reset();
    timer.reset();
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
      case("hangerOutertarmacBall"):
        useAuto.hangerOuterTarmacBall();
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
