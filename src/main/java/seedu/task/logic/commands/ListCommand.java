package seedu.task.logic.commands;


/**
 * Lists all task in KIT to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_SHORTFORM = "ls";
    public static final String COMMAND_WORD_HOTKEY = "l";
    

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
