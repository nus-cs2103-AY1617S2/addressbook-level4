package w10b3.todolist.logic.commands;

import java.util.ArrayList;

import javafx.util.Pair;
import w10b3.todolist.commons.core.Messages;
import w10b3.todolist.commons.core.UnmodifiableObservableList;
import w10b3.todolist.logic.commands.exceptions.CommandException;
import w10b3.todolist.model.ReadOnlyToDoList;
import w10b3.todolist.model.ToDoList;
import w10b3.todolist.model.task.ReadOnlyTask;
import w10b3.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address
 * book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final Pair<Character, Integer> targetIndex;

    private ReadOnlyToDoList originalToDoList;
    private CommandResult commandResultToUndo;

    public DeleteCommand(Pair<Character, Integer> targetIndex) {
        this.targetIndex = targetIndex;
    }

    // @@ A0143648Y
    @Override
    public CommandResult execute() throws CommandException {
        originalToDoList = new ToDoList(model.getToDoList());

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getListFromChar(targetIndex.getKey());

        if (lastShownList.size() < targetIndex.getValue()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = lastShownList.get(targetIndex.getValue() - 1);

        try {
            model.deleteTask(personToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        commandResultToUndo = new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
        updateUndoLists();

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
    }

    @Override
    public void updateUndoLists() {
        if (previousToDoLists == null) {
            previousToDoLists = new ArrayList<ReadOnlyToDoList>(3);
            previousCommandResults = new ArrayList<CommandResult>(3);
        }
        if (previousToDoLists.size() >= 3) {
            previousToDoLists.remove(0);
            previousCommandResults.remove(0);
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        } else {
            previousToDoLists.add(originalToDoList);
            previousCommandResults.add(commandResultToUndo);
        }
    }

}
