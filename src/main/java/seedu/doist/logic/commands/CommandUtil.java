package seedu.doist.logic.commands;

import java.util.ArrayList;

public class CommandUtil {
    
    /**
     * @return a string containing all the command words to be shown in the usage message, in the format of (word1|word2|...)
     */
    protected static String getUsageTextForCommandWords(ArrayList<String> commandWords, String defaultCommandWord) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("(");
    	if (!commandWords.contains(defaultCommandWord)) {
    		sb.append(defaultCommandWord + "|");
    	}
    	for (String commandWord: commandWords) {
    		sb.append(commandWord + "|");
    	}
    	sb.setCharAt(sb.length() - 1, ')');
    	return sb.toString();
    }
    
    public static boolean canCommandBeTriggeredByWord(ArrayList<String> commandWords, String defaultCommandWord, String word) {
    	return commandWords.contains(word) || defaultCommandWord == word;
    }
}
