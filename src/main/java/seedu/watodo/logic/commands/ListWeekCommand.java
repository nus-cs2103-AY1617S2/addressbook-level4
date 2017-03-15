package seedu.watodo.logic.commands;


/**
 * Lists all tasks scheduled on the current week in the task manager to the user.
 */
public class ListWeekCommand extends ListCommand {

    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_SUCCESS = "Listed this week's tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
