package seedu.task.logic.commands;

//@@author A0139975J-unused
// merged with list command
public class ListByDoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "listdone";
    public static final String COMMAND_WORD_2 = "ld";

    public static final String MESSAGE_LISTBYDONE_SUCCESS = "Listed all done tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all done tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;


    private final boolean value;

    public ListByDoneCommand(boolean value) {
        this.value = value;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(this.value);
        return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
