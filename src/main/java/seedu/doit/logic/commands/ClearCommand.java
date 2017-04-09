package seedu.doit.logic.commands;

//@@author A0138909R
/**
 * Clears all the tasks.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_PARAMETER = "";
    public static final String COMMAND_RESULT = "Deletes all tasks from DoIT";
    public static final String COMMAND_EXAMPLE = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks has been cleared!";

    @Override
    public CommandResult execute() {
        assert this.model != null;
        this.model.clearData();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }
}
