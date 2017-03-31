//@@author A0141011J

package seedu.taskit.logic.commands;

import seedu.taskit.commons.exceptions.NoValidStateException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Action redone.";
    public static final String MESSAGE_NO_NEXT_STATE = "No undone commands to redo.";

    public RedoCommand() {

    }

    @Override
    public CommandResult execute() {
        try {
            model.redo();
        } catch (NoValidStateException nvse) {
            return new CommandResult(MESSAGE_NO_NEXT_STATE);
        }
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }

}
