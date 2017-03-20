package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtags";

    @Override
    public CommandResult execute() {
        return new CommandResult(model.getTagListToString());
    }
}
