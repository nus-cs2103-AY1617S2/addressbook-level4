package seedu.address.controller;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;

/**
 * Responsible for storage-related task
 */
public class UndoController extends Controller {
    private static final String COMMAND_TEMPLATE = "^undo( (?<number>\\d+))?";
    private static final String UNDO_TIMES = "number";
    private static final String COMMAND_RESULT_TEMPLATE = "List undo-ed %d times";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String undoTimesToken = tokens.get(UNDO_TIMES);
        int undoTimes = undoTimesToken != null ? Integer.parseInt(undoTimesToken) : 1;

        Optional<TodoList> todoListOptional = storage.undo(undoTimes);
        if (todoListOptional.isPresent()) {
            uiStore.setTask(todoListOptional.get().getTasks());
            renderer.render();
        }

        return new CommandResult(String.format(COMMAND_RESULT_TEMPLATE, undoTimes));
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        final Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        final Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        final HashMap<String, String> tokens = new HashMap<>();
        tokens.put(UNDO_TIMES, matcher.group(UNDO_TIMES));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }
}
