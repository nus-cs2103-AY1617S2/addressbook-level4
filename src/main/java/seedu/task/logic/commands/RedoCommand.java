package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163845X
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "redo successful";
    public static final String MESSAGE_FAIL = "redo failure";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.redo();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAIL);
        }
    }

}
