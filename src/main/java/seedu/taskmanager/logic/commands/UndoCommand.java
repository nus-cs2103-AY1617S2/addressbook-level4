package seedu.taskmanager.logic.commands;

import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.HistoryManager;

// @@author A0140032E
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful: %1$s";
    public static final String MESSAGE_NO_MORE_UNDO = "No more actions available to undo";

    @Override
    public CommandResult execute() throws CommandException {
        HistoryManager historyManager = HistoryManager.getInstance();
        String commandText;
        try {
            commandText = historyManager.undo();
            //highlightChanges(commandText);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_NO_MORE_UNDO);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandText));
    }
}
// @@author
