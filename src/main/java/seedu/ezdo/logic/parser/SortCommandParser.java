//@@author A0138907W
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.ezdo.commons.exceptions.InvalidSortOrderException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements CommandParser {

    private static final String SORT_KEYWORD_NAME = "n";
    private static final String SORT_KEYWORD_PRIORITY = "p";
    private static final String SORT_KEYWORD_START_DATE = "s";
    private static final String SORT_KEYWORD_DUE_DATE = "d";

    private static final String KEYWORD_ASCENDING = "a";
    private static final String KEYWORD_DESCENDING = "d";

    private static final int INDEX_OF_SORT_CRITERIA = 0;
    private static final int INDEX_OF_SORT_ORDER = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String[]> sortArgumentsField = ParserUtil.parseSortCriteria(args);
        if (!sortArgumentsField.isPresent()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] sortArguments = sortArgumentsField.get();
        String sortCriteria = sortArguments[INDEX_OF_SORT_CRITERIA];
        String sortOrder = sortArguments[INDEX_OF_SORT_ORDER];

        Boolean isSortedAscending;

        if (sortOrder == null) {
            isSortedAscending = true;
        } else {
            try {
                isSortedAscending = determineIfIsSortedAscending(sortOrder);
            } catch (InvalidSortOrderException isoe) {
                isSortedAscending = null;
            }
        }

        switch (sortCriteria) {
        case SORT_KEYWORD_NAME:
            return new SortCommand(SortCriteria.NAME, isSortedAscending);
        case SORT_KEYWORD_PRIORITY:
            return new SortCommand(SortCriteria.PRIORITY, isSortedAscending);
        case SORT_KEYWORD_START_DATE:
            return new SortCommand(SortCriteria.START_DATE, isSortedAscending);
        case SORT_KEYWORD_DUE_DATE:
            return new SortCommand(SortCriteria.DUE_DATE, isSortedAscending);
        default:
            return new SortCommand(null, isSortedAscending);
        }
    }

    private boolean determineIfIsSortedAscending(String sortOrder) throws InvalidSortOrderException {
        Boolean isSortedAscending;
        switch(sortOrder) {
        case KEYWORD_ASCENDING:
            isSortedAscending = true;
            break;
        case KEYWORD_DESCENDING:
            isSortedAscending = false;
            break;
        default:
            throw new InvalidSortOrderException();
        }
        return isSortedAscending;
    }

}
