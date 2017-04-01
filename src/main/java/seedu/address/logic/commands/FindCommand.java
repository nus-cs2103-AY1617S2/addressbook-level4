package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;

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
    private final Deadline deadline;

    public FindCommand(String keywords, String deadline) throws IllegalValueException {
        final Set<String> keywordSet = new HashSet<>();
        StringTokenizer st = new StringTokenizer(keywords, " ");
        while (st.hasMoreTokens()) {
            keywordSet.add(st.nextToken());
        }
        this.keywords = keywordSet;
        this.deadline = createDeadline(deadline);
    }

    @Override
    public CommandResult execute() {
        // model.updateFilteredTaskListByKeywords(keywords);
        model.updateFilteredTaskListByDate(deadline);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    public Deadline createDeadline(String deadline) throws IllegalValueException {
        return (deadline == null ? new Deadline() : new Deadline(deadline));
    }

}
