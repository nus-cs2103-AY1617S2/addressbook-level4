package seedu.toluist.controller;

import seedu.toluist.controller.commons.SwitchConfig;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handle switch tab command
 */
public class SwitchController extends Controller {
    public static final String RESULT_MESSAGE_SWITCH_SUCCESS = "Switched to tab \"%s\"."
        + " Showing %d out of %d filtered tasks.";
    public static final String RESULT_MESSAGE_SWITCH_FAILURE = "\"%s\" is not a valid tab.";
    private static final String COMMAND_TEMPLATE = "switch\\s+(?<tab>\\S+)\\s*";
    private static final String COMMAND_WORD = "switch";
    public static final String TAB = "tab";
    private SwitchConfig switchConfig = SwitchConfig.getDefaultSwitchConfig();

    public SwitchController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String keyword = tokens.get(TAB);
        Optional<TaskSwitchPredicate> switchPredicateOptional = switchConfig.getPredicate(keyword);

        if (!switchPredicateOptional.isPresent()) {
            return new CommandResult(String.format(RESULT_MESSAGE_SWITCH_FAILURE, keyword));
        }

        TaskSwitchPredicate switchPredicate = switchPredicateOptional.get();
        UiStore.getInstance().setSwitchPredicate(switchPredicate);
        renderer.render();
        return new CommandResult(String.format(
                RESULT_MESSAGE_SWITCH_SUCCESS,
                switchPredicate.getDisplayName(),
                UiStore.getInstance().getTasks().stream().filter(switchPredicate.getPredicate()).collect(
                        Collectors.toList()).size(),
                UiStore.getInstance().getTasks().size()));
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
