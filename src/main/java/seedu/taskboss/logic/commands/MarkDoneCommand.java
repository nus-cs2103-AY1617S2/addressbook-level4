package seedu.taskboss.logic.commands;

import java.util.List;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;

//@@author A0144904H
public class MarkDoneCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_WORD_SHORT = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Marks the task identified by the index"
            + " number used in the last listing as done. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD
            + " 1" +  " || " + COMMAND_WORD_SHORT + " 1";

    public static final String MESSAGE_MARK_TASK_DONE_SUCCESS = "Task marked done: %1$s";

    private final int filteredTaskListIndex;

    public MarkDoneCommand(int index) {
        this.filteredTaskListIndex = index - 1;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkDone = lastShownList.get(filteredTaskListIndex);
        //@@author A0143157J
        if (taskToMarkDone.isRecurring()) {
            Task newRecurredTask = new Task(taskToMarkDone);
            newRecurredTask.getRecurrence().updateTaskDates(newRecurredTask);
            model.updateTask(filteredTaskListIndex, newRecurredTask);
        } else {
            Task taskMarked = new Task(taskToMarkDone.getName(), taskToMarkDone.getPriorityLevel(),
                taskToMarkDone.getStartDateTime(), taskToMarkDone.getEndDateTime(),
                taskToMarkDone.getInformation(), taskToMarkDone.getRecurrence(),
                new UniqueCategoryList("Done"));
            model.updateTask(filteredTaskListIndex, taskMarked);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_DONE_SUCCESS, taskToMarkDone));
    }

}
