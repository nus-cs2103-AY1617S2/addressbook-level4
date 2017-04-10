package org.teamstbf.yats.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

//@@author A0138952W
public class BatchUnmarkDoneCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "%d task marked as not done";
    public static final String MESSAGE_ALR_MARKED = "Task is already marked as not done.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as not done "
            + "by the index number used in the last task listing. " + "\n"
            + "Parameters: INDEX (must be a positive integer) " + "\n" + "Example: " + COMMAND_WORD + " 1 2 3";

    private static final String TASK_DONE_IDENTIFIER = "Yes";

    public final Stack<Integer> targetIndexes;

    public BatchUnmarkDoneCommand(Stack<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyEvent> lastShownList = retrieveDoneTaskList();
        int numOfTask = targetIndexes.size();
        model.saveImageOfCurrentTaskManager();

        for (int i = 0; i < numOfTask; i++) {

            if (lastShownList.size() < targetIndexes.peek()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyEvent taskToMark = lastShownList.get(targetIndexes.peek());

            Event markedTask = new Event(taskToMark.getTitle(), taskToMark.getLocation(), taskToMark.getStartTime(),
                    taskToMark.getEndTime(), taskToMark.getDeadline(), taskToMark.getDescription(),
                    taskToMark.getTags(), new IsDone("Yes"), taskToMark.isRecurring(), taskToMark.getRecurrence());

            if (markedTask.getIsDone().getValue().equals(IsDone.ISDONE_NOTDONE)) {
                return new CommandResult(MESSAGE_ALR_MARKED);
            }

            markedTask.getIsDone().markUndone();
            model.updateEvent(targetIndexes.pop(), markedTask);
            model.updateDoneTaskList();
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, numOfTask));
    }

    private List<ReadOnlyEvent> retrieveDoneTaskList() {
        Set<String> doneTaskIdentifier = new HashSet<String>();
        doneTaskIdentifier.add(TASK_DONE_IDENTIFIER);
        model.updateFilteredListToShowDone(doneTaskIdentifier);
        return model.getFilteredTaskList();
    }

}
