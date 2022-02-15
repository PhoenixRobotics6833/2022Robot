package frc.robot;

//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
    WPI_TalonSRX leftLeader;
    WPI_TalonSRX rightLeader;
    WPI_TalonSRX leftFollower;
    WPI_TalonSRX rightFollower;

    Joystick controller;

    DifferentialDrive myDrive;

    double throttleValue;
    //right trigger boosts speed
    int throttleAxis = 3;

    double leftStick;
    double rightStick;

    //double leftEncoder;
    //double rightEncoder;

    public DriveTrain(int motor1, int motor2, int motor3, int motor4, Joystick controller1) {
        leftLeader = new WPI_TalonSRX(motor1);
        leftFollower = new WPI_TalonSRX(motor2);
        rightLeader = new WPI_TalonSRX(motor3);
        rightFollower = new WPI_TalonSRX(motor4);
        
        controller = controller1;

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        myDrive = new DifferentialDrive(leftLeader, rightLeader);
    }

    public void TalonDrive() {

        throttleValue = controller.getRawAxis(throttleAxis);

        leftStick = controller.getRawAxis(1) / (2 - throttleValue);
        rightStick = controller.getRawAxis(5) / (2 - throttleValue);
    }

    public void TalonDriveNoLimiter() {
        myDrive.tankDrive(controller.getRawAxis(1) * 10, controller.getRawAxis(5) * 10);
    }
/* The encoder code, encoders not present yet
    public void MagEncoder() {
        leftLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        rightLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        this.leftEncoder = leftLeader.getSelectedSensorVelocity(0);
        this.rightEncoder = rightLeader.getSelectedSensorVelocity(0);
    }
*/

/* Puts the encoder values onto the Smartdashboard
    public void SmartDashboard() {
        SmartDashboard.putNumber("left", leftEncoder);
        SmartDashboard.putNumber("right", rightEncoder);
    }
*/
}
