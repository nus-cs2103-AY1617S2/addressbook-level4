package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks the task as 'finished' identified using it's last displayed index from the to-do list.
 */
public class FinishCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("finished", "finish", "fin"));
    public static final String DEFAULT_COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords()
            + ": Marks the tasks as 'finished' identified by the index numbers used in the last task listing.\n"
            + "Parameters: INDEX [INDEX...] (must be a positive integer)\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " 1 8";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Finished Task: %1$s";

    public final int[] targetIndices;

    public FinishCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ArrayList<ReadOnlyTask> tasksToFinish = getMultipleTasksFromIndices(targetIndices);
        for (ReadOnlyTask task : tasksToFinish) {
            try {
                model.finishTask(task);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, tasksToFinish));
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
