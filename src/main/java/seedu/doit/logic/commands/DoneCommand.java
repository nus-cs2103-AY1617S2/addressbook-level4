package seedu.doit.logic.commands;

//@@author A0146809W
/**
 * Lists all completed tasks in the task manager to the user.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final String COMMAND_PARAMETER = "";
    public static final String COMMAND_RESULT = "Displays all completed tasks";
    public static final String COMMAND_EXAMPLE = "done";
    public static final String MESSAGE_SUCCESS = "Listed all done tasks";


    @Override
    public CommandResult execute() {
        this.model.updateFilteredListToShowDone();
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
