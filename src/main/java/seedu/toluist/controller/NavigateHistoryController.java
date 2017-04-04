//@@author A0162011A
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.dispatcher.CommandHistoryList;

/**
 * NavigateHistoryController is responsible for handling cycling through previous commands
 */
public class NavigateHistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(NavigateHistoryController.class);
    private static final String COMMAND_WORD = "navigatehistory";
    private static final String PARAMETER_UP = "up";
    private static final String PARAMETER_DOWN = "down";
    private static final String PARAMETER_DIRECTION = "direction";
    private CommandHistoryList commandHistory;

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().getName() + " will handle command");

        String direction = tokens.get(PARAMETER_DIRECTION);

        if (direction.equals(PARAMETER_UP)) {
            showPreviousCommand(uiStore.getCommandInputProperty().getValue());
        } else if (direction.equals(PARAMETER_DOWN)) {
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

    public Map<String, String> tokenize(String command) {
        HashMap<String, String> tokens = new HashMap<>();

        command = command.replace(COMMAND_WORD, "").trim();
        tokens.put(PARAMETER_DIRECTION, command);

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
