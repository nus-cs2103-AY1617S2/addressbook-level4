//@@author A0131125Y
package seedu.toluist.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import seedu.toluist.ui.UiStore;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {
    /**
     * UiStore to store data to be used by Ui
     */
    protected final UiStore uiStore = UiStore.getInstance();

    /**
     * Given a command string, execute the command
     * and modifies the data appropriately. Also optionally
     * update the UI
     * @param tokens dictionary of tokens
     */
    public abstract void execute(HashMap<String, String> tokens);

    /**
     * Given command string, tokenize the string into
     * a dictionary of tokens
     * @param command
     * @return
     */
    public abstract HashMap<String, String> tokenize(String command);

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
    public HashMap<String, String[]> getCommandKeywordMap() {
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
