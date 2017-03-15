package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class BookCommand extends Command {

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
