package seedu.toluist.controller;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    private static final String COMMAND_TEMPLATE = "^(exit|quit)$";
    private static final String COMMAND_WORD_EXIT = "exit";
    private static final String COMMAND_WORD_QUIT = "quit";

    public ExitController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        renderer.stop();
        // This result won't be displayed
        return new CommandResult("");
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD_EXIT, COMMAND_WORD_QUIT };
    }
}
