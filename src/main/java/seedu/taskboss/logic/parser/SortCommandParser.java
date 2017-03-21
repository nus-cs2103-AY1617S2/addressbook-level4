package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.SortCommand;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;

//@@author A0143157J
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    private static final String SORT_PARAM_START_DATE_TIME = "sd";
    private static final String SORT_PARAM_END_DATE_TIME = "ed";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws IllegalValueException 
     */
    public Command parse(String args) throws IllegalValueException {
        Optional<String> sortTypeOp = ParserUtil.parseSortType(args);
        if (!sortTypeOp.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String sortType = sortTypeOp.get();
        switch(sortType) {
        case SORT_PARAM_START_DATE_TIME:
            return new SortCommand(SortBy.START_DATE_TIME);

        case SORT_PARAM_END_DATE_TIME:
            return new SortCommand(SortBy.END_DATE_TIME);

        default:
            throw new IllegalValueException(SortCommand.ERROR_INVALID_SORT_TYPE);
        }
    }
}
