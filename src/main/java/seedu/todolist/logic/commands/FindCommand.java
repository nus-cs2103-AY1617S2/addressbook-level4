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
            + "To search by keyword: keyword_1 keyword_2 \n"
            + "To search by tag: " + CliSyntax.PREFIX_TAG.getPrefix() + "tag_1\n"
            + "To search for todos that start before a particular time: "
            + CliSyntax.PREFIX_START_TIME.getPrefix() + "START_TIME \n"
            + "To search for todos that end before a particular time: "
            + CliSyntax.PREFIX_END_TIME.getPrefix() + "END_TIME \n"
            + "To search for todos that were completed before a particular time: "
            + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + "COMPLETE_TIME \n"
            + "To search for all uncompleted todos: "
            + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + "\n"
            + "To search for all completed todos: "
            + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + "not \n"
            + "Example: " + COMMAND_WORD + " groceries\n"
            + "Example 2: " + COMMAND_WORD + " " + CliSyntax.PREFIX_TAG.getPrefix() + "milk\n"
            + "Example 3: " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_TIME.getPrefix() + "9:00AM 28/03/17 \n"
            + "Example 4: " + COMMAND_WORD + " " + CliSyntax.PREFIX_END_TIME.getPrefix() + "9:00AM 28/03/17 \n"
            + "Example 5: " + COMMAND_WORD + " " + CliSyntax.PREFIX_COMPLETE_TIME.getPrefix() + "9:00AM 28/03/17 \n";

    public static final String MESSAGE_EMPTY_ERROR = "Cannot leave fields blank.";

    private Set<String> keywords;
    private UniqueTagList tags;
    private Date startTime;
    private Date endTime;
    private Object completeTime;

    /**
     * Creates a FindCommand with keywords as parameters
     */
    public FindCommand(Set<String> keywords, Date startTime, Date endTime, Object completeTime, UniqueTagList tags) {
        this.keywords = keywords;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completeTime = completeTime;
        this.tags = tags;
    }

    //@@author A0163720M
    @Override
    public CommandResult execute() {
        model.updateFilteredTodoList(keywords, startTime, endTime, completeTime, tags);
        return new CommandResult(getMessageForTodoListShownSummary(model.getFilteredTodoList().size()));
    }
    //@@author
}
