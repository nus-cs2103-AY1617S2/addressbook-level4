package seedu.onetwodo.logic.commands;

import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.events.ui.ShowHelpRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowHelpUGRequestEvent;

//@@author A0141138N
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String SHORT_COMMAND_WORD = "h";

    public static final String SHORT_USERGUIDE = "ug";
    public static final String LONG_USERGUIDE = "userguide";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String SHOWING_HELP_MESSAGE_USERGUIDE = "Opened user guide.";

    private String commandArgument;

    public HelpCommand(String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute() {
        String messageToReturn;

        switch (commandArgument) {
        case SHORT_USERGUIDE:
            EventsCenter.getInstance().post(new ShowHelpUGRequestEvent());
            messageToReturn = SHOWING_HELP_MESSAGE_USERGUIDE;
            break;

        case LONG_USERGUIDE:
            EventsCenter.getInstance().post(new ShowHelpUGRequestEvent());
            messageToReturn = SHOWING_HELP_MESSAGE_USERGUIDE;
            break;
        default:
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            messageToReturn = SHOWING_HELP_MESSAGE;
        }
        return new CommandResult(messageToReturn);
    }
}
