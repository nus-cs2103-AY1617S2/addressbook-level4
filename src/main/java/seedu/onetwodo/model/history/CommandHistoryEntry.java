package seedu.onetwodo.model.history;

import java.util.ArrayList;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.model.task.ReadOnlyTask;

public class CommandHistoryEntry implements CommandHistoryEntryInterface {
    private static final String COMMAND_FORMATTER = " %1$s";

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

    public CommandHistoryEntry (String commandWord, String taskString) {
        entry = new ArrayList<String>();
        entry.add(commandWord);
        entry.add(taskString);
    }

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

        case "Restore Task":
            return EditCommand.COMMAND_WORD;

        case EditCommand.COMMAND_WORD:
            return "Restore Task";

        case DoneCommand.COMMAND_WORD:
            return "Undone";

        case "Undone":
            return DoneCommand.COMMAND_WORD;

        case ClearCommand.COMMAND_WORD:
            return "Restore OneTwoDo";

        case "Restore OneTwoDo":
            return ClearCommand.COMMAND_WORD;

        default:
            return new String();
        }
    }
}
