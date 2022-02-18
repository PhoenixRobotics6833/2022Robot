package frc.robot;
import com.kauailabs.navx.frc.AHRS;


public class AutoRobotAction {
    
    //Intake useIntake;
    DriveTrain useTalon;
    Intake useIntake;
    AHRS ahrs;

    public AutoRobotAction(Intake intake, DriveTrain driveTrain, AHRS ahrs) {
        
        this.useTalon = driveTrain;
        this.useIntake = intake;
        this.ahrs = ahrs;

    }

    
    public void intakeForward(){
        
        useIntake.intakeMotor.set(0.8);
    }
    
    public void IntakeReverse() {

        useIntake.intakeMotor.set(-0.8);
    }
    
    public void IntakeStop() {

        useIntake.intakeMotor.set(0.0);
    }

    public void DriveForward() {
        
        useTalon.leftLeader.set(-0.35);
        useTalon.rightLeader.set(0.35);

    }

    public void DriveForwardSlow() {

        useTalon.leftLeader.set(-0.25);
        useTalon.rightLeader.set(0.25);

    }

    public void DriveBack() {

        useTalon.leftLeader.set(.35);
        useTalon.rightLeader.set(-.35);

    }

    public void DriveOff() {

        useTalon.leftLeader.set(0.0);
        useTalon.rightLeader.set(0.0);

    }



    double leftDrive = -.2;
    double rightDrive = .2;
    double straightError;
    double straightTarget;
    public double straightCurrentAngle;
    double straightOutput;

    public void driveStraightInit() {
        straightTarget = 0;
    }

    public void driveStraight() {
        straightError = Math.abs(straightTarget - ahrs.getAngle()) / 360;
        System.out.println(ahrs.getAngle());

        straightOutput = .7 * Math.sqrt(straightError);

        if(ahrs.getAngle() < 0.5) {
            //too far to the left
            rightDrive += .03;
            leftDrive += .03;
            System.out.println("drifting to the left");
        } else if(ahrs.getAngle() > 0.5) {
            //too far to the left
            rightDrive -= .03;
            leftDrive -= .03;
            System.out.println("drifting to the right");
        } else {
            rightDrive = .3;
            leftDrive = -.3;
        }

        if(leftDrive > .3) {
            leftDrive = .3;
        } else if(leftDrive < -.3) {
            leftDrive = -.3;
        }
        if(rightDrive > .3) {
            rightDrive = .3;
        } else if(rightDrive < -.3) {
            rightDrive = -.3;
        }

        useTalon.leftLeader.set(leftDrive);
        useTalon.rightLeader.set(rightDrive);

    }

    double rightEffector;
    double leftEffector;
    boolean leftSide;
    boolean rightSide;
    public void driveS2() {
        leftDrive = -.4;
        rightDrive = .45;
        if(rightSide) {
            leftEffector = 0;
        } else if(leftSide) {
            rightEffector = 0;
        }

        if(ahrs.getAngle() < 0) {
            //too far to the left
            rightEffector -= .01;
            leftEffector += .01;
            System.out.println("drifting to the left");
            leftSide = true;
        } else if(ahrs.getAngle() > 0) {
            //too far to the left
            leftEffector -= .01;
            rightEffector += .01;
            System.out.println("drifting to the right");
            rightSide = true;
        }

        useTalon.leftLeader.set(leftDrive + leftEffector);
        useTalon.rightLeader.set(rightDrive + rightEffector);
    }
}
