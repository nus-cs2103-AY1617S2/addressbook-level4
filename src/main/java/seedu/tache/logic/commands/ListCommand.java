package seedu.tache.logic.commands;

import static seedu.tache.logic.parser.CliSyntax.ALL_FILTER;
import static seedu.tache.logic.parser.CliSyntax.COMPLETED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.FLOATING_FILTER;
import static seedu.tache.logic.parser.CliSyntax.TIMED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.UNCOMPLETED_FILTER;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists out all tasks based on type (default: all)\n"
            + "Parameters (Optional): all, timed, floating, uncompleted or completed\n"
            + "Example: " + COMMAND_WORD + " completed";


    private String filter;
    //@@author A0139925U
    public ListCommand() {
        this.filter = "all";
    }

    public ListCommand(String filter) {
        assert filter != null;
        this.filter = filter;
    }

    @Override
    public CommandResult execute() {
        switch(filter) {
        case COMPLETED_FILTER:
            model.updateFilteredListToShowCompleted();
            break;
        case UNCOMPLETED_FILTER:
            model.updateFilteredListToShowUncompleted();
            break;
        case TIMED_FILTER:
            model.updateFilteredListToShowTimed();
            break;
        case FLOATING_FILTER:
            model.updateFilteredListToShowFloating();
            break;
        case ALL_FILTER:
            model.updateFilteredListToShowAll();
            break;
        default:
            model.updateFilteredListToShowAll();
            break;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
