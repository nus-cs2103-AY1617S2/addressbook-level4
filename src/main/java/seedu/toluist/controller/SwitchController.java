package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.SwitchConfig;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

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
    public static final String TAB = "tab";
    private static final String COMMAND_TEMPLATE = "switch(\\s+(?<tab>\\S+))?\\s*";
    private SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

    public SwitchController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String keyword = tokens.get(TAB);

        if (keyword == null) {
            return new CommandResult(RESULT_MESSAGE_NO_TAB);
        }

        Optional<TaskSwitchPredicate> switchPredicateOptional = switchConfig.getPredicate(keyword);

        if (!switchPredicateOptional.isPresent()) {
            return new CommandResult(String.format(RESULT_MESSAGE_SWITCH_FAILURE, keyword));
        }

        String messageTemplate = uiStore.getTasks().size() == TodoList.load().getTasks().size()
                ? RESULT_MESSAGE_SWITCH_SUCCESS_ALL
                : RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED;
        TaskSwitchPredicate switchPredicate = switchPredicateOptional.get();
        UiStore.getInstance().setSwitchPredicate(switchPredicate);
        renderer.render();
        return new CommandResult(String.format(
                messageTemplate,
                switchPredicate.getDisplayName(),
                UiStore.getInstance().getShownTasks().stream().filter(switchPredicate.getPredicate()).collect(
                        Collectors.toList()).size(),
                UiStore.getInstance().getShownTasks().size()));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TAB, matcher.group(TAB));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
