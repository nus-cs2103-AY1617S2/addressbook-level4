package seedu.bulletjournal.logic.commands;

import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.model.HistoryManager;

// @@author A0146738U-reused

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful: ";
    public static final String MESSAGE_NO_MORE_UNDO = "No more actions available to undo";

    @Override
    public CommandResult execute() throws CommandException {
        HistoryManager historyManager = HistoryManager.getInstance();
        String commandText;
        try {
            commandText = historyManager.undo();
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NO_MORE_UNDO);
        }
        return new CommandResult(MESSAGE_SUCCESS + commandText);
    }
}
