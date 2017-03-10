package seedu.address.logic.commands;

import java.util.Date;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.DateTimeParserManager;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meet alice";

    private static DateTimeParser dateParser;
    private final Set<String> keywords;
    private final Date startDate;
    private final Date endDate;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
        this.startDate = null;
        this.endDate = null;
    }

    public FindCommand(String endDate) throws IllegalDateTimeValueException {
        if (isParsableDate(endDate)) {
            if (endDate.matches("[a-zA-Z]+")) {
                this.endDate = dateParser.parse(endDate + "235959").get(0).getDates().get(0);
            } else {
                this.endDate = dateParser.parse(endDate).get(0).getDates().get(0);
            }
        } else {
            throw new IllegalDateTimeValueException();
        }
        this.startDate = dateParser.parse("today 000000").get(0).getDates().get(0);
        this.keywords = null;
    }

    public FindCommand(String startDate, String endDate) throws IllegalDateTimeValueException {
        if (isParsableDate(startDate) && isParsableDate(endDate)) {
            if (startDate.matches("[a-zA-Z]+")) {
                this.startDate = dateParser.parse(startDate + " 000000").get(0).getDates().get(0);
            } else {
                this.startDate = dateParser.parse(startDate).get(0).getDates().get(0);
            }

            if (endDate.matches("[a-zA-Z]+")) {
                this.endDate = dateParser.parse(endDate + " 235959").get(0).getDates().get(0);
            } else {
                this.endDate = dateParser.parse(endDate).get(0).getDates().get(0);
            }
        } else {
            throw new IllegalDateTimeValueException();
        }
        this.keywords = null;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public boolean isParsableDate(String dateTime) {
        dateParser = new DateTimeParserManager();
        return dateParser.parse(dateTime).size() > 0;
    }

    @Override
    public CommandResult execute() {
        if (keywords == null) {
            model.updateFilteredTaskList(startDate, endDate);
        } else {
            model.updateFilteredTaskList(keywords);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
