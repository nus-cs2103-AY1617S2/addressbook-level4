package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;
import typetask.logic.commands.RemoveDeadlineCommand;

//@@author A0139926R
/**
 * Parses input arguments and creates a new RemoveDeadlineCommand object
 */
public class RemoveDeadlineCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an RemoveDeadlineCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveDeadlineCommand.MESSAGE_USAGE));
        }

        return new RemoveDeadlineCommand(index.get());
    }
}
