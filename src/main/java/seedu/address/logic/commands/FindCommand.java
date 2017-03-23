package seedu.address.logic.commands;

import java.util.Set;

/**
 * Finds and lists all activities in WhatsLeft whose description contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose description contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }
    //@@author A0110491U
    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(keywords);
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForActivityListShownSummary(model.getFilteredEventList().size() +
                model.getFilteredTaskList().size()));
    }

}
