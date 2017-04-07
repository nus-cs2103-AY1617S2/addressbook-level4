package seedu.tache.logic.commands;

import static seedu.tache.logic.parser.CliSyntax.FILTER_ALL;
import static seedu.tache.logic.parser.CliSyntax.FILTER_COMPLETED;
import static seedu.tache.logic.parser.CliSyntax.FILTER_DUE_THIS_WEEK;
import static seedu.tache.logic.parser.CliSyntax.FILTER_DUE_TODAY;
import static seedu.tache.logic.parser.CliSyntax.FILTER_FLOATING;
import static seedu.tache.logic.parser.CliSyntax.FILTER_OVERDUE;
import static seedu.tache.logic.parser.CliSyntax.FILTER_TIMED;
import static seedu.tache.logic.parser.CliSyntax.FILTER_UNCOMPLETED;

import seedu.tache.commons.util.StringUtil;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String SHORT_COMMAND_WORD = "l";

    public static final String MESSAGE_SUCCESS = "%1$s tasks listed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists out all tasks based on type (default: all)\n"
            + "Parameters (Optional): all, timed, floating, today, this week, uncompleted or completed\n"
            + "Example: " + COMMAND_WORD + " completed";


    private String filter;
    //@@author A0139925U
    public ListCommand() {
        this.filter = "uncompleted";
    }

    public ListCommand(String filter) {
        assert filter != null;
        this.filter = filter;
    }

    @Override
    public CommandResult execute() {
        switch(filter) {
        case FILTER_COMPLETED:
            model.updateFilteredListToShowCompleted();
            break;
        case FILTER_TIMED:
            model.updateFilteredListToShowTimed();
            break;
        case FILTER_FLOATING:
            model.updateFilteredListToShowFloating();
            break;
        case FILTER_DUE_TODAY:
            model.updateFilteredListToShowDueToday();
            break;
        case FILTER_DUE_THIS_WEEK:
            model.updateFilteredListToShowDueThisWeek();
            break;
        case FILTER_OVERDUE:
            model.updateFilteredListToShowOverdueTasks();
            break;
        case FILTER_ALL:
            model.updateFilteredListToShowAll();
            break;
        case FILTER_UNCOMPLETED:
        default:
            model.updateFilteredListToShowUncompleted();
            return new CommandResult(String.format(MESSAGE_SUCCESS, StringUtil.capitalizeFirstCharacter(filter)));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, StringUtil.capitalizeFirstCharacter(filter)));
    }
}
