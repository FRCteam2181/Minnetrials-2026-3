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
        .withClosedLoopController(4, 0, 0)
    .withTrapezoidalProfile(DegreesPerSecond.of(180), DegreesPerSecondPerSecond.of(90))
      .withSoftLimits(Degrees.of(-30), Degrees.of(100))
      .withGearing(new MechanismGearing(GearBox.fromReductionStages(4, 4)))
//      .withExternalEncoder(armMotor.getAbsoluteEncoder())
      .withZeroPower(MotorMode.BRAKE)
      .withTelemetry("ArmMotor", TelemetryVerbosity.HIGH)
//      .withSpecificTelemetry("ArmMotor", motorTelemetryConfig)
      .withStatorCurrentLimit(Amps.of(40))
//      .withVoltageCompensation(Volts.of(12))
      .withMotorInverted(false)
      .withClosedLoopRampRate(Seconds.of(0.25))
      .withOpenLoopRampRate(Seconds.of(0.25))
      .withFeedforward(new ArmFeedforward(0, 0, 0, 0))
      .withControlMode(ControlMode.CLOSED_LOOP)
      .withExternalEncoder(cancoder)
      .withExternalEncoderGearing(0.5)
      .withUseExternalFeedbackEncoder(true)
      .withStartingPosition(Degrees.of(0))
      .withMomentOfInertia(Meters.of(0.135), Pounds.of(1));

    

    private final SmartMotorController motor = new SparkWrapper(spark, DCMotor.getNeoVortex(1), smcConfig);

    private final ArmConfig armConfig = new ArmConfig()
        .withHardLimits(Degrees.of(0.0), Degrees.of(90.0))
        .withLength(Feet.of(3))
        .withTelemetry("PivotSystem", TelemetryVerbosity.HIGH);

    private final Arm arm = new Arm(armConfig, motor);
    //Brake Mode means motor will not move when idle

    public Command set(double dutyCycle) {
        return arm.set(dutyCycle);
  }
    public Command run(Angle angle) {
        return arm.run(angle);
  }


    
    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    arm.updateTelemetry();
    }
    
    @Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    arm.simIterate();
    }

}
