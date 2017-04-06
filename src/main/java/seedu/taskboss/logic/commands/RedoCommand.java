package seedu.taskboss.logic.commands;

import java.util.EmptyStackException;

import seedu.taskboss.commons.exceptions.IllegalValueException;

//@@author A0138961W
/**
 * Redoes the most recent undo operation done by the user.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_WORD_SHORT = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Redoes the previous undo operation by the user\n"
        + "Example: " + COMMAND_WORD + " || " + COMMAND_WORD_SHORT;

    public static final String MESSAGE_SUCCESS = "The most recent operation has been redone!";

    public static final String MESSAGE_WITHOUT_PREVIOUS_OPERATION = "Cannot redo";

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.redoTaskboss();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }
}
