//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Responsible for redo-related task
 */
public class RedoController extends Controller {
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*redo(\\s+(?<number>\\d+))?\\s*";
    private static final String COMMAND_WORD = "redo";
    private static final String PARAMETER_REDO_TIMES = "number";
    private static final String RESULT_MESSAGE_TEMPLATE = "Your last undone %s %s re-applied.";

    private static final Logger logger = LogsCenter.getLogger(RedoController.class);

    public void execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String redoTimesToken = tokens.get(PARAMETER_REDO_TIMES);
        int redoTimes = redoTimesToken != null ? Integer.parseInt(redoTimesToken) : 1;

        Pair<TodoList, Integer> redoResult = TodoList.getInstance().getStorage().redo(redoTimes);
        TodoList todoList = TodoList.getInstance();
        todoList.setTasks(redoResult.getKey().getTasks());
        int actualRedoTimes = redoResult.getValue();

        uiStore.setTasks(todoList.getTasks());

        uiStore.setCommandResult(new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE,
                StringUtil.nounWithCount("change", actualRedoTimes),
                actualRedoTimes == 1 ? "was" : "were")));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_REDO_TIMES, matcher.group(PARAMETER_REDO_TIMES));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
