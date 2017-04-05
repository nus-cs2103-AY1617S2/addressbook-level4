package seedu.taskboss.logic.commands;

import java.util.EmptyStackException;

import seedu.taskboss.commons.exceptions.IllegalValueException;

//@@author A0138961W
/**
 * Undoes the most recent operation entered by user.
 */

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_WORD_SHORT = "u";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Undoes the most recent operation entered by user\n"
            + "Example: " + COMMAND_WORD + " || " + COMMAND_WORD_SHORT;

    public static final String MESSAGE_SUCCESS = "The most recent operation has been undone!";

    public static final String MESSAGE_WITHOUT_PREVIOUS_OPERATION = "Cannot undo.";

    public UndoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.undoTaskboss();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }
}
