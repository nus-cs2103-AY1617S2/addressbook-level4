//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.SwitchConfig;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Handle switch tab command
 */
public class SwitchController extends Controller {
    public static final String RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED = "Switched to tab %s."
        + " Showing %d out of %d filtered tasks.";
    public static final String RESULT_MESSAGE_SWITCH_SUCCESS_ALL = "Switched to tab %s."
            + " Showing %d out of all %d existing tasks.";
    public static final String RESULT_MESSAGE_SWITCH_FAILURE = "%s is not a valid tab.";
    public static final String RESULT_MESSAGE_NO_TAB = "A tab to switch to was not provided";
    public static final String COMMAND_WORD = "switch";
    public static final String PARAMETER_TAB = "tab";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*switch(\\s+(?<tab>\\S+))?\\s*";
    private SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

    private static final String HELP_DETAILS = "Changes the displayed task list.";
    private static final String HELP_FORMAT = "switch WINDOWIDENTIFIER";

    public void execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String keyword = tokens.get(PARAMETER_TAB);

        if (keyword == null) {
            uiStore.setCommandResult(new CommandResult(RESULT_MESSAGE_NO_TAB));
            return;
        }

        Optional<TaskSwitchPredicate> switchPredicateOptional = switchConfig.getPredicate(keyword);

        if (!switchPredicateOptional.isPresent()) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_SWITCH_FAILURE, keyword)));
            return;
        }

        String messageTemplate = uiStore.getTasks().size() == TodoList.getInstance().getTasks().size()
                ? RESULT_MESSAGE_SWITCH_SUCCESS_ALL
                : RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED;
        TaskSwitchPredicate switchPredicate = switchPredicateOptional.get();
        UiStore.getInstance().setObservableSwitchPredicate(switchPredicate);

        uiStore.setCommandResult(new CommandResult(String.format(
                messageTemplate,
                switchPredicate.getDisplayName(),
                UiStore.getInstance().getShownTasks().size(),
                UiStore.getInstance().getTasks().size())));
    }

    public HashMap<String, String> tokenize(String command) {
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

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    //@@author A0162011A
    public static String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_DETAILS, HELP_FORMAT };
    }

    public static String[] getDetailedHelp() {
        return getBasicHelp();
    }
}
