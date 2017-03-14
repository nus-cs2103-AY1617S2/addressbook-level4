package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * Responsible for storage-related task
 */
public class StoreController extends Controller {
    private static final String COMMAND_TEMPLATE = "^save\\s+(?<directory>\\S+)\\s*";
    private static final String COMMAND_WORD = "saveConfig";
    private static final String STORE_DIRECTORY = "directory";
    private static final String RESULT_MESSAGE_TEMPLATE = "Data storage path was changed to %s";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public StoreController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass() + "will handle command");
        HashMap<String, String> tokens = tokenize(command);
        String directory = tokens.get(STORE_DIRECTORY);
        TodoList.load().getStorage().move(directory);
        return new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE, Config.getInstance().getTodoListFilePath()));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(STORE_DIRECTORY, matcher.group(STORE_DIRECTORY));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
