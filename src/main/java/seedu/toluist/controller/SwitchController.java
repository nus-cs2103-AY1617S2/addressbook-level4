package seedu.toluist.controller;

import seedu.toluist.controller.commons.SwitchConfig;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TaskSwitchPredicate;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handle switch tab command
 */
public class SwitchController extends Controller {
    private static final String RESULT_MESSAGE = "Switch switch";
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
        TaskSwitchPredicate predicate = switchConfig.getPredicate(keyword).get();
        UiStore.getInstance().setSwitchPredicate(predicate);
        renderer.render();
        return new CommandResult(RESULT_MESSAGE);
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
