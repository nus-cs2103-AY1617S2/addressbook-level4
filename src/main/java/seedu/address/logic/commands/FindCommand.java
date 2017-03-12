
package seedu.address.logic.commands;

import java.util.Set

;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose title contain any of "
            + "the specified keywords (minimally 3-letter substrings) and displays them as a list with index numbers.\n"
            + "Parameters: find [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " groceries or groc";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(
                model.getNonFloatingTaskList().size() + model.getFloatingTaskList().size()
                ));
    }

}
