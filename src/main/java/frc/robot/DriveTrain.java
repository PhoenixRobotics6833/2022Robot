package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

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
    double gearRatio = 72 / 12;

    Timer timer;

    public DriveTrain(int motor1, int motor2, int motor3, int motor4, int motor5, int motor6, Joystick controller1) {
        leftLeader = new WPI_TalonSRX(motor1);
        leftFollower = new WPI_TalonSRX(motor2);
        leftFollower2 = new WPI_TalonSRX(motor3);
        rightLeader = new WPI_TalonSRX(motor4);
        rightFollower = new WPI_TalonSRX(motor5);
        rightFollower2 = new WPI_TalonSRX(motor6);
        
        //timer = new Timer();

        controller = controller1;

        leftFollower.follow(leftLeader);
        leftFollower2.follow(leftLeader);
        rightFollower.follow(rightLeader);
        rightFollower2.follow(rightLeader);

        myDrive = new DifferentialDrive(leftLeader, rightLeader);

        //timer.start();
        //resetEncoderDistance();
    }
    
    public void TalonDrive() {

        throttleValue = controller.getRawAxis(throttleAxis);

        leftStick = controller.getRawAxis(1) / (2 - throttleValue);
        rightStick = controller.getRawAxis(5) / (2 - throttleValue);
        myDrive.tankDrive(leftStick, -rightStick);
    }

    public void TalonDriveNoLimiter() {
        double leftAxis = controller.getRawAxis(1);
        double rightAxis = controller.getRawAxis(5);

       

        myDrive.tankDrive(Math.atan(leftAxis * (Math.PI/2)), -Math.atan(rightAxis * (Math.PI/2));
    }

// The encoder code
    public void MagEncoder() {
        leftLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        rightLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        this.leftEncoder = leftLeader.getSelectedSensorVelocity(0);
        this.rightEncoder = rightLeader.getSelectedSensorVelocity(0);
    }

// Puts the encoder values onto the Smartdashboard
    public void SmartDashboard() {
        SmartDashboard.putNumber("leftVelocity", leftEncoder);
        SmartDashboard.putNumber("rightVelocity", rightEncoder);
        SmartDashboard.putNumber("leftDistanceInches", leftEncoderDistance());
        SmartDashboard.putNumber("rightDistanceInches", rightEncoderDistance());
    }

// calculates the distance moved
    public double leftEncoderDistance() {

        if(timer.advanceIfElapsed(0.1)) {
            lEncoderDistance += (-leftEncoder / 4096) * (6 * Math.PI) / gearRatio;
        }
        return lEncoderDistance;
    }

    public double rightEncoderDistance() {

        if(timer.advanceIfElapsed(0.1)) {
            rEncoderDistance += (rightEncoder/4096) * (6 * Math.PI) / gearRatio;
        }
        return rEncoderDistance;
    }

// sets the encoder distance to 0
    public void resetEncoderDistance() {
        lEncoderDistance = 0.0;
        rEncoderDistance = 0.0;
        timer.reset();
    }

}
