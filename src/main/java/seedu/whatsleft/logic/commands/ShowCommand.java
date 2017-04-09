package seedu.whatsleft.logic.commands;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.events.model.ShowStatusChangedEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.logic.commands.exceptions.CommandException;

//@@author A0121668A

/**
 * Updates the display preference of tasks in WhatsLeft.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the current display status preference "
            + "(reflected on the bottom right status bar) "
            + "    Parameters: STATUS PREFERENCE ([com/pend])" + "\n"
            + "Example: " + COMMAND_WORD + " (show all tasks and events)" + "\n"
            + "Example: " + COMMAND_WORD + " com (show completed tasks and past events)" + "\n"
            + "Example: " + COMMAND_WORD + " pend (show pending tasks and future events)";

    public static final String MESSAGE_DISPLAY_PREFERENCE_SUCCESS = "Successfully changed display"
            + " preference to show [%1$s] tasks";

    private final String displayPreference;

    public ShowCommand(String displayPreference) {
        assert displayPreference.equals("") | displayPreference.equals("com") | displayPreference.equals("pend");

        this.displayPreference = displayPreference;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String preference;
        if (displayPreference.equals("com")) {
            preference = "COMPLETED";
        } else if (displayPreference.equals("pend")) {
            preference = "PENDING";
        } else if (displayPreference.equals("")) {
            preference = "ALL";
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAY_STATUS_KEYWORD);
        }
        model.setDisplayStatus(preference);
        model.updateFilteredListToShowAll();
        //@@author A0148038A
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(0));
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(0));
        EventsCenter.getInstance().post(new ShowStatusChangedEvent(model.getDisplayStatus()));
        return new CommandResult(String.format(MESSAGE_DISPLAY_PREFERENCE_SUCCESS, preference));
    }

    // @@author
}
