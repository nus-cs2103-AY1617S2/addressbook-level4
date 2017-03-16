package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements CommandParser {

    private static final String NAME_SORT_KEYWORD = "name";
    private static final String PRIORITY_SORT_KEYWORD = "priority";
    private static final String START_DATE_SORT_KEYWORD = "start date";
    private static final String DUE_DATE_SORT_KEYWORD = "due date";
    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     */
    public Command parse(String args) {
        Optional<String> sortCriteriaField = ParserUtil.parseSortCriteria(args);
        if (!sortCriteriaField.isPresent()) {
            return new IncorrectCommand(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String sortCriteria = sortCriteriaField.get();

        switch (sortCriteria) {
        case NAME_SORT_KEYWORD:
            return new SortCommand(SortCriteria.NAME);
        case PRIORITY_SORT_KEYWORD:
            return new SortCommand(SortCriteria.PRIORITY);
        case START_DATE_SORT_KEYWORD:
            return new SortCommand(SortCriteria.START_DATE);
        case DUE_DATE_SORT_KEYWORD:
            return new SortCommand(SortCriteria.DUE_DATE);
        default:
            return new SortCommand(null);
        }
    }

}
