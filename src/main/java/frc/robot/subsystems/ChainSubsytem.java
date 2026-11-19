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
     private final SparkFlex spark = new SparkFlex(5, MotorType.kBrushless);


    private final SmartMotorControllerConfig smcConfig = new SmartMotorControllerConfig(this)
        .withControlMode(ControlMode.CLOSED_LOOP)
        .withClosedLoopController(50.0, 0.0, 20.0)
        .withSimClosedLoopController(50.0, 0.0, 20.0)
        .withFeedforward(new SimpleMotorFeedforward(0.0, 0.0, 0.0))
        .withSimFeedforward(new SimpleMotorFeedforward(0.0, 0.0, 0.0))
        .withGearing(new MechanismGearing(8))
        .withMomentOfInertia(Meters.of(1.0), Kilograms.of(3.0))
        .withMotorInverted(false)
        .withZeroPower(MotorMode.BRAKE)
        .withStatorCurrentLimit(Amps.of(40))
        .withStartingPosition(Degrees.of(0))
        .withTelemetry("ChainSubsystemMotor", TelemetryVerbosity.HIGH);
    

    private final SmartMotorController motor = new SparkWrapper(spark, DCMotor.getNeoVortex(1), smcConfig);

    private final PivotConfig pivotConfig = new PivotConfig()
        .withHardLimits(Degrees.of(0.0), Degrees.of(90.0))
        .withTekemetry("PivotSystem", TelemetryVerbosity.HIGH);

    private final Pivot pivot = new Pivot(pivotConfig, motor);
    //Brake Mode means motor will not move when idle

    public Command set(double dutyCycle) {
        return pivot.set(dutyCycle);
  }
    public Command run(Angle angle) {
        return pivot.run(angle);
  }


    
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
