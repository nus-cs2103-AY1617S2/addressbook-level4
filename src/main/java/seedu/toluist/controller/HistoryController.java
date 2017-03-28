//@@author A0162011A
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.CommandHistoryList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * ListController is responsible for rendering the initial UI
 */
public class HistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(ClearController.class);
    private static final String RESULT_MESSAGE = "%s\n%s displayed.";
    private static final String COMMAND_WORD = "history";
    private static final String COMMAND_REGEX = "(?iu)^\\s*history\\s*";

    private CommandHistoryList commandHistory;

    public void setCommandHistory(CommandHistoryList commandHistory) {
        this.commandHistory = commandHistory;
    }

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        String result = String.join("\n", commandHistory.getAsArrayList());

        uiStore.setCommandResult(new CommandResult(String.format(
                RESULT_MESSAGE, result, StringUtil.nounWithCount("command", commandHistory.getAsArrayList().size()))));
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
