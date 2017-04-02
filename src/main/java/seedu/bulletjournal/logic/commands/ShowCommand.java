//@@author A0105748B
package seedu.bulletjournal.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks that are done or undone.
 * Keyword matching is case insensitive.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks that are "
            + "done or undone (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " done";

    private final Set<String> keywords;
    
    public ShowCommand(Set<String> keywords) {
        this.keywords = keywords;
        if (this.keywords.contains("undone")) {
            keywords.add("empty");
        }
    }

    @Override
    public CommandResult execute() {
        model.updateMatchedTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
