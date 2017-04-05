package seedu.address.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.ocpsoft.prettytime.nlp.parse.DateGroup;

//@@author A0144422R
/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: "
            + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS_STATUS_BAR = "%1$s task(s) found.";

    private final Set<String> keywords;
    private final Date date;
    private final Set<String> tagKeys;

    public FindCommand(Set<String> keywords, List<DateGroup> dates,
            Set<String> tagKeys) {
        this.keywords = keywords;
        if (dates != null && dates.size() > 0
                && dates.get(0).getDates().size() > 0) {
            this.date = dates.get(0).getDates().get(0);
        } else {
            this.date = null;
        }
        this.tagKeys = tagKeys;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, date, tagKeys);
        return new CommandResult(
                getMessageForTaskListShownSummary(
                        model.getFilteredTaskList().size()),
                String.format(MESSAGE_SUCCESS_STATUS_BAR,
                        model.getFilteredTaskList().size()));
    }

}
