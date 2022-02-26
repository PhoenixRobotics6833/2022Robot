package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
    AutoRobotAction useRobot;
    Timer timer;
    int autoStep;
    AHRS ahrs;
    // AutoGyroAction useGyro;
    /* */

    public Autonomous(AutoRobotAction useRobot, AHRS ahrs, Timer timer) {

        
        //this.useGyro = useGyro;
        this.useRobot = useRobot;
        this.ahrs = ahrs;
        this.timer = timer;
        autoStep = 0;
    
      }

    public void resetStep() {
        autoStep = 0;
    }

    public void DumpEscapeDR() {
        if(autoStep == 0) {
            if(timer.get() < 2.0){
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
            }
        }
    }
}
