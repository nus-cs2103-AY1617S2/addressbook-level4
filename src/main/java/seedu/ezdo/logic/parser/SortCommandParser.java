//@@author A0138907W
package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Parses input arguments and creates a new SortCommand object.
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
        String sortCriteriaArgument = sortArguments[INDEX_OF_SORT_CRITERIA];
        String sortOrderArgument = sortArguments[INDEX_OF_SORT_ORDER];

        SortCriteria sortCriteria = getSortCriteria(sortCriteriaArgument);
        Boolean isSortedAscending = checkIfSortedAscending(sortOrderArgument);

        return new SortCommand(sortCriteria, isSortedAscending);
    }

    /**
     * Returns the {@code SortCriteria} that corresponds to given {@code sortCriteriaArgument}.
     * @param sortCriteriaArgument A string which represents a sort criteria.
     * @return The matching {@code SortCriteria}. If no match is found, returns {@code null}.
     */
    private SortCriteria getSortCriteria(String sortCriteriaArgument) {
        SortCriteria sortCriteria;
        switch (sortCriteriaArgument) {
        case SORT_KEYWORD_NAME:
            sortCriteria = SortCriteria.NAME;
            break;
        case SORT_KEYWORD_PRIORITY:
            sortCriteria = SortCriteria.PRIORITY;
            break;
        case SORT_KEYWORD_START_DATE:
            sortCriteria = SortCriteria.START_DATE;
            break;
        case SORT_KEYWORD_DUE_DATE:
            sortCriteria = SortCriteria.DUE_DATE;
            break;
        default:
            sortCriteria = null;
            break;
        }
        return sortCriteria;
    }

    /**
     * Checks if the {@code sortOrder} represents ascending order.
     * @param sortOrder A string representing a sort order keyword ("a" or "d").
     * @return true if the sort order is ascending, false if it is descending.
     * If the given sortOrder does not match any keyword, returns null.
     */
    private Boolean checkIfSortedAscending(String sortOrder) {
        Boolean isSortedAscending;
        if (sortOrder == null) {
            // If no sort order was specified, then default to ascending sort order.
            isSortedAscending = true;
        } else {
            switch(sortOrder) {
            case KEYWORD_ASCENDING:
                isSortedAscending = true;
                break;
            case KEYWORD_DESCENDING:
                isSortedAscending = false;
                break;
            default:
                isSortedAscending = null;
                break;
            }
        }
        return isSortedAscending;
    }

}
