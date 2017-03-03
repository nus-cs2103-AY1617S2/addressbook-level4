package seedu.doist.logic.commands;


import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

	public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("help"));
    public static final String DEFAULT_COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = CommandUtil.getUsageTextForCommandWords(commandWords, DEFAULT_COMMAND_WORD) + ": Shows program usage instructions.\n"
            + "Example: " + DEFAULT_COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
