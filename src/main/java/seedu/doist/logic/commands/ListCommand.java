package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

	public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("list", "ls"));
    public static final String DEFAULT_COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
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
    	return commandWords.contains(word) || DEFAULT_COMMAND_WORD == word;
    }
}
