package seedu.doist.logic.commands;


/**
 * Undo commands that are done previously.
 */
public class UndoCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "undo %1$d steps successful";

    private int numSteps = 1;

    @Override
    public CommandResult execute() {
        assert model != null;
        for (int i = 0; i < numSteps; i++) {
            model.recoverPreviousTodoList();
        }
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, numSteps));
    }

    public UndoCommand(int numSteps) {
        this.numSteps = numSteps;
    }

    public static CommandInfo info() {
        return new CommandInfo(Command.getAliasList(DEFAULT_COMMAND_WORD), DEFAULT_COMMAND_WORD);
    }
}
