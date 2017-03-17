package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.UniqueTodoList.TodoNotFoundException;

/**
 * Completes a todo identified using its last displayed index from the todo list
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes last action if it involves modifying a todo.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TODO_SUCCESS = "Completed Todo: %1$s";

    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTodo> lastShownList = model.getFilteredTodoList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        ReadOnlyTodo todoToComplete = lastShownList.get(targetIndex - 1);

        try {
            model.completeTodo(todoToComplete);
        } catch (TodoNotFoundException tnfe) {
            assert false : "The target todo cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_TODO_SUCCESS, todoToComplete));
    }

}
