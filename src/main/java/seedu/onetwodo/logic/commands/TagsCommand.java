//@@author A0135739W
package seedu.onetwodo.logic.commands;

import seedu.onetwodo.logic.commands.exceptions.CommandException;

/**
* shows all the tags.
*/
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": shows all the tags.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Showed all the tags.";

    @Override
    public CommandResult execute() throws CommandException {
        model.displayTags();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
