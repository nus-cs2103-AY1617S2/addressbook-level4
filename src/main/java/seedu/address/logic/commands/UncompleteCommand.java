package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ReadOnlyTodo;

/**
 * Uncompletes a todo identified using its last displayed index from the todo list
 */
public class UncompleteCommand extends Command {

    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uncompletes todo.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_TODO_SUCCESS = "Uncompleted Todo: %1$s";

    public static final String MESSAGE_TODO_NOT_COMPLETE = "This todo is not complete";

    public final int filteredTodoListIndex;

    public UncompleteCommand(int targetIndex) {
        // convert index from 1 based to 0 based
        this.filteredTodoListIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTodo> lastShownList = model.getFilteredTodoList();

        if (lastShownList.size() <= filteredTodoListIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        ReadOnlyTodo todoToUncomplete = lastShownList.get(filteredTodoListIndex);

        if (todoToUncomplete.getCompleteTime() == null) {
            throw new CommandException(MESSAGE_TODO_NOT_COMPLETE);
        }

        model.uncompleteTodo(filteredTodoListIndex);

        return new CommandResult(String.format(MESSAGE_UNCOMPLETE_TODO_SUCCESS, todoToUncomplete));
    }

}
