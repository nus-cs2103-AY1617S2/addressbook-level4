package seedu.toluist.controller;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.TodoList;

/**
 * Responsible for saving-related task
 */
public class StoreController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(StoreController.class);
    private static final String COMMAND_TEMPLATE = "^save(\\s+(?<directory>\\S+))?\\s*";
    public static final String COMMAND_WORD = "save";
    public static final String STORE_DIRECTORY = "directory";

    public static final String RESULT_MESSAGE_WARNING_OVERWRITE = "A file exists at %s. This file will be overwritten.";

    public void execute(String command) {
        logger.info(getClass() + "will handle command");
        HashMap<String, String> tokens = tokenize(command);
        String path = tokens.get(STORE_DIRECTORY);

        if (path == null) {
            uiStore.setCommandResult(new CommandResult(Messages.MESSAGE_NO_STORAGE_PATH));
        }

        Config config = Config.getInstance();
        if (config.getTodoListFilePath().equals(path)) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(Messages.MESSAGE_STORAGE_SAME_LOCATION, path)));
        }

        String message = "";
        if (FileUtil.isFileExists(new File(path))) {
            message += String.format(RESULT_MESSAGE_WARNING_OVERWRITE, path) + "\n";
        }

        if (TodoList.load().getStorage().move(path)) {
            message += String.format(Messages.MESSAGE_SET_STORAGE_SUCCESS, config.getTodoListFilePath());
            uiStore.setCommandResult(new CommandResult(message));
        } else {
            uiStore.setCommandResult(
                    new CommandResult(String.format(Messages.MESSAGE_SET_STORAGE_FAILURE, path)));
        }
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
