package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Finds and lists all tasks in address book whose name or description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;
    private final String deadline;

    public FindCommand(String keywords, String deadline) {
        final Set<String> keywordSet = new HashSet<>();
        StringTokenizer st = new StringTokenizer(keywords, " ");
        while (st.hasMoreTokens()) {
            keywordSet.add(st.nextToken());
        }
        this.keywords = keywordSet;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListByKeywords(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
