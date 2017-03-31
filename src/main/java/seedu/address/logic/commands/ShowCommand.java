package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.ShowStatusChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;


//@@author A0121668A

/**
 * Updates the display preference of tasks in WhatsLeft.
 */

public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the current display status preference "
            + "to show completed, pending or all tasks "
            + "Parameters: STATUS PREFERENCE (com shows complete tasks, pend shows pending tasks, all shows all tasks"
            + "[com/pend/all]"
            + "Example: " + COMMAND_WORD + " com";

    public static final String MESSAGE_DISPLAY_PREFERENCE_SUCCESS = "Successfully changed display"
            + " preference to show %1$s tasks";

    private final String displayPreference;

    public ShowCommand(String displayPreference) {
        assert displayPreference.equals("") | displayPreference.equals("com") | displayPreference.equals("pend");

        this.displayPreference = displayPreference;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String preference;
        if (displayPreference.equals("com")) {
            preference = "Completed";
        } else if (displayPreference.equals("pend")) {
            preference = "Pending";
        } else if (displayPreference.equals("")) {
            preference = "All";
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAY_STATUS_KEYWORD);
        }
        model.setDisplayStatus(preference);
        model.updateFilteredListToShowAll();
        indicateShowStatusChanged();
        return new CommandResult(String.format(MESSAGE_DISPLAY_PREFERENCE_SUCCESS, preference));
    }

    private void indicateShowStatusChanged() {
        EventsCenter.getInstance().post(new ShowStatusChangedEvent(model.getDisplayStatus()));
    }

    //@@author
}
