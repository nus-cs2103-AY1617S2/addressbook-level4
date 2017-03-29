package seedu.tache.logic.commands;

import static seedu.tache.logic.parser.CliSyntax.ALL_FILTER;
import static seedu.tache.logic.parser.CliSyntax.COMPLETED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.DUE_THIS_WEEK_FILTER;
import static seedu.tache.logic.parser.CliSyntax.DUE_TODAY_FILTER;
import static seedu.tache.logic.parser.CliSyntax.FLOATING_FILTER;
import static seedu.tache.logic.parser.CliSyntax.TIMED_FILTER;
import static seedu.tache.logic.parser.CliSyntax.UNCOMPLETED_FILTER;

import seedu.tache.commons.util.StringUtil;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

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
        case COMPLETED_FILTER:
            model.updateFilteredListToShowCompleted();
            break;
        case TIMED_FILTER:
            model.updateFilteredListToShowTimed();
            break;
        case FLOATING_FILTER:
            model.updateFilteredListToShowFloating();
            break;
        case DUE_TODAY_FILTER:
            model.updateFilteredListToShowDueToday();
            break;
        case DUE_THIS_WEEK_FILTER:
            model.updateFilteredListToShowDueThisWeek();
            break;
        case ALL_FILTER:
            model.updateFilteredListToShowAll();
            break;
        case UNCOMPLETED_FILTER:
        default:
            model.updateFilteredListToShowUncompleted();
            return new CommandResult(String.format(MESSAGE_SUCCESS, StringUtil.capitalizeFirstCharacter(filter)));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, StringUtil.capitalizeFirstCharacter(filter)));
    }
}
