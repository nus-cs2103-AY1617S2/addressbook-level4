package seedu.task.logic.commands;


/**
 * Lists all task in KIT to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD_1 = "list";
    public static final String COMMAND_WORD_2 = "ls";
    public static final String COMMAND_WORD_3 = "l";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;


    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
