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


    double leftSpeed = -0.5;
    double rightSpeed = 0.5;
    double leftCompensation = 0.0;
    double rightCompensation = 0.0;
    double myDirection = 1;
    // drives straight forward or backward
    public void driveStraightEncoder(String direction) {

        if(direction == "forward") {
            myDirection = 1;
        }
        else if(direction == "backward") {
            myDirection = -1;
        }
        // because the max speed is 1.0, we only want to increase speed when 
        // the speed is not high
        // this would give us a max speed of 0.79 on each motor
        if(leftCompensation >= (-0.29 * myDirection) || rightCompensation <= (0.29 * myDirection)) {
            if(getLeftMotorVelocity() > getRightMotorVelocity()) {
                //drifting to the right
                leftCompensation += (0.01 * myDirection);
                rightCompensation += (0.01 * myDirection);
            }
            else if(getRightMotorVelocity() > getLeftMotorVelocity()) {
                //drifting to the left
                leftCompensation -= (0.01 * myDirection);
                rightCompensation -= (0.01 * myDirection);
            }
        }
        // if the speed is high, we want to decrease it.
        else {
            if(getLeftMotorVelocity() > getRightMotorVelocity()) {
                //drifting to the right
                leftCompensation += (0.01 * myDirection);
            }
            else if(getRightMotorVelocity() > getLeftMotorVelocity()) {
                //drifting to the left
                rightCompensation -= (0.01 * myDirection);
            }
        }
        useTalon.leftLeader.set((leftSpeed + leftCompensation) * myDirection);
        useTalon.rightLeader.set((rightSpeed + rightCompensation) * myDirection); 

    }

    // get the value of left motor velocity (negative because the motor is backward)
    public double getLeftMotorVelocity() {
        return -useTalon.leftLeader.getSelectedSensorVelocity();
    }

    // get value of the right motor velocity
    public double getRightMotorVelocity() {
        return useTalon.rightLeader.getSelectedSensorVelocity();
    }

    double startingAngle;
    double targetAngle;
    boolean isTurning = false;
    boolean isDone = false;
    // turn a set number of degrees, positive or negative
    public void turnDegrees(double degrees) {
        if(!isDone){ 
            if(!isTurning){
                startingAngle = ahrs.getAngle();
                isTurning = true;
            }
            targetAngle = startingAngle + degrees;

            //turning to the left
            if(targetAngle < startingAngle) {
                useTalon.leftLeader.set(0.5);
                useTalon.rightLeader.set(0.5);
                if(ahrs.getAngle() <= targetAngle){
                    useTalon.leftLeader.set(0.0);
                    useTalon.rightLeader.set(0.0);
                    isTurning = false;
                    isDone = true;
                } 
            }
            // turning to the right
            else if(targetAngle > startingAngle){
                useTalon.leftLeader.set(-0.5);
                useTalon.rightLeader.set(-0.5);
                if(ahrs.getAngle() >= targetAngle) {
                    useTalon.leftLeader.set(0.0);
                    useTalon.rightLeader.set(0.0);
                    isTurning = false;
                    isDone = true;
                }
            }
        }
    }
    public void resetTurning() {
        isDone = false;
    }

    boolean isMoving = false;
    // drives forwards (+) or backwards (-) in inches
    public void driveDistance(double distance) {
        if(!isMoving){
            useTalon.resetEncoderDistance();
            isMoving = true;
       }
       // move forward
       if(distance > 0){
            if((useTalon.leftEncoderDistance() +  useTalon.rightEncoderDistance() / 2) <= distance){
                driveStraightEncoder("forward");
            }
            else {
                useTalon.leftLeader.set(0.0);
                useTalon.rightLeader.set(0.0);
            }
        }
        // move backward
        else if(distance < 0){
            if((useTalon.leftEncoderDistance() +  useTalon.rightEncoderDistance() / 2) >= distance){
                driveStraightEncoder("backward");
            }
            else {
                useTalon.leftLeader.set(0.0);
                useTalon.rightLeader.set(0.0);
            }
        }
    }
    public void resetDriveDistance() {
        isMoving = false;
    }
}
