package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.Joystick;



public class Intake {
    PWMVictorSPX intakeMotor;

    Joystick controller;
    double throttleValue;
    
    public Intake(int motor, Joystick controller2) {
        intakeMotor = new PWMVictorSPX(motor);
        
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
