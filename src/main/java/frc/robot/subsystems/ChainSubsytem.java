package frc.robot.subsystems;

import java.io.ObjectInputFilter.Config;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import yams.gearing.GearBox;
import yams.gearing.MechanismGearing;
import yams.mechanisms.config.FlyWheelConfig;
import yams.mechanisms.velocity.FlyWheel;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.local.SparkWrapper;

public class ChainSubsytem extends SubsystemBase {
    //Create new SparkFlex motorcontroller with the can ID of 5 and type of brushless
     SparkFlex chain = new SparkFlex(5, MotorType.kBrushless);

     SparkFlexConfig config = new SparkFlexConfig();
    //Brake Mode means motor will not move when idle

    public ChainSubsytem(){
        chain.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        config.idleMode(IdleMode.kBrake);
        config.smartCurrentLimit(60);
    }

    
    
    

    //ResetSafeParameters -> Resets old settings Persisting -> Saves to correct memory
    

    public void run(double dutyCycle) {
        chain.set(dutyCycle);
    }
    public void stop(){
        chain.set(0);
    }

    public Command runMotor(double dutyCycle){
        return runEnd(() -> {run(dutyCycle);}, 
                           () -> {stop();});
    }

    /** Creates a new ExampleSubsystem. */
    // public ChainSubsystem() {}
    
    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    chain.updateTelemetry();
    }
    
    @Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    ChainSubsytem.simIterate();
    }

}
