package seedu.task.logic.commands;

//@@author A0142487Y
public class ListByTagCommand extends Command {

    public static final String COMMAND_WORD_1 = "listtag";
    public static final String COMMAND_WORD_2 = "listag";
    public static final String COMMAND_WORD_3 = "ltag";
    public static final String COMMAND_WORD_4 = "lt";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ":\n" + "Finds all persons whose tags contain "
            + "the specified tag (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: Tag \n\t" + "Example: " + COMMAND_WORD_1 + " friend";

    private final String tag;

    public ListByTagCommand(String tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(tag);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
