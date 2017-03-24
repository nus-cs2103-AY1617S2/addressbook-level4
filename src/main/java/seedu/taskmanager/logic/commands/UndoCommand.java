package seedu.taskmanager.logic.commands;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful";
    public static final String MESSAGE_NO_MORE_UNDO = "No more actions available to undo";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
