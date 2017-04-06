//@@author A0105748B
package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bulletjournal.logic.parser.CliSyntax.FINISH_ARGS_FORMAT;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_BEGINTIME;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.bulletjournal.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.FinishCommand;
import seedu.bulletjournal.logic.commands.FinishCommand.EditTaskDescriptor;
import seedu.bulletjournal.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FinishCommand object
 */
public class FinishCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FinishCommand and returns an FinishCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        final Matcher matcher = FINISH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishCommand.MESSAGE_USAGE));
        }
        args = args + " s/done";
        ArgumentTokenizer argsTokenizer
            = new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_STATUS, PREFIX_BEGINTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setEmail(ParserUtil.parseEmail(argsTokenizer.getValue(PREFIX_STATUS)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new FinishCommand(index.get(), editTaskDescriptor);
    }
}
