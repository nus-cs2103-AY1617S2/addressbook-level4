//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Responsible for loading-related task
 */
public class LoadController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(LoadController.class);
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*load(\\s+(?<directory>\\S+))?\\s*";
    public static final String COMMAND_WORD = "load";
    public static final String PARAMETER_STORE_DIRECTORY = "directory";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Changes the location for the storage file used in this system.";
    private static final String HELP_FORMAT = "load NEWFILELOCATION";
    private static final String[] HELP_COMMENTS = { "Related commands: `save`",
                                                    "The displayed data will be updated to the data from the new file.",
                                                    "The old file will remain in your computer.",
                                                    "The file location entered is relative "
                                                        + "to the location of the program.",
                                                    "Warning: If a file with the requested name already exists, "
                                                        + "it will be overwritten." };
    private static final String[] HELP_EXAMPLES = { "`load newfile.json`\nLoads ToLuist data from `newfile.json`.",
                                                    "`load newfolder/newfile.json`\nLoads ToLuist data "
                                                        + "from `newfile.json` in the folder `newfolder`.",
                                                    "`load ../newfile.json`\nLoads ToLuist data "
                                                        + "from `newfile.json` in the parent folder" };

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
        String oldStoragePath = config.getTodoListFilePath();
        if (oldStoragePath.equals(path)) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(Messages.MESSAGE_STORAGE_SAME_LOCATION, path)));
            return;
        }

        try {
            TodoList todoList = TodoList.getInstance();
            todoList.load(path);
            UiStore.getInstance().setTasks(todoList.getTasks());
            uiStore.setCommandResult(
                    new CommandResult(String.format(Messages.MESSAGE_SET_STORAGE_SUCCESS, path)));
        } catch (DataStorageException e) {
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
