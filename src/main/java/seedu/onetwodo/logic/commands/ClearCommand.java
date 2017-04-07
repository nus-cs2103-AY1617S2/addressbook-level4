package seedu.onetwodo.logic.commands;

//@@author A0135739W
/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEAR_ALL_SUCCESS = "All tasks have been removed!";
    public static final String MESSAGE_CLEAR_DONE_SUCCESS = "All completed tasks have been removed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes all tasks or all done tasks.\n"
            + "Parameters: all, done or empty\n"
            + "Example: " + COMMAND_WORD + " done";

    private String commandArgument;

    public ClearCommand (String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        String messageToReturn;

        switch (commandArgument) {
        case "done":
            model.clearDone();
            messageToReturn = MESSAGE_CLEAR_DONE_SUCCESS;
            break;

        case "all":
        default:
            model.clear();
            messageToReturn = MESSAGE_CLEAR_ALL_SUCCESS;
        }
        return new CommandResult(messageToReturn);
    }
}
