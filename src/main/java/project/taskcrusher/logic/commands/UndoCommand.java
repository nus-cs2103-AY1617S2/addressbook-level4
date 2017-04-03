package project.taskcrusher.logic.commands;

/**
 * Clears the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo Performed";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
