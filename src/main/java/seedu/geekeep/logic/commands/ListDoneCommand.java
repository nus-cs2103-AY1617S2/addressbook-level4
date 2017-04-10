//@@author A0139438W
package seedu.geekeep.logic.commands;

/**
 * Lists all completed tasks in GeeKeep to the user.
 */
public class ListDoneCommand extends Command {

    public static final String COMMAND_WORD = "listdone";
    public static final String MESSAGE_USAGE = "No argument should be given to listdone";
    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListToShowDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
