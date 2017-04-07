package seedu.taskboss.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

//@@author A0144904H
public class UnmarkCommand extends Command {

    private static final String NUMBERING_DOT = ". ";
    private static final int INDEX_ONE = 1;

    private static final int INDEX_ZERO = 0;
    public static final String COMMAND_WORD = "unmark";
    public static final String COMMAND_WORD_SHORT = "um";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Unmarks the tasks identified by the index"
            + " numbers provided that were used in the last listing. Reccuring task dates"
            + "will be updated aswell. "
            + "Parameters: LIST OF INDEXES (must be a positive integers)\n"
            + "Example: " + COMMAND_WORD
            + " 1 2 3" +  " || " + COMMAND_WORD_SHORT + " 1";

    public static final String MESSAGE_UNMARK_TASK_DONE_SUCCESS = "Task unmarked:\n%1$s";
    public static final String DONE = "Done";
    public static final String ERROR_NOT_MARKED = "The task was never "
            + "terminated or marked done previously";

    public final ArrayList<Integer> filteredTaskListIndices;
    public final ArrayList<ReadOnlyTask> tasksToUnmark;

    public UnmarkCommand(Set<Integer> targetIndex) {
        filteredTaskListIndices = new ArrayList<Integer>(targetIndex);
        Collections.sort(filteredTaskListIndices);
        Collections.reverse(filteredTaskListIndices);
        tasksToUnmark = new ArrayList<ReadOnlyTask>();
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndices.get(filteredTaskListIndices.size() - 1) < 1
                || filteredTaskListIndices.get(INDEX_ZERO) > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int index : filteredTaskListIndices) {
            ReadOnlyTask taskToUnmark = lastShownList.get(index - 1);
            if (!taskToUnmark.getCategories().contains(Category.done)) {
                throw new CommandException(ERROR_NOT_MARKED);
            }
            tasksToUnmark.add(taskToUnmark);
        }

        model.unmarkTask(filteredTaskListIndices, tasksToUnmark);

        scrollToTask(tasksToUnmark);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_DONE_SUCCESS, getDesiredTasksToMarkDoneFormat()));
    }

    /**
     * Scrolls to the position of the task with the lowest index
     * in the mark done list
     */
    private void scrollToTask(ArrayList<ReadOnlyTask> tasksToMarkDone) {
        ReadOnlyTask taskToMarkDone = tasksToMarkDone.get(INDEX_ZERO);
        UnmodifiableObservableList<ReadOnlyTask> latestShownList = model.getFilteredTaskList();
        int targetIndex = latestShownList.indexOf(taskToMarkDone);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
    }

    /**
     * Returns a formatted {@code ArrayList} tasksToMarkDone,
     * so that each ReadOnlyTask in the ArrayList is numbered
     */
    private String getDesiredTasksToMarkDoneFormat() {
        int i = INDEX_ONE;
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyTask task : tasksToUnmark) {
            builder.append(i + NUMBERING_DOT).append(task.toString());
            i++;
        }
        return builder.toString();
    }
}
