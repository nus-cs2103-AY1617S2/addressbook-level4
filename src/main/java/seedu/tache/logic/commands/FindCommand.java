package seedu.tache.logic.commands;

import java.util.ArrayList;
import java.util.Set;

import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.util.StringUtil;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_SUCCESS = "Listed tasks whose names contain %1$s.\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting with";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    //@@author A0142255M
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        ArrayList<String> keywordsList = new ArrayList<String>(keywords);
        String keywordString = "\"" + keywordsList.get(0) + "\"";
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(MESSAGE_SUCCESS, keywordString));
        builder.append(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        return new CommandResult(builder.toString());
    }

}
