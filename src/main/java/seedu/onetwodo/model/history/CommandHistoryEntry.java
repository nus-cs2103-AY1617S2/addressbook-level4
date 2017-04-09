package seedu.onetwodo.model.history;

import java.util.ArrayList;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.task.ReadOnlyTask;

//@@author A0135739W
/**
 * Represents an entry of command history.
 */
public class CommandHistoryEntry implements CommandHistoryEntryInterface {
    public static final String COMMAND_FORMATTER = " %1$s";

    private ArrayList<String> entry;

    public CommandHistoryEntry (String commandWord) {
        entry = new ArrayList<String>();
        entry.add(commandWord);
    }

    public CommandHistoryEntry (String commandWord, ReadOnlyTask task) {
        entry = new ArrayList<String>();
        entry.add(commandWord);
        entry.add(String.format(COMMAND_FORMATTER, task));
    }

    public CommandHistoryEntry (String commandWord, ReadOnlyTask taskBeforeEdit, ReadOnlyTask taskAfterEdit) {
        entry = new ArrayList<String>();
        entry.add(commandWord);
        entry.add(String.format(COMMAND_FORMATTER, taskBeforeEdit));
        entry.add(String.format(COMMAND_FORMATTER, taskAfterEdit));
    }

    @Override
    public String getFeedbackMessage() {
        if (entry.size() == 1) {
            switch (entry.get(0)) {

            case ClearCommand.COMMAND_CLEAR_DONE:
                return ClearCommand.MESSAGE_CLEAR_DONE_SUCCESS;

            case ClearCommand.COMMAND_CLEAR_UNDONE:
                return ClearCommand.MESSAGE_CLEAR_UNDONE_SUCCESS;

            case ClearCommand.COMMAND_CLEAR_ALL:
            default:
                return ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS;
            }
        } else if (entry.size() == 2) {
            return entry.get(0).concat(entry.get(1));
        } else {
            return entry.get(0).concat(entry.get(2));
        }
    }

    @Override
    public String getFeedbackMessageInReverseCommand() {
        if (entry.size() == 1) {
            return getReverseCommand(entry.get(0));
        } else {
            return getReverseCommand(entry.get(0)).concat(entry.get(1));
        }
    }

    private String getReverseCommand(String commandWord) {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return DeleteCommand.COMMAND_WORD;

        case DeleteCommand.COMMAND_WORD:
            return AddCommand.COMMAND_WORD;

        case EditCommand.COMMAND_WORD:
            return "Restore Task";

        case DoneCommand.COMMAND_WORD:
            return UndoneCommand.COMMAND_WORD_CAP;

        case UndoneCommand.COMMAND_WORD:
            return DoneCommand.COMMAND_WORD;

        case ClearCommand.COMMAND_CLEAR_ALL:
            return ClearCommand.MESSAGE_UNDO_CLEAR_ALL_SUCCESS;

        case ClearCommand.COMMAND_CLEAR_DONE:
            return ClearCommand.MESSAGE_UNDO_CLEAR_DONE_SUCCESS;

        case ClearCommand.COMMAND_CLEAR_UNDONE:
            return ClearCommand.MESSAGE_UNDO_CLEAR_UNDONE_SUCCESS;

        default:
            return new String();
        }
    }
}
