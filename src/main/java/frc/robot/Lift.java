package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;

public class Lift {

    Servo servoLeft;
    Servo servoRight;
    Joystick controller;

    public Lift(int servo1, int servo2, Joystick controller2) {

        servoLeft = new Servo(servo1);
        servoRight = new Servo(servo2);

        controller = controller2;

    }

    public void liftControl() {

        // unlock the left servo
        // button x
        if(controller.getRawButton(3)) {
            servoLeft.setAngle(90);
        }

        // unlock the right servo
        // button B
        if(controller.getRawButton(2)) {
            servoRight.setAngle(90);

        }

        // lock both servos
        // button Y
        if(controller.getRawButton(4)) {
            servoLeft.setAngle(0);
            servoRight.setAngle(0);
        }

        // unlock both servos
        // button A
        if(controller.getRawButton(1)) {
            servoLeft.setAngle(90);
            servoRight.setAngle(90);
        }


    }
    
}
