package savvytodo.logic.commands;

import java.util.LinkedList;
import java.util.List;

import savvytodo.commons.core.Messages;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Status;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0140016B
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_FORMAT = "mark INDEX [MORE_INDEX]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the tasks identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s\n";
    public static final String MESSAGE_MARK_TASK_FAIL = "Task %1$s is already marked!\n";

    public final int[] targetIndices;

    public MarkCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute()  throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        LinkedList<Integer> targettedIndices = new LinkedList<Integer>();
        LinkedList<Task> tasksToMark = new LinkedList<Task>();
        for (int targetIndex : targetIndices) {
            int filteredTaskListIndex = targetIndex - 1;
            if (filteredTaskListIndex >= lastShownList.size() || filteredTaskListIndex < 0) {
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            targettedIndices.add(targetIndex);
            tasksToMark.add((Task) lastShownList.get(filteredTaskListIndex));
        }

        StringBuilder resultSb = new StringBuilder();
        try {
            for (Task taskToMark : tasksToMark) {
                if (taskToMark.isCompleted().value) {
                    resultSb.append(String.format(MESSAGE_MARK_TASK_FAIL, targettedIndices.peekFirst()));
                } else {
                    taskToMark.setStatus(new Status(true));
                    model.updateTask(targettedIndices.peekFirst() - 1, taskToMark);
                    resultSb.append(String.format(MESSAGE_MARK_TASK_SUCCESS, targettedIndices.peekFirst()));
                }
                targettedIndices.removeFirst();
            }
        } catch (DuplicateTaskException e) {
            //ignore for completed
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(resultSb.toString());
    }

}
//@@author A0140016B
