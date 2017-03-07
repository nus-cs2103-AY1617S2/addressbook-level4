package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.UniqueTodoList.TodoNotFoundException;

/**
 * Deletes a todo identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the todo identified by the index number used in the last todo listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Todo: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTodo> lastShownList = model.getFilteredTodoList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        ReadOnlyTodo todoToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTodo(todoToDelete);
        } catch (TodoNotFoundException pnfe) {
            assert false : "The target todo cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, todoToDelete));
    }

}
