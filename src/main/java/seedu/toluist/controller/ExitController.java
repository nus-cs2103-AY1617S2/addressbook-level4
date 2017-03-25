//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String COMMAND_WORD_QUIT = "quit";
    private static final String COMMAND_TEMPLATE = "^(exit|quit)\\s*";

    public void execute(String command) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
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
