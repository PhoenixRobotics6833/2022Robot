package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;


// 8 inch height of opening
// 15 inch width of opening
// ball diameter 9.5 inch

public class Intake {
    WPI_TalonSRX intakeMotor;

    Joystick controller;
    double throttleValue;
    
    public Intake(int motor, Joystick controller2) {
        intakeMotor = new WPI_TalonSRX(motor);
        
        controller = controller2;
    }

    public void intakeControl() {
        // left trigger / reverse
        if(controller.getRawAxis(2) > 0.2) {
            intakeMotor.set(-controller.getRawAxis(2));
        } 
        // right trigger / forward
        else if(controller.getRawAxis(3) > 0.2) {
            intakeMotor.set(controller.getRawAxis(3));
        }
        // no button / do nothing
        else {
            intakeMotor.set(0.0);
        }

    }

}
