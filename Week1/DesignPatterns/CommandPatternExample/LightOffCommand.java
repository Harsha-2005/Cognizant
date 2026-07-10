package CommandPatternExample;

/**
 * LightOffCommand - Concrete Command
 *
 * Encapsulates the request to turn a Light OFF.
 */
public class LightOffCommand implements Command {

    private final Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn(); // Undo turn-off by turning on
    }
}
