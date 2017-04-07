package seedu.bulletjournal.logic.commands;

import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.model.HistoryManager;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo successful: ";
    public static final String MESSAGE_NO_MORE_REDO = "No more actions available to redo";

    @Override
    public CommandResult execute() throws CommandException {
        HistoryManager historyManager = HistoryManager.getInstance();
        String commandText;
        try {
            commandText = historyManager.redo();
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NO_MORE_REDO);
        }
        return new CommandResult(MESSAGE_SUCCESS + commandText);
    }
}
