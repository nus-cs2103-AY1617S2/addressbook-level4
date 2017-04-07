package typetask.logic.commands;

//@@author A0139926R
/**
 * Undo recent command entered.
 * Undo commands that makes modification to data
 * E.g add,delete,edit,clear,done
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_WORD_SHORT = "u";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the most recent command." + "\n\nExample: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Restored previous command.";
    public static final String MESSAGE_FAILURE = "No previous command to undo";

    private static final int STATUS_EMPTY_HISTORY = 0;

    /**
     * Returns failure if there is no command executed before
     * Returns success if a command is found to be executed before this command
     */
    @Override
    public CommandResult execute() {
        switch (model.restoreTaskManager()) {
        case STATUS_EMPTY_HISTORY:
            return new CommandResult(MESSAGE_FAILURE);
        default:
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
