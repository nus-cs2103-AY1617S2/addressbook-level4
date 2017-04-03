package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.QuickAddCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;
import seedu.task.model.task.Remark;

// @@author A0140063X-reused
/**
 * Parses input arguments and creates a new QuickAddCommand object
 */
public class QuickAddCommandParser extends CommandParser {

    private String REMARK;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * QuickAddCommand and returns an QuickAddCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_REMARK, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Map<Prefix, List<String>> tokenizedArguments = argsTokenizer.getTokenizedArguments();
        REMARK = tokenizedArguments.containsKey(PREFIX_REMARK) ? argsTokenizer.getValue(PREFIX_REMARK).get()
                : Remark.DEFAULT_REMARK;
        try {
            return new QuickAddCommand(argsTokenizer.getPreamble().get().replace("\\", ""), REMARK.replace("\\", ""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickAddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IOException ioe) {
            return new IncorrectCommand(ioe.getMessage());
        }
    }

}
