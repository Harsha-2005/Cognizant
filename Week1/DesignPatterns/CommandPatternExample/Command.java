package CommandPatternExample;

/**
 * Command - Command Interface
 *
 * Defines the contract for all command objects.
 * All commands must implement execute() and optionally undo().
 */
public interface Command {
    void execute();
    void undo();
}
