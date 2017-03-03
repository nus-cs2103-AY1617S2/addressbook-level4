package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.core.Messages;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("delete"));
    public static final String DEFAULT_COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = getUsageTextForCommandWords()
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(personToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * @return a string containing all the command words to be shown in the usage message, in the format of (word1|word2|...)
     */
    protected static String getUsageTextForCommandWords() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (!commandWords.contains(DEFAULT_COMMAND_WORD)) {
            sb.append(DEFAULT_COMMAND_WORD + "|");
        }
        for (String commandWord: commandWords) {
            sb.append(commandWord + "|");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }
    
    public static boolean canCommandBeTriggeredByWord(String word) {
        return commandWords.contains(word) || DEFAULT_COMMAND_WORD.equals(word);
    }
}
