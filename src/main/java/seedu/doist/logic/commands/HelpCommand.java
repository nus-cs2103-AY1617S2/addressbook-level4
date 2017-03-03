package seedu.doist.logic.commands;


import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

	public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("help"));
    public static final String DEFAULT_COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = getUsageTextForCommandWords() 
    		+ ": Shows program usage instructions.\n"
            + "Example: " + DEFAULT_COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
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
