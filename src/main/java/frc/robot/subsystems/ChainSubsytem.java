package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

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
    


//  private SmartMotorControllerConfig smcConfig =
//                 new SmartMotorControllerConfig(this)
//           .withControlMode(ControlMode.CLOSED_LOOP)
//           // Feedback Constants (PID Constants)
//         //   .withClosedLoopController(1, 0, 0)
//         //   .withSimClosedLoopController(1, 0, 0)
//         //   // Feedforward Constants
//         //   .withFeedforward(new SimpleMotorFeedforward(0, 0, 0))
//         //   .withSimFeedforward(new SimpleMotorFeedforward(0, 0, 0))
//         //   // Telemetry name and verbosity level
//         //   .withTelemetry("Top Intake Roller Motor", Telemetry.telemetryVerbosity.yamsVerbosity)
//           // Gearing from the motor rotor to final shaft.
//           // In this example GearBox.fromReductionStages(3,4) is the same as
//           // GearBox.fromStages("3:1","4:1") which corresponds to the gearbox attached to your
//           // motor.
//           // You could also use .withGearing(12) which does the same thing.
//           .withGearing(new MechanismGearing(GearBox.fromReductionStages(5)))
//           // Motor properties to prevent over currenting.
//           .withMotorInverted(false)
//           .withIdleMode(MotorMode.COAST);
//         //   .withStatorCurrentLimit(Amps.of(60));

  private SparkFlex chain = new SparkFlex(5, MotorType.kBrushless);
  private SmartMotorControllerConfig config = new SmartMotorControllerConfig(this).withGearing(14)
        .withMotorInverted(false)
        .withIdleMode(MotorMode.COAST);
    private SparkWrapper chainMotorController = new SparkWrapper(chain, DCMotor.getNEO(1),  config);
//    private SmartMotorController chainMotor =
//          new SparkWrapper(chain, DCMotor.getNEO(1), smcConfig);

//            private final FlyWheelConfig chainMotorConfig = 
//            new FlyWheelConfig(chainMotor)
//           // Diameter of the flywheel.
//           .withDiameter(Inches.of(4))
//           // Mass of the flywheel.
//           .withMass(Pounds.of(1))
//           // Maximum speed of the shooter.
//           .withUpperSoftLimit(RPM.of(1000))
//           // Telemetry name and verbosity for the arm.
//           .withTelemetry("Top Intake Roller", Telemetry.telemetryVerbosity.yamsVerbosity);

//   private FlyWheel topIntakeWheel = new FlyWheel(topIntakeConfig);

  /**
   * Set the shooter velocity.
   *
   * @param speed Speed to set.
   * @return {@link edu.wpi.first.wpilibj2.command.RunCommand}
//    */
//   public Command settopVelocity(AngularVelocity speed) {
//     return topIntakeWheel.setSpeed(speed);
//   }

  /* Set the dutycycle of the shooter.
   *
   * @param dutyCycle DutyCycle to set.
   * @return {@link edu.wpi.first.wpilibj2.command.RunCommand}
   */
  public Command set(double dutyCycle) {
    return chainMotorController.;
  }

  /** Creates a new ExampleSubsystem. */
  public TopIntakeSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    topIntakeWheel.updateTelemetry();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    topIntakeWheel.simIterate();
  }
}