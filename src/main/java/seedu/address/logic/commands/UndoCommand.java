package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model.StateLimitReachedException;

/**
 * Adds a task to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a task. ";

    public static final String MESSAGE_SUCCESS = "Undone";
    public static final String MESSAGE_NO_BACKWARDS_COMMAND = "There's no previous command to undo.";
    /*
    private final Task toAdd;
	*/
    /**
     * Creates an Undo command.
     */
    public UndoCommand() {
    	/*
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        toAdd = null;
        */
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.setAddressBookStateBackwards();
        } catch (StateLimitReachedException e) {
            throw new CommandException(MESSAGE_NO_BACKWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}
