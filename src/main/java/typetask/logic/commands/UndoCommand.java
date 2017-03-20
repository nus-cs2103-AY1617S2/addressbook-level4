package typetask.logic.commands;

import typetask.commons.core.Messages;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_WORD_SHORT = "u";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the most recent command." + "\n\nExample: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Restored previous command.";
    public static final String MESSAGE_FAILURE = "An error occured when running undo command.";
    public static final String MESSAGE_EMPTY_HISTORY = "There is nothing to undo.";

    private static final int STATUS_EMPTY_HISTORY = 0;
    private static final int STATUS_ERROR_HISTORY = -1;

    public UndoCommand() {

    }

    @Override
    public CommandResult execute() {
        switch (model.restoreTaskManager()) {
            case STATUS_ERROR_HISTORY:
                return new CommandResult(MESSAGE_FAILURE);
            case STATUS_EMPTY_HISTORY:
                return new CommandResult(MESSAGE_FAILURE);
            default:
                return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
