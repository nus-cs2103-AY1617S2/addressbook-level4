//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.dispatcher.CommandHistoryList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * HistoryController is responsible for showing past commands entered
 */
public class HistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(HistoryController.class);
    private static final String MESSAGE_RESULT = "%s\n%s displayed.";
    private static final String COMMAND_WORD = "history";
    private static final String COMMAND_REGEX = "(?iu)^\\s*history\\s*";

    private static final String HELP_DETAILS = "Shows previous commands entered.";
    private static final String HELP_FORMAT = "history";
    private static final String[] HELP_COMMENTS = { "Commands are listed in order "
                                                        + "from latest command to earlier command.",
                                                    "You can also use the up and down arrow keys "
                                                        + "to cycle through the commands in the command box." };

    private CommandHistoryList commandHistoryList;

    public void setCommandHistory(CommandHistoryList commandHistoryList) {
        this.commandHistoryList = commandHistoryList;
    }

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().getName() + " will handle command");
        UiStore uiStore = UiStore.getInstance();
        ArrayList<String> commandHistory = commandHistoryList.getCommandHistory();
        String result = String.join("\n", commandHistory);
        uiStore.setCommandResult(new CommandResult(String.format(
                MESSAGE_RESULT, result, StringUtil.nounWithCount("command", commandHistory.size()))));
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_REGEX);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, null };
    }
}
