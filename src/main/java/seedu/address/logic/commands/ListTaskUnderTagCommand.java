package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskUnderTagCommand extends Command {

    public static final String COMMAND_WORD = "list#";
    public static final String MESSAGE_SUCCESS = "Tasks under tag \\p{Alnum}+ is listed!";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
