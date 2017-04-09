//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.SwitchConfig;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Handle switch tab command
 */
public class SwitchController extends Controller {
    public static final String MESSAGE_RESULT_SWITCH_SUCCESS_FILTERED = "Switched to tab %s."
        + " Showing %d out of %d filtered tasks.";
    public static final String MESSAGE_RESULT_SWITCH_SUCCESS_ALL = "Switched to tab %s."
            + " Showing %d out of all %d existing tasks.";
    public static final String MESSAGE_RESULT_SWITCH_FAILURE = "%s is not a valid tab.";
    public static final String MESSAGE_RESULT_NO_TAB = "A tab to switch to was not provided";
    public static final String COMMAND_WORD = "switch";
    public static final String PARAMETER_TAB = "tab";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*switch(\\s+(?<tab>\\S+))?\\s*";
    private SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

    //@@author A0162011A
    private static final String HELP_DETAILS = "Changes the displayed task list.";
    private static final String HELP_FORMAT = "switch TABIDENTIFIER";
    private static final String[] HELP_COMMENTS = { "`TABIDENTIFIER` corresponds to the underlined letter "
                                                        + "at the top of the program.",
                                                    "Allowed values are `I`, `T`, `N`, `C`, `A`.",
                                                    "Alternatively, you can call them by their order: "
                                                        + "`1`, `2`, `3`, `4`, `5`",
                                                    "Alternatively, you can use the shortcut command: "
                                                        + "`ctrl` + (`1`/`2`/`3`/`4`/`5`)." };
    private static final String[] HELP_EXAMPLES = { "`switch 1`\nSwitches the screen to the `Incomplete` window.",
                                                    "`switch T`\nSwitches the screen to the `Today` window.",
                                                    "`ctrl` + `3`\nSwitches the screen to the `Next 7 Days` window." };

    //@@author A0131125Y
    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        String keyword = tokens.get(PARAMETER_TAB);

        validateNoTab(keyword);
        validateInvalidTab(keyword);

        switchTab(keyword);
    }

    private void switchTab(String keyword) {
        UiStore uiStore = UiStore.getInstance();
        TaskSwitchPredicate switchPredicate = switchConfig.getPredicate(keyword).get();
        String messageTemplate = uiStore.getTasks().size() == TodoList.getInstance().getTasks().size()
                ? MESSAGE_RESULT_SWITCH_SUCCESS_ALL
                : MESSAGE_RESULT_SWITCH_SUCCESS_FILTERED;
        UiStore.getInstance().setObservableSwitchPredicate(switchPredicate);

        uiStore.setCommandResult(new CommandResult(String.format(
                messageTemplate,
                switchPredicate.getDisplayName(),
                UiStore.getInstance().getShownTasks().size(),
                UiStore.getInstance().getTasks().size())));
    }

    private void validateInvalidTab(String keyword) throws InvalidCommandException {
        Optional<TaskSwitchPredicate> switchPredicateOptional = switchConfig.getPredicate(keyword);

        if (!switchPredicateOptional.isPresent()) {
            throw new InvalidCommandException(String.format(MESSAGE_RESULT_SWITCH_FAILURE, keyword));
        }
    }

    private void validateNoTab(String keyword) throws InvalidCommandException {
        if (keyword == null) {
            throw new InvalidCommandException(MESSAGE_RESULT_NO_TAB);
        }
    }

    public Map<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_TAB, matcher.group(PARAMETER_TAB));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public Map<String, String[]> getCommandKeywordMap() {
        Map<String, String[]> keywords = new HashMap<>();
        for (String tab : switchConfig.getAllKeys()) {
            keywords.put(tab, new String[0]);
        }
        return keywords;
    }

    public String[][][] getConflictingKeywordsList() {
        return new String[][][] { StringUtil.collectionToArrayOfArrays(switchConfig.getAllKeys()) };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
