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
import seedu.taskboss.model.task.ReadOnlyTask;

//@@author A0144904H
public class MarkDoneCommand extends Command {

    private static final int INDEX_ZERO = 0;
    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_WORD_SHORT = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Marks the tasks identified by the indexes"
            + " numbers provided that are used in the last listing as done. "
            + "Parameters: LIST OF INDEXES (must be a positive integers)\n"
            + "Example: " + COMMAND_WORD
            + " 1 2 3" +  " || " + COMMAND_WORD_SHORT + " 1";

    public static final String MESSAGE_MARK_TASK_DONE_SUCCESS = "Task marked done: %1$s";
    public static final String DONE = "Done";

    public final ArrayList<Integer> filteredTaskListIndices;
    public final ArrayList<ReadOnlyTask> tasksToMarkDone;

    public MarkDoneCommand(Set<Integer> targetIndex) {
        this.filteredTaskListIndices = new ArrayList<Integer>(targetIndex);
        Collections.sort(this.filteredTaskListIndices);
        this.tasksToMarkDone = new ArrayList<ReadOnlyTask>();
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (this.filteredTaskListIndices.get(filteredTaskListIndices.size() - 1) > lastShownList.size()
                || this.filteredTaskListIndices.get(INDEX_ZERO) < 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int index : this.filteredTaskListIndices) {
            this.tasksToMarkDone.add(lastShownList.get(index - 1));
        }

        model.markDone(this.filteredTaskListIndices, tasksToMarkDone);

        scrollToTask(tasksToMarkDone);
        return new CommandResult(String.format(MESSAGE_MARK_TASK_DONE_SUCCESS, tasksToMarkDone));
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

}
