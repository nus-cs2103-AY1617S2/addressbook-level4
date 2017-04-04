//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.KeywordTokenizer;
import seedu.toluist.ui.UiStore;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {
    public static final String DEFAULT_DESCRIPTION_KEYWORD = "description";

    /**
     * UiStore to store data to be used by Ui
     */
    protected final UiStore uiStore = UiStore.getInstance();

    /**
     * Given a command string, execute the command
     * and modifies the data appropriately. Also optionally
     * update the UI
     * @param tokens dictionary of tokens
     * @throws InvalidCommandException if some constraints fail
     */
    public abstract void execute(Map<String, String> tokens) throws InvalidCommandException;

    /**
     * Given command string, tokenize the string into
     * a dictionary of tokens
     * The default implementation uses keywordize to tokenize,
     * so controllers without keywords can override this to have their own tokenization logic
     * @param command
     * @return dictionary of tokens with value
     */
    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();
        for (Pair<String, String> tokenPair : keywordize(command)) {
            tokens.put(tokenPair.getKey(), tokenPair.getValue());
        }
        return tokens;
    }

    /**
     * Given command string, make that into a list of keywords,
     * in order of appearance in the commands
     * @param command
     * @return list of keywords and associated value
     */
    public List<Pair<String, String>> keywordize(String command) {
        if (!StringUtil.isPresent(command)) {
            return new ArrayList<>();
        }
        String commandWithoutCommandWord = StringUtil.replaceFirstWord(command, "");
        return KeywordTokenizer.tokenizeInOrder(commandWithoutCommandWord, DEFAULT_DESCRIPTION_KEYWORD,
                getCommandKeywordMap().keySet().toArray(new String[0]));
    }

    /**
     * Check if Controller can handle this command
     * @param command
     * @return true / false
     */
    public abstract boolean matchesCommand(String command);

    /**
     * Returns command word(s) used by controller
     */
    public String[] getCommandWords() {
        return new String[0];
    }

    /**
     * Returns mapping of keywords with possible values used by controller
     */
    public Map<String, String[]> getCommandKeywordMap() {
        return new HashMap<>();
    }

    /**
     * Returns array of groups of keywords that should not appear together
     * E.g /by & /from in add controller
     */
    public String[][][] getConflictingKeywordsList() {
        return new String[0][0][0];
    }

    /**
     * Return the set of all keywords that conflict with the current keyword
     * @param keyword a keyword
     * @return set of conflicting keywords
     */
    public final Set<String> getConflictingKeywords(String keyword) {
        return Arrays.stream(getConflictingKeywordsList())
                .filter(conflictingRule -> Arrays.stream(conflictingRule)
                        .flatMap(Arrays::stream)
                        .collect(Collectors.toList())
                        .contains(keyword))
                .map(conflictingRule -> Arrays.stream(conflictingRule)
                        .filter(group -> !Arrays.asList(group).contains(keyword))
                        .flatMap(Arrays::stream)
                        .collect(Collectors.toSet()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    //@@author A0162011A
    /**
     * Returns basic help command(s) used by help controller
     * Format is String[CommandWords, Format, Details]
     */
    public String[] getBasicHelp() {
        return new String[0];
    }

    /**
     * Returns detailed help command(s) used by help controller
     * Format is String[String[BasicHelp], String[Comments], String[Examples]]
     * Comments and Examples can be null, but at least one should be there to make this not pointless
     */
    public String[][] getDetailedHelp() {
        return new String[0][0];
    }
}
