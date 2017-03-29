//@@author A0163744B
package seedu.task.logic.commands;


/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static enum ListCommandOption {
        ALL,
        COMPLETE,
        INCOMPLETE,
        ID,
        DUE,
        START,
        END
    };

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the tasks"
            + "Parameters: [complete | incomplete | by added | by due | by starts | by ends]\n"
            + "Example: " + COMMAND_WORD + " complete";

    public static final String MESSAGE_SUCCESS = "Listed tasks";

    private ListCommandOption option;

    public ListCommand(ListCommandOption commandOption) {
        this.option = commandOption;
    }

    @Override
    public CommandResult execute() {
        switch(this.option) {
        case ALL:
            model.updateFilteredListToShowAll();
            break;
        case COMPLETE:
            model.updateFilteredListToShowCompletion(true);
            break;
        case INCOMPLETE:
            model.updateFilteredListToShowCompletion(false);
            break;
        case ID:
            model.updateFilteredListToSortById();
            break;
        case DUE:
            model.updateFilteredListToSortByDue();
            break;
        case START:
            model.updateFilteredListToSortByStart();
            break;
        case END:
            model.updateFilteredListToSortByEnd();
            break;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
