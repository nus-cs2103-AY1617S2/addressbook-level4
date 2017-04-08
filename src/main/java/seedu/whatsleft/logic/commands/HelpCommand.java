package seedu.whatsleft.logic.commands;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.events.ui.ShowHelpRequestEvent;

//@@author A0148038A
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public final String commandHelpMessage;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand(String commandWord) {
        this.commandHelpMessage = commandWord;
    }
    @Override
    public CommandResult execute() {
        if (commandHelpMessage.equals("")) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            model.storePreviousCommand("");
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            return new CommandResult(commandHelpMessage);
        }
    }
}
