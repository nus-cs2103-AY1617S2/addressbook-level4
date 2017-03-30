package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.model.DoneStatus;

//@@author A0135739W
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private static final String TODAY = "today";
    private static final String TODAY_START = "0000h";
    private static final String TOMORROW = "tomorrow";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(
                PREFIX_BEFORE, PREFIX_AFTER, PREFIX_PRIORITY, PREFIX_TAG, PREFIX_ORDER);
        argsTokenizer.tokenize(args);
        Optional<String> preamble = argsTokenizer.getPreamble();
        String beforeDate = argsTokenizer.getValue(PREFIX_BEFORE).orElse("");
        String afterDate = argsTokenizer.getValue(PREFIX_AFTER).orElse("");
        String priority = argsTokenizer.getValue(PREFIX_PRIORITY).orElse("");
        String order = argsTokenizer.getValue(PREFIX_ORDER).orElse("");
        Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));

        try {
            if (!preamble.isPresent()) {
                return new ListCommand(DoneStatus.UNDONE_STRING, beforeDate, afterDate, priority, tags, order);
            }
            switch (preamble.get().toLowerCase()) {
            case DoneStatus.DONE_STRING:
                return new ListCommand(DoneStatus.DONE_STRING, beforeDate, afterDate, priority, tags, order);
            case DoneStatus.ALL_STRING:
                return new ListCommand(DoneStatus.ALL_STRING, beforeDate, afterDate, priority, tags, order);
            case DoneStatus.UNDONE_STRING:
            case "":
                return new ListCommand(DoneStatus.UNDONE_STRING, beforeDate, afterDate, priority, tags, order);
            case TODAY:
                return new ListCommand(DoneStatus.UNDONE_STRING, TOMORROW, TODAY_START, priority, tags, order);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
