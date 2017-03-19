package seedu.doist.logic.commands;

import java.util.ArrayList;

public class CommandInfo {
    public ArrayList<String> commandWords;
    public String defaultCommandWord;

    public CommandInfo(ArrayList<String> commandWords, String defaultCommandWord) {
        this.commandWords = commandWords;
        this.defaultCommandWord = defaultCommandWord;
    }

    /**
     * @return a string containing all the command words to be shown in the
     *         usage message, in the format of (word1|word2|...)
     */
    protected String getUsageTextForCommandWords() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (!commandWords.contains(defaultCommandWord)) {
            sb.append(defaultCommandWord + "|");
        }
        for (String commandWord : commandWords) {
            sb.append(commandWord + "|");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    //public boolean canBeTriggeredByWord(String word) {
    //    return commandWords.contains(word) || defaultCommandWord.equals(word);
    //}
}
