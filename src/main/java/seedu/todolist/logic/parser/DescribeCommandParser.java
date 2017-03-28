package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.DescribeCommand;
import seedu.todolist.logic.commands.IncorrectCommand;

//@@author A0141647E
/*
 * Parse input arguments and return a new DescribeCommand object.
 */
public class DescribeCommandParser {

    /*
     * Parse the given {@code String} in the context of the Describe command
     * and return a DescribeCommand object for execution
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        Optional<String> description = preambleFields.get(1);

        if (!index.isPresent() || !description.isPresent() || index.get() < 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescribeCommand.MESSAGE_USAGE));
        }

        return new DescribeCommand(index.get(), description.get());
    }
}
