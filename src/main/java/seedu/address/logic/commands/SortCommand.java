//@@author A0138377U
package seedu.address.logic.commands;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Lists all tasks in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of tasks according to the parameter indicated\n"
            + "Parameters: name|deadline\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public String cmd;
    private ArrayList<ReadOnlyTask> sortingList;

    public SortCommand(String args) {
        cmd = args;
    }

    private void sort() {

        int noOfTasks = model.getFilteredTasksSize();
        if (noOfTasks == 0) {
            return;
        }

        sortingList = model.getList();

        if ("name".equals(cmd)) {
            bubbleSortName(noOfTasks - 1);
        } else {
            bubbleSortDate(noOfTasks - 1);
        }
        ObservableList<ReadOnlyTask> sortedTaskList = createList();
        model.setList(sortedTaskList);
    }

    private ObservableList<ReadOnlyTask> createList() {
        ObservableList<ReadOnlyTask> tempList = FXCollections.observableArrayList();
        for (ReadOnlyTask task : sortingList) {
            tempList.add(task);
        }
        return tempList;
    }

    private void bubbleSortName(int upper) {
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int k = 0; k < upper; k++) {
                if (getCName(k).compareToIgnoreCase(getCName(k + 1)) > 0) {
                    exchange(k , k + 1);
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
                if (getCTime(k) < getCTime(k + 1)) {
                    exchange(k , k + 1);
                    flag = true;
                }
            }
        }
    }

    private String getCName(int index) {
        return sortingList.get(index).getName().toString();
    }

    private long getCTime(int index) {
        try {
            return sortingList.get(index).getDeadline().date.getBeginning().getTime();
        } catch (NullPointerException e) {
            return Long.MAX_VALUE;
        }
    }

    private void exchange(int i, int j) {
        ReadOnlyTask temp = sortingList.get(i);
        sortingList.set(i, sortingList.get(j));
        sortingList.set(j, temp);
    }

    @Override
    public CommandResult execute() {
        this.sort();
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS + " by " + cmd + ".");
    }
}
