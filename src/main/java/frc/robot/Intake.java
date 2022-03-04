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
        // throttle right bumper
        if(controller.getRawButton(6) == true){
            throttleValue = 0.2;
        }
        else {
            throttleValue = 0.0;
        }
        // left trigger / forward 
        if(controller.getRawAxis(2) > 0.5) {
            intakeMotor.set(0.8 + throttleValue);
        } 
        // right trigger / reverse
        else if(controller.getRawAxis(3) > 0.5) {
            intakeMotor.set(-(0.8 + throttleValue));
        }
        // no button / do nothing
        else {
            intakeMotor.set(0.0);
        }

    }

}
