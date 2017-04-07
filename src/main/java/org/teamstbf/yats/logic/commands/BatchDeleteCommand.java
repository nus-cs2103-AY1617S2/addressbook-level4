package org.teamstbf.yats.logic.commands;

import java.util.Stack;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

//@@author A0138952W
public class BatchDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tasks identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted %d Tasks";

    public final Stack<Integer> targetIndexes;

    public BatchDeleteCommand(Stack<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();
        int numOfTask = targetIndexes.size();
        model.saveImageOfCurrentTaskManager();
        for (int i = 0; i < numOfTask; i++) {

            if (lastShownList.size() < targetIndexes.peek()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyEvent taskToDelete = lastShownList.get(targetIndexes.pop() - 1);

            try {
                model.deleteEvent(taskToDelete);
            } catch (EventNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, numOfTask));
    }
}
