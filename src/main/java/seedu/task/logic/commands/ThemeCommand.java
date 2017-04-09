package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowThemeWindowRequestEvent;

//@@author A0163848R-reused
/**
 * Format full help instructions for every command for display.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program theme manager.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_THEME_MESSAGE = "Opened theme manager window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowThemeWindowRequestEvent());
        return new CommandResult(SHOWING_THEME_MESSAGE);
    }
}
