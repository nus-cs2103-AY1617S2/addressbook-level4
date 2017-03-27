package seedu.todolist.logic.commands;

import java.util.Set;

import seedu.todolist.logic.parser.CliSyntax;
import seedu.todolist.model.tag.UniqueTagList;


/**
 * Finds and lists all todos in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all todos whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "To search by keyword: KEYWORD [MORE_KEYWORDS]...\n"
            + "To search by tag: " + CliSyntax.PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + "groceries\n"
            + "Example2: " + COMMAND_WORD + CliSyntax.PREFIX_TAG + "milk\n";
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
     * Creates a FindCommand with tags as parameters
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public FindCommand(UniqueTagList tags) {
        this.tags = tags;
    }
    //@@author

    //@@author A0163720M
    @Override
    public CommandResult execute() {
        if (this.tags != null) {
            model.updateFilteredTodoList(tags);
        } else {
            model.updateFilteredTodoList(keywords);
        }

        return new CommandResult(getMessageForTodoListShownSummary(model.getFilteredTodoList().size()));
    }
    //@@author
}
