package seedu.toluist.controller;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    private static final String COMMAND_TEMPLATE = "^exit$";

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
}
