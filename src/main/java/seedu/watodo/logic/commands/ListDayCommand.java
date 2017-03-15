package seedu.watodo.logic.commands;


/**
 * Lists all tasks scheduled on the current day in the task manager to the user.
 */
public class ListDayCommand extends ListCommand {

    public static final String COMMAND_WORD = "day";

    public static final String MESSAGE_SUCCESS = "Listed today's tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
