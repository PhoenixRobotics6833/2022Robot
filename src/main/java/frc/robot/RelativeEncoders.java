package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RelativeEncoders {
   
    Encoder leftEncoder;
    Encoder rightEncoder;
    double pulsesPerRevolution = 0.0 ;
    double wheelDiameterInches = 0.0 ;

    public RelativeEncoders(int lChannelA, int lChannelB, int rChannelA, int rChannelB){
        
        leftEncoder = new Encoder(lChannelA, lChannelB);
        rightEncoder = new Encoder(rChannelA, rChannelB);
        
        leftEncoder.setDistancePerPulse((Math.PI * wheelDiameterInches) / pulsesPerRevolution);
        rightEncoder.setDistancePerPulse((Math.PI * wheelDiameterInches) / pulsesPerRevolution);
        resetEncoders();

    }

    public void resetEncoders() {

        leftEncoder.reset();
        rightEncoder.reset();

    }

    public int getLeftEncoderCount() {
        return leftEncoder.get();
      }
    
    public int getRightEncoderCount() {
        return rightEncoder.get();
    }
    
    public double getLeftDistanceInch() {
        return leftEncoder.getDistance();
    }
    
    public double getRightDistanceInch() {
        return rightEncoder.getDistance();
    }
    
    public double getAverageDistanceInch() {
        return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
    }

    public void SmartDashboard() {

        SmartDashboard.putNumber("lEncoderCount", getLeftEncoderCount());
        SmartDashboard.putNumber("lEncoderDistanceInch", getLeftDistanceInch());
        SmartDashboard.putNumber("rEncoderCount", getRightEncoderCount());
        SmartDashboard.putNumber("rEncoderDistance",getRightDistanceInch());
        
    }

    
}
