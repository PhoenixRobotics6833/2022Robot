package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
    DriveTrain useTalons;
    AutoRobotAction useRobot;
    Timer timer;
    int autoStep;
    AHRS ahrs;
    // AutoGyroAction useGyro;
    /* */

    public Autonomous(/*AutoGyroAction useGyro,*/ AutoRobotAction useRobot, AHRS ahrs, Timer timer) {

        
        //this.useGyro = useGyro;
        this.useRobot = useRobot;
        this.ahrs = ahrs;
        this.timer = timer;
        autoStep = 0;
    
      }

    public void resetStep() {
        autoStep = 0;
    }
}
