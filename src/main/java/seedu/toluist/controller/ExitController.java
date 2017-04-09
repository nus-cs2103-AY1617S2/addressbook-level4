//@@author A0131125Y
package seedu.toluist.controller;

import java.util.Map;

import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String COMMAND_WORD_QUIT = "quit";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*(exit|quit)\\s*";

    private static final String HELP_DETAILS = "Exits the program.";
    private static final String HELP_FORMAT = "exit/quit";
    private static final String[] HELP_COMMENTS = { "Your data will save automatically.",
                                                    "You can re-open the program by clicking on the .jar file." };

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD_EXIT, COMMAND_WORD_QUIT };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, null };
    }
}
