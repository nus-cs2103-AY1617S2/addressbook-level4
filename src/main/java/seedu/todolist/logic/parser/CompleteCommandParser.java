package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.CompleteCommand;
import seedu.todolist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new CompleteCommand object
 */
public class CompleteCommandParser {
    //@@author A0163786N
    /**
     * Parses the given {@code String} of arguments in the context of the CompleteCommand
     * and returns an CompleteCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(null), 2);
        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        Optional<String> completeTime = preambleFields.get(1);
        try {
            if (completeTime.isPresent()) {
                return new CompleteCommand(index.get(), completeTime.get());
            } else {
                return new CompleteCommand(index.get());
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
