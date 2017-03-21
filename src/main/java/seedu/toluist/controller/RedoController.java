package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;

/**
 * Responsible for redo-related task
 */
public class RedoController extends Controller {
    private static final String COMMAND_TEMPLATE = "^redo(\\s+(?<number>\\d+))?\\s*";
    private static final String COMMAND_WORD = "redo";
    private static final String REDO_TIMES = "number";
    private static final String RESULT_MESSAGE_TEMPLATE = "List redo-ed %d times";

    private static final Logger logger = LogsCenter.getLogger(RedoController.class);

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String redoTimesToken = tokens.get(REDO_TIMES);
        int redoTimes = redoTimesToken != null ? Integer.parseInt(redoTimesToken) : 1;

        Pair<TodoList, Integer> redoResult = TodoList.load().getStorage().redo(redoTimes);
        TodoList todoList = redoResult.getKey();
        int actualRedoTimes = redoResult.getValue();

        uiStore.setTasks(todoList.getTasks());

        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, actualRedoTimes));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(REDO_TIMES, matcher.group(REDO_TIMES));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
