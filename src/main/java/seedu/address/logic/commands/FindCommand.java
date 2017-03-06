package seedu.address.logic.commands;

import java.util.Set;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String PREFIX_NAME = "/n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME \n"
            + "Example: " + COMMAND_WORD + " n/meeting";

    private final Set<String> keywords;
    private final String prefix;

    public FindCommand(String pre, Set<String> keywords) {
        this.prefix = pre;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (prefix == PREFIX_NAME) {
            model.updateFilteredPersonList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    	} else {
            model.updateFilteredPersonList(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    	}
    }

}
