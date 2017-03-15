package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.UniqueTaskList.TaskAlreadyFinishedException;
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

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Finished Tasks: %1$s";
    public static final String MESSAGE_TASK_ALREADY_FINISHED = "Tasks already finished: %1$s";

    public final int[] targetIndices;

    public FinishCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String outputMessage = "";

        ArrayList<ReadOnlyTask> tasksToFinish = getMultipleTasksFromIndices(targetIndices);
        ArrayList<ReadOnlyTask> tasksFinished = new ArrayList<ReadOnlyTask>();
        ArrayList<ReadOnlyTask> tasksAlreadyFinished = new ArrayList<ReadOnlyTask>();

        for (ReadOnlyTask task : tasksToFinish) {
            try {
                model.finishTask(task);
                tasksFinished.add(task);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            } catch (TaskAlreadyFinishedException e) {
                tasksAlreadyFinished.add(task);
            }
        }
        if (!tasksAlreadyFinished.isEmpty()) {
            outputMessage += String.format(MESSAGE_TASK_ALREADY_FINISHED, tasksAlreadyFinished + "\n");
        }
        if (!tasksFinished.isEmpty()) {
            outputMessage += String.format(MESSAGE_FINISH_TASK_SUCCESS, tasksFinished);
        }
        return new CommandResult(outputMessage);
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
