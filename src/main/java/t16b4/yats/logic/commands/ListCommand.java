package t16b4.yats.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_EXTENTION = "by";
    public static final String COMMAND_WORD_SUFFIX_TITLE = "title";
    public static final String COMMAND_WORD_SUFFIX_DEADLINE = "deadline";
    public static final String COMMAND_WORD_SUFFIX_TIMING = "timing";
    public static final String COMMAND_WORD_SUFFIX_TAG = "tag";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
