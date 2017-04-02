//@@author A0138377U
package seedu.address.logic.commands;

/**
 * Lists all tasks in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of tasks by the parameter indicated\n"
            + "Parameters: name|deadline\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public String cmd;

    public SortCommand(String args) {
        cmd = args;
    }

    @Override
    public CommandResult execute() {
        model.sort(cmd);
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS + " by " + cmd + ".");
    }
}
