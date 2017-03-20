package seedu.taskboss.logic.commands;

import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Finds and lists all tasks in TaskBoss whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_SHORT = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Finds all tasks whose names/information/datetime contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME or sd/STARTDATETIME or ed/ENDDATETIME or i/INFORMATION \n"
            + "Example: " + COMMAND_WORD + " n/meeting" + " || " + COMMAND_WORD_SHORT + " sd/Mar";

    private final String keywords;
    private final String prefix;

    public FindCommand(String pre, String keywords) {
        this.prefix = pre;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (prefix.equals(PREFIX_NAME.toString())) {
            String[] keywordsList = keywords.split("\\s+");
            final Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywordsList));
            model.updateFilteredTaskListByName(keywordSet);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (prefix.equals(PREFIX_START_DATE.toString())) {
            model.updateFilteredTaskListByStartDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (prefix.equals(PREFIX_END_DATE.toString())) {
            model.updateFilteredTaskListByEndDateTime(keywords);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } else { //prefix.equals(PREFIX_INFORMATION.toString()))
            String[] keywordsList = keywords.split("\\s+");
            final Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywordsList));
            model.updateFilteredTaskListByInformation(keywordSet);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }
    }

}
