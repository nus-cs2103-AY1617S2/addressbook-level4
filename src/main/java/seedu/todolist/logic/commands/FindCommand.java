package seedu.todolist.logic.commands;

import java.util.Date;
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
            + "Example 2: " + COMMAND_WORD + CliSyntax.PREFIX_TAG + "milk\n";
    public static final String MESSAGE_EMPTY_ERROR = "Cannot leave fields blank.";

    public static enum FindTime {
        START_TIME,
        END_TIME,
        COMPLETE_TIME
    }

    private Set<String> keywords;
    private UniqueTagList tags;
    private Date startTime;
    private Date endTime;
    private Date completeTime;

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

    public FindCommand(Date date, FindTime timeToFind) {
        if (timeToFind == FindTime.START_TIME) {
            this.startTime = date;
        } else if (timeToFind == FindTime.END_TIME) {
            this.endTime = date;
        } else if (timeToFind == FindTime.COMPLETE_TIME) {
            this.completeTime = date;
        }
    }
    //@@author A0163720M
    @Override
    public CommandResult execute() {
        if (tags != null) {
            model.updateFilteredTodoList(tags);
        } else if (startTime != null) {
            model.filterByStartTime(startTime);
        } else if (endTime != null) {
            model.filterByEndTime(endTime);
        } else if (completeTime != null) {
            model.filterByCompleteTime(completeTime);
        } else {
            model.updateFilteredTodoList(keywords);
        }

        return new CommandResult(getMessageForTodoListShownSummary(model.getFilteredTodoList().size()));
    }
    //@@author
}
