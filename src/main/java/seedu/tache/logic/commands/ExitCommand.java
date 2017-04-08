package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    //@@author A0150120H
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits Tache.\n"
            + "All changes will be saved but undo history will be discarded.\n"
            + "Example: " + COMMAND_WORD;
    //@@author
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tache as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
