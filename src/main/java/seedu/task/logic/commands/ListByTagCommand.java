package seedu.task.logic.commands;


public class ListByTagCommand extends Command {

    public static final String COMMAND_WORD = "listtag";
    public static final String COMMAND_WORD_SINGLE_T = "listag";
    public static final String COMMAND_WORD_LONGER_HOTKEY = "ltag";
    public static final String COMMAND_WORD_HOTKEY = "lt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose tags contain "
            + "the specified tag (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: Tag \n\t" + "Example: " + COMMAND_WORD + " friend";

    private final String tag;

    public ListByTagCommand(String tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(tag);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
