package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * Responsible for storage-related task
 */
public class UndoController extends Controller {
    private static final String COMMAND_TEMPLATE = "^undo(\\s+(?<number>\\d+))?\\s*";
    private static final String COMMAND_WORD = "undo";
    private static final String UNDO_TIMES = "number";
    private static final String RESULT_MESSAGE_TEMPLATE = "List undo-ed %d times";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public UndoController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String undoTimesToken = tokens.get(UNDO_TIMES);
        int undoTimes = undoTimesToken != null ? Integer.parseInt(undoTimesToken) : 1;

        Pair<TodoList, Integer> undoResult = TodoList.load().getStorage().undo(undoTimes);
        TodoList todoList = undoResult.getKey();
        int actualUndoTimes =  undoResult.getValue();

        uiStore.setTask(todoList.getTasks());
        renderer.render();

        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, actualUndoTimes));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(UNDO_TIMES, matcher.group(UNDO_TIMES));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
