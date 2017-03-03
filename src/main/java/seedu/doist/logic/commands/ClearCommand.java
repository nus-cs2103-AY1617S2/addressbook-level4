package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.model.TodoList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {
	
	public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("clear"));
    public static final String DEFAULT_COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TodoList());
        return new CommandResult(MESSAGE_SUCCESS);
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
