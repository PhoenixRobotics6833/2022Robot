package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

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

    // The talon axis input to speed output is a x^3 curve
    public void TalonDriveCubic() {
        double leftAxis = controller.getRawAxis(1);
        double rightAxis = controller.getRawAxis(5);

       

        myDrive.tankDrive(Math.pow(leftAxis, 3), Math.pow(rightAxis, 3));
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

        double leftDrive = (3 * leftAxis/4) + leftDirection * throttleValue/4;
        double rightDrive = (3 * rightAxis/4) + rightDirection * throttleValue/4;

        System.out.println("LeftDrive: " + leftDrive);
        System.out.println("RightDrive: " + rightDrive);
        myDrive.tankDrive(leftDrive, rightDrive);

    }

// The encoder code
// deprecated
    public void MagEncoder() {
        leftLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        //this.leftEncoder = leftLeader.getSelectedSensorPosition(0);
        //this.rightEncoder = rightLeader.getSelectedSensorPosition(0);
        
    }

    // we only have the right encoder on the robot
    // the left encoder broke
    public void encoders() {
        rightLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        this.rightEncoder = rightLeader.getSelectedSensorPosition(0);
    }

// Puts the encoder values onto the Smartdashboard
    public void SmartDashboard() {
        SmartDashboard.putNumber("leftVelocity", leftEncoder);
        SmartDashboard.putNumber("rightVelocity", rightEncoder);
        //SmartDashboard.putNumber("leftDistanceInches", leftEncoderDistance());
        SmartDashboard.putNumber("rightDistanceInches", getRightEncoderDistance());
    }

// calculates the distance moved in inches
    /*public double leftEncoderDistance() {

        lEncoderDistance = (leftEncoder / 4096) * 6 * Math.PI;
        return lEncoderDistance;
    }
    */

    public double getRightEncoderDistance() {
        rightEncoder = rightLeader.getSelectedSensorPosition(0);
        rEncoderDistance = (rightEncoder / 4096) * 6 * Math.PI;
        return rEncoderDistance;
    }

// sets the encoder distance to 0
    public void resetEncoders() {
        //lEncoderDistance = 0.0;
        rEncoderDistance = 0.0;
        rightLeader.setSelectedSensorPosition(0);
    }

    double leftDrive = -0.95;
    double rightDrive = -0.95;

    public void chargeStraight(AHRS ahrs) {
        if(ahrs.getAngle() < -0.5) {
            //too far to the left
            rightDrive += .03;
            leftDrive -= .03;

        }
        else if(ahrs.getAngle() > 0.5) {
            //too far to the right
            rightDrive -= .03;
            leftDrive += .03;
        }
        else {
            rightDrive = -0.95;
            leftDrive = -0.95;
        }

        // if too fast or too slow
        // reset back to default
        if(leftDrive > -0.9) {
            leftDrive = -0.95;
        }
        else if(leftDrive < -1.0) {
            leftDrive = -0.95;
        }

        if(rightDrive > -0.9) {
            rightDrive = -0.95;
        }
        else if(rightDrive < -1.0) {
            rightDrive = -0.95;
        }

        leftLeader.set(leftDrive);
        rightLeader.set(rightDrive);
        
    }

}
