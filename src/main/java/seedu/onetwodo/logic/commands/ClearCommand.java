package seedu.onetwodo.logic.commands;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.DoneStatus;

//@@author A0135739W
/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_CLEAR_ALL = COMMAND_WORD + " " + DoneStatus.ALL_STRING;
    public static final String COMMAND_CLEAR_DONE = COMMAND_WORD + " " + DoneStatus.DONE_STRING;
    public static final String COMMAND_CLEAR_UNDONE = COMMAND_WORD + " " + DoneStatus.UNDONE_STRING;
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "All tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_ALL_SUCCESS = "OneTwoDo is restored!";
    public static final String MESSAGE_CLEAR_DONE_SUCCESS = "All completed tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_DONE_SUCCESS = "All completed tasks have been restored";
    public static final String MESSAGE_CLEAR_UNDONE_SUCCESS = "All uncompleted tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_UNDONE_SUCCESS = "All uncompleted tasks have been restored!";
    public static final String MESSAGE_NO_MORE_TASK = "OneTwoDo is already cleared.";
    public static final String MESSAGE_NO_MORE_DONE_TASK = "No more completed tasks to be cleared.";
    public static final String MESSAGE_NO_MORE_UNDONE_TASK = "No more uncompleted tasks to be cleared.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes all tasks or all done tasks.\n"
            + "Parameters: all, done or empty\n"
            + "Example: " + COMMAND_WORD + " " + DoneStatus.DONE_STRING;

    private String commandArgument;

    public ClearCommand (String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        String messageToReturn;

        try {
            switch (commandArgument) {
            case DoneStatus.DONE_STRING:
                model.clearDone();
                messageToReturn = MESSAGE_CLEAR_DONE_SUCCESS;
                break;

            case DoneStatus.UNDONE_STRING:
                model.clearUndone();
                messageToReturn = MESSAGE_CLEAR_UNDONE_SUCCESS;
                break;

            case DoneStatus.ALL_STRING:
            default:
                model.clear();
                messageToReturn = MESSAGE_CLEAR_ALL_SUCCESS;
            }
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        return new CommandResult(messageToReturn);
    }
}
