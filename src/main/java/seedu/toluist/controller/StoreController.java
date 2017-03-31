//@@author A0131125Y
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
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Responsible for saving-related task
 */
public class StoreController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(StoreController.class);
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*save(\\s+(?<directory>\\S+))?\\s*";
    public static final String COMMAND_WORD = "save";
    public static final String PARAMETER_STORE_DIRECTORY = "directory";

    public static final String RESULT_MESSAGE_WARNING_OVERWRITE = "A file exists at %s. This file will be overwritten.";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Changes the location for the storage file used in this system.";
    private static final String HELP_FORMAT = "save NEWFILELOCATION";
    private static final String[] HELP_COMMENTS = { "Related commands: `load`",
                                                    "All data will be moved to the new file location.",
                                                    "If the file does not exist, the file will be created.",
                                                    "The old file will be removed.",
                                                    "The file location entered is relative "
                                                        + "to the location of the program.",
                                                    "Warning: If a file with the requested name already exists, "
                                                        + "it will be overwritten." };
    private static final String[] HELP_EXAMPLES = { "`save newfile.json`\nSaves ToLuist data to `newfile.json`.",
                                                    "`save newfolder/newfile.json`\nSaves ToLuist data "
                                                        + "to `newfile.json` in the folder `newfolder`.",
                                                    "`save ../newfile.json`\nSaves ToLuist data "
                                                        + "to `newfile.json` in the parent folder" };


    //@@author A0131125Y
    public void execute(String command) {
        logger.info(getClass() + "will handle command");
        HashMap<String, String> tokens = tokenize(command);
        String path = tokens.get(PARAMETER_STORE_DIRECTORY);

        if (path == null) {
            uiStore.setCommandResult(new CommandResult(Messages.MESSAGE_NO_STORAGE_PATH));
            return;
        }

        Config config = Config.getInstance();
        if (config.getTodoListFilePath().equals(path)) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(Messages.MESSAGE_STORAGE_SAME_LOCATION, path)));
            return;
        }

        String message = "";
        if (FileUtil.isFileExists(new File(path))) {
            message += String.format(RESULT_MESSAGE_WARNING_OVERWRITE, path) + "\n";
        }

        if (TodoList.getInstance().getStorage().move(path)) {
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
        tokens.put(PARAMETER_STORE_DIRECTORY, matcher.group(PARAMETER_STORE_DIRECTORY));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    //@@author A0162011A
    public static String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public static String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
