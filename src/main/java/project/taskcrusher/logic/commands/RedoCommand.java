package project.taskcrusher.logic.commands;

/**
 * Clears the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo Performed";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.redo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}