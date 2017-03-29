//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.ui.commons.CommandResult;

/**
 * HistoryController is responsible for showing past commands entered
 */
public class HistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(HistoryController.class);
    private static final String RESULT_MESSAGE = "%s\n%s displayed.";
    private static final String COMMAND_WORD = "history";
    private static final String COMMAND_REGEX = "(?iu)^\\s*history\\s*";

    private static final String HELP_BASIC = "history";
    private static final String HELP_ADVANCED = "history";

    private ArrayList<String> commandHistory;

    public void setCommandHistory(ArrayList<String> commandHistory) {
        this.commandHistory = commandHistory;
    }

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        String result = String.join("\n", commandHistory);

        uiStore.setCommandResult(new CommandResult(String.format(
                RESULT_MESSAGE, result, StringUtil.nounWithCount("command", commandHistory.size()))));
    }

    public HashMap<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_REGEX);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
