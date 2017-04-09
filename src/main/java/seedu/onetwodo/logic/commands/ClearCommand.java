package seedu.onetwodo.logic.commands;

import seedu.onetwodo.model.DoneStatus;

//@@author A0135739W
/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_CLEAR_ALL = "clear" + " " + DoneStatus.ALL_STRING;
    public static final String COMMAND_CLEAR_DONE = "clear" + " " + DoneStatus.DONE_STRING;
    public static final String COMMAND_CLEAR_UNDONE = "clear" + " " + DoneStatus.UNDONE_STRING;
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "All tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_ALL_SUCCESS = "OneTwoDo is restored!";
    public static final String MESSAGE_CLEAR_DONE_SUCCESS = "All completed tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_DONE_SUCCESS = "All completed tasks have been restored";
    public static final String MESSAGE_CLEAR_UNDONE_SUCCESS = "All uncompleted tasks have been removed!";
    public static final String MESSAGE_UNDO_CLEAR_UNDONE_SUCCESS = "All uncompleted tasks have been restored!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes all tasks or all done tasks.\n"
            + "Parameters: all, done or empty\n"
            + "Example: " + COMMAND_WORD + " " + DoneStatus.DONE_STRING;

    private String commandArgument;

    public ClearCommand (String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        String messageToReturn;

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
        return new CommandResult(messageToReturn);
    }
}
