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

    private void sort(String para) {
        int noOfTasks = model.getFilteredTasksSize();
        if (noOfTasks == 0) {
            return;
        }

        if ("name".equals(para)) {
            bubbleSortName(noOfTasks - 1);
        } else {
            bubbleSortDate(noOfTasks - 1);
        }

    }

    private void bubbleSortName(int upper) {
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int k = 0; k < upper; k++) {
                if (model.getCName(k).compareToIgnoreCase(model.getCName(k + 1)) > 0) {
                    model.exchange(k , k + 1);
                    flag = true;
                }
            }
        }
    }

    private void bubbleSortDate(int upper) {
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int k = 0; k < upper; k++) {
                if (model.getCTime(k) < model.getCTime(k + 1)) {
                    model.exchange(k , k + 1);
                    flag = true;
                }
            }
        }
    }

    @Override
    public CommandResult execute() {
        this.sort(cmd);
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS + " by " + cmd + ".");
    }
}
