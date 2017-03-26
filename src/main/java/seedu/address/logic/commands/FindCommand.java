package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.model.tag.UniqueTagList;


/**
 * Finds and lists all todos in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all todos whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "groceries";
    public static final String MESSAGE_EMPTY_ERROR = "Cannot leave fields blank.";

    private Set<String> keywords;
    private UniqueTagList tags;

    /**
     * Creates a FindCommand with keywords as parameters
     */
    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    //@@author A0163720M
    /**
     * Creates a FindCommand with keywords and tags as parameters
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public FindCommand(Set<String> keywords, UniqueTagList tags) {
        this.keywords = keywords;
        this.tags = tags;
    }

    //@@author A0163720M
    /**
     * Creates a FindCommand with tags as parameters
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public FindCommand(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTodoList(keywords);
        return new CommandResult(getMessageForTodoListShownSummary(model.getFilteredTodoList().size()));
    }
}
