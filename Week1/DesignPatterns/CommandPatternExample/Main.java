package CommandPatternExample;

/**
 * Main - Tests the Command Pattern for the home automation system.
 *
 * The RemoteControl (Invoker) knows nothing about the Light (Receiver).
 * It only interacts through the Command interface — decoupling sender from receiver.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Command Pattern - Home Automation Demo ===\n");

        // Create Receivers
        Light livingRoomLight = new Light("Living Room");
        Light bedroomLight    = new Light("Bedroom");

        // Create Commands
        Command livingRoomOn  = new LightOnCommand(livingRoomLight);
        Command livingRoomOff = new LightOffCommand(livingRoomLight);
        Command bedroomOn     = new LightOnCommand(bedroomLight);
        Command bedroomOff    = new LightOffCommand(bedroomLight);

        // Create Invoker
        RemoteControl remote = new RemoteControl();

        System.out.println("--- Pressing buttons on the remote ---");
        remote.pressButton(livingRoomOn);
        remote.pressButton(bedroomOn);
        remote.pressButton(livingRoomOff);

        System.out.println("\n--- Undo last command (Living Room Off → On) ---");
        remote.pressUndo();

        System.out.println("\n--- Undo again (Bedroom On → Off) ---");
        remote.pressUndo();

        System.out.println("\n--- Undo again (Living Room On → Off) ---");
        remote.pressUndo();

        System.out.println("\n--- Undo when history is empty ---");
        remote.pressUndo();

        System.out.println("\n--- Press bedroom off ---");
        remote.pressButton(bedroomOff);
    }
}
