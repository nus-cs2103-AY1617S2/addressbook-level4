package typetask.logic.commands;

import java.util.Set;
//@@author A0139926R
/**
 * Finds and lists all tasks in TaskManager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD2 = "search";
    public static final String COMMAND_WORD3 = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;
    private String keywordsContainDate = "";

    public FindCommand(Set<String> keywords, String keywordsContainDate) {
        this.keywords = keywords;
        this.keywordsContainDate = keywordsContainDate;
    }

    @Override
    public CommandResult execute() {
        if (keywordsContainDate.equals("")) {
            model.updateFilteredTaskList(keywords);
        } else {
            model.updateFilteredTaskList(keywordsContainDate);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
