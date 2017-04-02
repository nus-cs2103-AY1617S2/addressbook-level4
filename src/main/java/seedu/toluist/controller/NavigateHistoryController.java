//@@author A0162011A
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandHistoryList;

/**
 * NavigateHistoryController is responsible for handling cycling through previous commands
 */
public class NavigateHistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(NavigateHistoryController.class);
    private static final String COMMAND_WORD = "navigatehistory";
    private static final String UP_PARAMETER = "up";
    private static final String DOWN_PARAMETER = "down";
    private static final String DIRECTION_PARAMETER = "direction";
    private CommandHistoryList commandHistory;

    public void execute(HashMap<String, String> tokens) {
        logger.info(getClass().getName() + " will handle command");

        String direction = tokens.get(DIRECTION_PARAMETER);

        if (direction.equals(UP_PARAMETER)) {
            showPreviousCommand(uiStore.getCommandInputProperty().getValue());
        } else if (direction.equals(DOWN_PARAMETER)) {
            showNextCommand();
        }
    }

    private void showNextCommand() {
        String command = commandHistory.movePointerDown();
        uiStore.setCommandInput(command);
    }

    private void showPreviousCommand(String currentCommand) {
        String command = commandHistory.movePointerUp(currentCommand);
        uiStore.setCommandInput(command);
    }

    public HashMap<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        command = command.replace(COMMAND_WORD, "").trim();
        tokens.put(DIRECTION_PARAMETER, command);

        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.startsWith(COMMAND_WORD);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public void setCommandHistory(CommandHistoryList commandHistory) {
        this.commandHistory = commandHistory;
    }
}
