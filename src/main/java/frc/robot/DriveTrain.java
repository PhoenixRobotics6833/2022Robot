package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
    WPI_TalonSRX leftLeader;
    WPI_TalonSRX rightLeader;

    WPI_TalonSRX leftFollower;
    WPI_TalonSRX leftFollower2;
    WPI_TalonSRX rightFollower;
    WPI_TalonSRX rightFollower2;

    Joystick controller;

    DifferentialDrive myDrive;

    double throttleValue;
    //right trigger boosts speed
    int throttleAxis = 3;

    double leftStick;
    double rightStick;

    double leftEncoder;
    double rightEncoder;

    double lEncoderDistance;
    double rEncoderDistance;


    public DriveTrain(int motor1, int motor2, int motor3, int motor4, int motor5, int motor6, Joystick controller1) {
        leftLeader = new WPI_TalonSRX(motor1);
        leftFollower = new WPI_TalonSRX(motor2);
        leftFollower2 = new WPI_TalonSRX(motor3);
        rightLeader = new WPI_TalonSRX(motor4);
        rightFollower = new WPI_TalonSRX(motor5);
        rightFollower2 = new WPI_TalonSRX(motor6);
        rightLeader.setInverted(true);
        rightFollower.setInverted(true);
        rightFollower2.setInverted(true);
        

        controller = controller1;

        leftFollower.follow(leftLeader);
        leftFollower2.follow(leftLeader);
        rightFollower.follow(rightLeader);
        rightFollower2.follow(rightLeader);

        myDrive = new DifferentialDrive(leftLeader, rightLeader);

    }
    
    public void TalonDrive() {

        throttleValue = controller.getRawAxis(throttleAxis);

        leftStick = controller.getRawAxis(1) / (2 - throttleValue);
        rightStick = controller.getRawAxis(5) / (2 - throttleValue);
        myDrive.tankDrive(leftStick, rightStick);
    }

    public void TalonDriveNoLimiter() {
        double leftAxis = controller.getRawAxis(1);
        double rightAxis = controller.getRawAxis(5);

       

        myDrive.tankDrive(Math.atan(leftAxis * (Math.PI/2)), Math.atan(rightAxis * (Math.PI/2)));
    }

    public void talonDriveThrottle() {
        throttleValue = controller.getRawAxis(throttleAxis);

        double leftAxis = controller.getRawAxis(1);
        double rightAxis = controller.getRawAxis(5);

        int leftDirection = 1;
        int rightDirection = 1;

        if(leftAxis < 0) {
            leftDirection = -1;
        }
        //else {
        //    leftDirection = 1;
        //}
        if(rightAxis < 0) {
            rightDirection = -1;
        }

        myDrive.tankDrive(leftAxis/2 + leftDirection * throttleValue/2, rightAxis/2 + rightDirection * throttleValue/2);

    }

// The encoder code
    public void MagEncoder() {
        leftLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        this.leftEncoder = leftLeader.getSelectedSensorPosition(0);
        this.rightEncoder = rightLeader.getSelectedSensorPosition(0);
    }

// Puts the encoder values onto the Smartdashboard
    public void SmartDashboard() {
        SmartDashboard.putNumber("leftVelocity", leftEncoder);
        SmartDashboard.putNumber("rightVelocity", rightEncoder);
        SmartDashboard.putNumber("leftDistanceInches", leftEncoderDistance());
        SmartDashboard.putNumber("rightDistanceInches", rightEncoderDistance());
    }

// calculates the distance moved in inches
    public double leftEncoderDistance() {

        lEncoderDistance = (leftEncoder / 4096) * 6 * Math.PI;
        return lEncoderDistance;
    }

    public double rightEncoderDistance() {

        rEncoderDistance = (rightEncoder / 4096) * 6 * Math.PI;
        return rEncoderDistance;
    }

// sets the encoder distance to 0
    public void resetEncoderDistance() {
        lEncoderDistance = 0.0;
        rEncoderDistance = 0.0;
    }

}
