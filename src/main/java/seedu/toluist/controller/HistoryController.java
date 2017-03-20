package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;

/**
 * ListController is responsible for rendering the initial UI
 */
public class HistoryController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(ClearController.class);
    private static final String RESULT_MESSAGE = "%s\n%d commands displayed.";
    private static final String COMMAND_WORD = "history";
    private static final String COMMAND_REGEX = "^history\\s*";

    public HistoryController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        String result = String.join("\n", commandHistory);

        return new CommandResult(String.format(RESULT_MESSAGE, result, commandHistory.size()));
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
