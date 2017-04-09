package seedu.address.logic.commands;

import java.util.Set;

//@@author A0164889E
/**
 * Finds and lists all persons in address book whose group contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose groups contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " learning";

    private final Set<String> keywords;

    public GroupCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonListGroup(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

}
//@@author
