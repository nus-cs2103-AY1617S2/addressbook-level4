package seedu.jobs.logic.commands;

import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.events.ui.BrowserDisplayEvent;

public class DisplayCommand extends Command {
    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": display and activate browser and google calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_DISPLAY_MESSAGE = "displaying browser";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new BrowserDisplayEvent());
        return new CommandResult(SHOWING_DISPLAY_MESSAGE);
    }
}
