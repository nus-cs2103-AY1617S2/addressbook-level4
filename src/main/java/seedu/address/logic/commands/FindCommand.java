package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Finds and lists all tasks in task manager whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " meet alice";

    private final Set<String> keywords;
    private final String startDate;
    private final String endDate;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
        this.startDate = null;
        this.endDate = null;
    }

    public FindCommand(String endDate) throws IllegalDateTimeValueException {
        this.startDate = "";
        this.endDate = endDate;
        this.keywords = null;
    }

    public FindCommand(String startDate, String endDate) throws IllegalDateTimeValueException {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keywords = null;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public boolean isParsableDate(String dateTime) {
        dtParser.parse(dateTime);
        return !dtParser.parse(dateTime).isEmpty();
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            executeFindCommandLogic();
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        } catch (IllegalDateTimeValueException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    public void executeFindCommandLogic() throws IllegalDateTimeValueException {
        Date start;
        Date end;
        if (keywords == null) {
            if ("".equals(startDate) && isParsableDate(endDate)) {
                if (endDate.matches("[a-zA-Z]+")) {
                    end = dtParser.parse(endDate + "235959").get(0).getDates().get(0);
                } else {
                    end = dtParser.parse(endDate).get(0).getDates().get(0);
                }
                start = dtParser.parse("today 000000").get(0).getDates().get(0);

                saveCurrentState();
                model.updateFilteredTaskList(start, end);
            } else if (isParsableDate(startDate) && isParsableDate(endDate)) {
                if (startDate.matches("[a-zA-Z]+")) {
                    start = dtParser.parse(this.startDate + " 000000").get(0).getDates().get(0);
                } else {
                    start = dtParser.parse(this.startDate).get(0).getDates().get(0);
                }

                if (endDate.matches("[a-zA-Z]+")) {
                    end = dtParser.parse(this.endDate + " 235959").get(0).getDates().get(0);
                } else {
                    end = dtParser.parse(this.endDate).get(0).getDates().get(0);
                }

                saveCurrentState();
                if (end.before(start)) {
                    model.updateFilteredTaskList(end, start);
                } else {
                    model.updateFilteredTaskList(start, end);
                }

            } else {
                throw new IllegalDateTimeValueException();
            }
        } else {
            saveCurrentState();
            model.updateFilteredTaskList(keywords);
        }
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getRawTaskManager().getImmutableTaskList(),
                        model.getRawTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isMutating() {
        return false;
    }

}
