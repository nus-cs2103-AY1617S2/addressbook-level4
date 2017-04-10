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
import seedu.task.logic.commands.SmartAddCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

// @@author A0140063X-reused
/**
 * Parses input arguments and creates a new QuickAddCommand object
 */
public class SmartAddCommandParser extends CommandParser {

    private String remark;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * QuickAddCommand and returns an QuickAddCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_REMARK, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        Map<Prefix, List<String>> tokenizedArguments = argsTokenizer.getTokenizedArguments();
        remark = tokenizedArguments.containsKey(PREFIX_REMARK) ? argsTokenizer.getValue(PREFIX_REMARK).get()
                : "";
        try {
            return new SmartAddCommand(argsTokenizer.getPreamble().get().replace("\\", ""), remark.replace("\\", ""),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmartAddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IOException ioe) {
            return new IncorrectCommand(ioe.getMessage());
        }
    }

}
