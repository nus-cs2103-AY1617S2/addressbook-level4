package seedu.doit.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_PARAMETER = "";
    public static final String COMMAND_RESULT = "Lists all uncompleted tasks";
    public static final String COMMAND_EXAMPLE = "list";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        this.model.updateFilteredListToShowAll();
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
