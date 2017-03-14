package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String COMMAND_WORD_QUIT = "quit";
    private static final String COMMAND_TEMPLATE = "^(exit|quit)$";

    public ExitController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        renderer.stop();
        // This result won't be displayed
        return new CommandResult("");
    }

    public HashMap<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD_EXIT, COMMAND_WORD_QUIT };
    }
}
