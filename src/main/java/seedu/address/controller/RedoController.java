package seedu.address.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;

/**
 * Responsible for redo-related task
 */
public class RedoController extends Controller {
    private static final String COMMAND_TEMPLATE = "^redo( (?<number>\\d+))?";
    private static final String REDO_TIMES = "number";
    private static final String COMMAND_RESULT_TEMPLATE = "List redo-ed %d times";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String redoTimesToken = tokens.get(REDO_TIMES);
        int redoTimes = redoTimesToken != null ? Integer.parseInt(redoTimesToken) : 1;

        Pair<TodoList, Integer> redoResult = storage.redo(redoTimes);
        TodoList todoList = redoResult.getKey();
        int actualRedoTimes = redoResult.getValue();

        uiStore.setTask(todoList.getTasks());
        renderer.render();

        return new CommandResult(String.format(COMMAND_RESULT_TEMPLATE, actualRedoTimes));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(REDO_TIMES, matcher.group(REDO_TIMES));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }
}
