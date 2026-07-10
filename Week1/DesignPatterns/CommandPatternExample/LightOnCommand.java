package CommandPatternExample;

/**
 * LightOnCommand - Concrete Command
 *
 * Encapsulates the request to turn a Light ON.
 */
public class LightOnCommand implements Command {

    private final Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff(); // Undo turn-on by turning off
    }
}
