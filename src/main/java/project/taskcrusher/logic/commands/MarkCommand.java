package project.taskcrusher.logic.commands;

import project.taskcrusher.commons.core.Messages;
import project.taskcrusher.commons.core.UnmodifiableObservableList;
import project.taskcrusher.logic.commands.exceptions.CommandException;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;

//@@author A0163639W
/**
 * Mark an active task/event as complete or incomplete.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Marks an event/task as complete or incomplete "
            + "by the index number used in the last event/task listing.\n"
            + "Parameters: t|e complete|incomplete INDEX (must be a positive integer)";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked task: %1$s!";
    public static final String MESSAGE_MARK_EVENT_SUCCESS = "Marked event: %1$s!";
    public static final int MARK_COMPLETE = 1;
    public static final int MARK_INCOMPLETE = 2;

    private final int filteredTargetIndex;
    private final int markFlag;
    private final String typeFlag;

    /**
     * @param filteredTargetIndex the index of the task/event in the filtered list to mark complete/incomplete
     * @param doneTaskDescriptor details to done the task with
     */
    public MarkCommand(String typeFlag, int filteredTaskListIndex, int markFlag) {
        assert filteredTaskListIndex > 0;
        assert markFlag == MARK_COMPLETE || markFlag == MARK_INCOMPLETE;

        this.markFlag = markFlag;
        this.typeFlag = typeFlag;
        this.filteredTargetIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (this.typeFlag.equals(Task.TASK_FLAG)) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (lastShownList.size() < filteredTargetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToMark = lastShownList.get(filteredTargetIndex);

            model.markTask(filteredTargetIndex, markFlag);
            model.updateFilteredTaskListToShowAll();
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));

        } else {
            UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

            if (lastShownList.size() < filteredTargetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            ReadOnlyEvent eventToMark = lastShownList.get(filteredTargetIndex);

            model.markEvent(filteredTargetIndex, markFlag);
            model.updateFilteredEventListToShowAll();
            return new CommandResult(String.format(MESSAGE_MARK_EVENT_SUCCESS, eventToMark));
        }
    }

}
