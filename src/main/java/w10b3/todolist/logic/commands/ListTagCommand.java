package w10b3.todolist.logic.commands;

//@@author A0122017Y
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
