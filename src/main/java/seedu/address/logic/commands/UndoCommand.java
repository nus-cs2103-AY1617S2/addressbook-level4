package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes last action if it involves changing a todo. ";

    public static final String MESSAGE_SUCCESS = "Action undone: %1$s";
    
    public static final String MESSAGE_ERROR = "Error: last action could not be undone";

    private final Command actionToUndo;

    /**
     * Creates an UndoCommand using raw values.
     * * Only adds floating task for now
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand(Command actionToUndo) {
        this.actionToUndo = actionToUndo;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.undoLastAction();
            return new CommandResult(String.format(MESSAGE_SUCCESS, actionToUndo.getClass().COMMAND_WORD));
        } catch (Exception e) {
            throw new CommandException();
        }

    }
	
	@Override
    public Boolean isModifying() {
    	return false;
    }

}
