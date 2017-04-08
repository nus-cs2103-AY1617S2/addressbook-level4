package project.taskcrusher.logic.commands;

import project.taskcrusher.model.UserInbox;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Active list has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.saveUserInboxStateForUndo();
        model.resetData(new UserInbox());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
