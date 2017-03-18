package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ReadOnlyTodo;

/**
 * Completes a todo identified using its last displayed index from the todo list
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes todo.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TODO_SUCCESS = "Completed Todo: %1$s";

    public static final String MESSAGE_TODO_ALREADY_COMPLETE = "This todo is already complete";

    public static final String COMPLETE_TIME_FORMAT = "h:mma dd/MM/yyyy";

    public final int filteredTodoListIndex;
    public final Date completeTime;

    public CompleteCommand(int targetIndex) {
        // convert index from 1 based to 0 based
        this.filteredTodoListIndex = targetIndex - 1;
        this.completeTime = new Date();
    }

    public CompleteCommand(int targetIndex, String completeTime) throws IllegalValueException {
     // convert index from 1 based to 0 based
        this.filteredTodoListIndex = targetIndex - 1;
        this.completeTime = StringUtil.parseDate(completeTime, COMPLETE_TIME_FORMAT);
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTodo> lastShownList = model.getFilteredTodoList();

        if (lastShownList.size() <= filteredTodoListIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        ReadOnlyTodo todoToComplete = lastShownList.get(filteredTodoListIndex);

        if (todoToComplete.getCompleteTime() != null) {
            throw new CommandException(MESSAGE_TODO_ALREADY_COMPLETE);
        }

        model.completeTodo(filteredTodoListIndex, completeTime);

        return new CommandResult(String.format(MESSAGE_COMPLETE_TODO_SUCCESS, todoToComplete));
    }

}
