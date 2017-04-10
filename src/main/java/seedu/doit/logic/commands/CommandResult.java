//@@author A0146809W

package seedu.doit.logic.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.doit.model.item.ReadOnlyTask;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

    /**
     * Returns a string with the task index attached to task name for every task
     *
     * @param tasksSet Set of tasks
     * @return A formatted string with the task index attached to the corresponding task
     */
    public static String tasksToString(HashSet<ReadOnlyTask> tasksSet) {

        final StringBuilder builder = new StringBuilder();

        List<ReadOnlyTask> tasksList = new ArrayList<>(tasksSet);

        //from 1st task to 2nd last task
        for (int i = 0; i < tasksList.size() - 1; i++) {
            builder.append(tasksList.get(i).getName());
            builder.append(", ");
        }

        //last task
        builder.append(tasksList.get(tasksList.size() - 1).getName());

        return builder.toString();
    }

}
