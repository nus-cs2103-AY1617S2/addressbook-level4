package seedu.taskboss.logic.commands;

import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Finds and lists all tasks in TaskBoss whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    private static final String ALL_WHITESPACE = "\\s+";
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_SHORT = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Finds all tasks with names or information containing any of "
            + "the specified keywords (case-sensitive),\n"
            + " or with dates specified in any of following formats"
            + " e.g 28 / Feb / Feb 28 / Feb 28, 2017,\n"
            + " and displays them in a list.\n"
            + "Parameters: k/NAME AND INFORMATION KEYWORDS or sd/START_DATE or ed/END_DATE \n"
            + "Example: " + COMMAND_WORD + " k/meeting" + " || " + COMMAND_WORD_SHORT + " sd/march 19";

    private final String keywords;
    private final String prefix;

    //@@author A0147990R
    public FindCommand(String pre, String keywords) {
        this.prefix = pre;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (prefix.equals(PREFIX_KEYWORD.toString())) {
            String[] keywordsList = keywords.split(ALL_WHITESPACE);
            final Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywordsList));
            model.updateFilteredTaskListByKeywords(keywordSet);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (prefix.equals(PREFIX_START_DATE.toString())) {
            model.updateFilteredTaskListByStartDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else { //find by end datetime
            model.updateFilteredTaskListByEndDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }
    }

}
