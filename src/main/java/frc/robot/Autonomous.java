package frc.robot;

import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
    AutoRobotAction useRobot;
    Timer timer;
    int autoStep;
    AHRS ahrs;
    //Ultrasonic ultrasonic;
    // AutoGyroAction useGyro;
    /* */

    public Autonomous(AutoRobotAction useRobot, AHRS ahrs, Timer timer/*, Ultrasonic ultrasonic*/) {

        
        //this.useGyro = useGyro;
        this.useRobot = useRobot;
        this.ahrs = ahrs;
        this.timer = timer;
        //this.ultrasonic = ultrasonic;
        autoStep = 0;
    
      }

    public void resetStep() {
        autoStep = 0;
    }

    public void taxiBackward() {
        if(autoStep == 0) {
            if(timer.get() < 5.0) {
                useRobot.DriveBack();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void taxiForward() {
        if(autoStep == 0) {
            if(timer.get() < 5.0) {
                useRobot.DriveForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void dumpEscapeDR() {
        if(autoStep == 0) {
            if(timer.get() < 2.0){
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1) {
            if(timer.get() < 5.0) {
                useRobot.DriveBack();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void dumpEscape() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 2) {
            if(timer.get() < 5.0) {
                useRobot.driveStraightBackward();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void DumpPickUp() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1) {
            if( timer.get() < 2.0) {
                useRobot.DriveBack();
            }            
            else {
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 2) {
            if(timer.get() < 5.0) {
                useRobot.rotateToAngle(180);
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 3) {
            if(timer.get() < 5.0) {
                useRobot.driveS3();
                useRobot.intakeForward();
            }
            else {
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    // start on the tarmac on the terminal side
    // within the tarmac the side closer to the opponent (right side)
    // the robot is right up against the hub
    // 1. dumps the ball into the hub
    // 2. drives out towards the ball
    public void terminalHubStart() {

        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1) {
            if(timer.get() < 2.0) {
                useRobot.DriveBack();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 2) {
            if(timer.get() < 5.0) {
                useRobot.rotateToAngle(160);
            }
            else {
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 3) {
            if(timer.get() < 5.0) {
                useRobot.driveS3();
            }
            else {
                autoStep = 4;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    // start on the tarmac on the hanger side
    // within the tarmac the side closer to the opponent (left side)
    // the robot is right up against the hub
    // 1. dumps the ball into the hub
    // 2. drives out towards the ball
    public void hangerHubStart() {
        if(autoStep == 0) {
            if(timer.get() < 2.0){
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1) {
            if(timer.get() < 2.0) {
                useRobot.DriveBack();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 2) {
            if(timer.get() < 5.0) {
                useRobot.rotateToAngle(-160);
            }
            else {
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 3) {
            if(timer.get() < 5.0) {
                useRobot.driveS3();
            }
            else {
                autoStep = 4;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        
    }

    // starts in tarmac closest to the left ball near the player terminal. 
    //Retrieves the ball and goes back to the hub to dump both balls 
    public void terminalOuterLeftBall(){
        if (autoStep == 0){
            if(timer.get() < 1.5){
                useRobot.DriveForward();
                
            }
            else{
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        


        }
        else if (autoStep == 1 ){
            if(timer.get() < 0.5){
                useRobot.intakeForward();
                useRobot.DriveForward();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }

        }
        else if(autoStep == 2 ){
            if (timer.get() < 5.0){
                useRobot.rotateToAngle(180);
            }
            else {
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 3){
            if (timer.get() < 3.0){
                useRobot.driveS3();
            }
            else {
                autoStep = 4;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 4){
            if ( timer.get() < 1.0){
                useRobot.rotateToAngle(-35);
            }
            else {
                autoStep = 5;
                timer.reset();
                timer.start();
                ahrs.reset();
            }

        }
        else if(autoStep == 5){
            if (timer.get() < 0.5){
                useRobot.DriveForwardSlow();
            }
            else{
                autoStep = 6;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        
        }
        else if (autoStep == 6){
            if(timer.get()< 2){
                useRobot.intakeForward();
            }
            else {
                autoStep =7;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }

            
    }

    public void terminalInnerRightBall(){
        if(autoStep == 0){
            if (timer.get() < 1.5){
                useRobot.DriveForward();
            }
            else{
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
         

        }
        else if(autoStep == 1){
            if(timer.get() <0.5){
                useRobot.intakeForward();
                useRobot.DriveForward();

            }
            else{
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }

            }
        else if(autoStep == 2){
            if(timer.get() < 5.0){
                useRobot.rotateToAngle(180);
            }
            else{
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 3){
            if(timer.get() < 3.0){
                useRobot.DriveForward();
            }          
            else {
                autoStep = 4;
                timer.reset();
                timer.start();
                ahrs.reset();
            }  
        }
        else if (autoStep == 4){
            if(timer.get() < 5.0){
                useRobot.rotateToAngle(20);
            }
            else {
                autoStep = 5;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 5){
            if(timer.get() < 2.0){
                useRobot.DriveForward();
            }
            else {
                autoStep = 6;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 6){
            if (timer.get() < 1.0){
                useRobot.intakeForward();
            }
            else {
                autoStep = 7;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void hangerOuterTarmacBall() {
        if(autoStep == 0){
            if(timer.get() < 1.5){
                useRobot.DriveForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1){
            if(timer.get() < 0.5){
                useRobot.intakeForward();
                useRobot.DriveForward();
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 2) {
            if (timer.get() < 5.0){
                useRobot.rotateToAngle(180);
            }
            else {
                autoStep = 3;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 3){
            if (timer.get() < 3.0){
                useRobot.DriveForward();
            }
            else {
                autoStep = 4;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 4 ){
            if (timer.get() < 3.0){
                useRobot.rotateToAngle(15);
            }
            else {
                autoStep = 5;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if (autoStep == 5){
            if(timer.get() < 1.0){
                useRobot.DriveForwardSlow();
            }
            else {
                autoStep = 6;
                timer.reset();
                timer.start();
                ahrs.reset();
            }

        
        }
        else if (autoStep == 6){
            if(timer.get() < 0.5){
                useRobot.intakeForward();
            }
            else {
                autoStep = 7;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void testDriveForward() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.DriveForward();
                System.out.println("here");
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void testIntakeForward() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.intakeForward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void testRotateToAngle() {
        if(autoStep == 0) {
            if(timer.get() < 5.0) {
                useRobot.rotateToAngle(-90);
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }
    
    public void testDriveBack() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.DriveBack();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
        else if(autoStep == 1) {
            if(timer.get() < 0.2) {
                useRobot.useTalon.leftLeader.set(0.2);
                useRobot.useTalon.rightLeader.set(0.2);
            }
            else {
                autoStep = 2;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void testDriveForwardSlow() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.DriveForwardSlow();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }

    public void testDriveS3() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.driveS3();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }

    }

    public void testDriveStraightBackward() {
        if(autoStep == 0) {
            if(timer.get() < 2.0) {
                useRobot.driveStraightBackward();
            }
            else {
                autoStep = 1;
                timer.reset();
                timer.start();
                ahrs.reset();
            }
        }
    }


}
