package seedu.address.logic.commands;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Successfully undo previous change";
    public static final String MESSAGE_ERROR = "Cannot undo previous change";
    public static final String MESSAGE_NO_CHANGE = "No change to be undone";

    /**
     * Default empty constructor.
     */
    public UndoCommand() {
    }

    public CommandResult execute() {
        model.resetData(model.getCopy());
        model.updateCopy(model.getTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
