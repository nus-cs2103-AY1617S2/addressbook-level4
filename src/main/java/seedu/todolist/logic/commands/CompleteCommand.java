package seedu.todolist.logic.commands;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.util.Date;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.todo.ReadOnlyTodo;

/**
 * Completes a todo identified using its last displayed index from the todo list
 */
public class CompleteCommand extends Command {
    //@@author A0163786N
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes todo.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TODO_SUCCESS = "[Completed]: %1$s";

    public static final String MESSAGE_TODO_ALREADY_COMPLETE = "This todo is already complete";

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
        this.completeTime = StringUtil.parseDate(completeTime, DATE_FORMAT);
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
