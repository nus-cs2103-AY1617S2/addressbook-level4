package seedu.toluist.ui.commons;

/**
 * Represents command input. To be used by Ui.
 */
public class CommandInput {
    private final String command;

    public CommandInput(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
