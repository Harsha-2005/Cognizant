package CommandPatternExample;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * RemoteControl - Invoker Class
 *
 * The invoker does NOT know anything about how commands are executed.
 * It simply holds a Command reference and calls execute() on it.
 * Also maintains a history stack to support undo operations.
 */
public class RemoteControl {

    private final Deque<Command> commandHistory = new ArrayDeque<>();

    /**
     * Executes a command and pushes it onto the history stack for undo support.
     * @param command The command to execute
     */
    public void pressButton(Command command) {
        command.execute();
        commandHistory.push(command); // Save to history for undo
    }

    /**
     * Undoes the most recently executed command.
     */
    public void pressUndo() {
        if (commandHistory.isEmpty()) {
            System.out.println("[RemoteControl] Nothing to undo.");
            return;
        }
        Command lastCommand = commandHistory.pop();
        System.out.println("[RemoteControl] Undoing last command...");
        lastCommand.undo();
    }
}
