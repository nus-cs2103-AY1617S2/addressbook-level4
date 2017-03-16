package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model.StateLimitReachedException;


/**
 * Redo a task in Burdens.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redone";
    public static final String MESSAGE_NO_FORWARDS_COMMAND = "There's no command to redo.";

    //private final Task toAdd;

    /**
     * Creates a Redo Command.
     */
    public RedoCommand() {
        /*
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        toAdd = null;
        */
        //Where is the hashset stored?
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.setAddressBookStateForwards();
        } catch (StateLimitReachedException e) {
            throw new CommandException(MESSAGE_NO_FORWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
