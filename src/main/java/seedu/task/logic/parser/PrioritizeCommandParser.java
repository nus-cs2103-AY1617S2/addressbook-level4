//@@author A0113795Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.model.task.Priority.MESSAGE_PRIORITY_CONSTRAINTS;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.PrioritizeCommand;
import seedu.task.model.task.Priority;

public class PrioritizeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the PrioritizeCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) throws NoSuchElementException {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);
        Optional<String> newValue = preambleFields.get(1);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrioritizeCommand.MESSAGE_USAGE));
        }

        try {
            return new PrioritizeCommand(index.get(), new Priority(newValue.get()));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrioritizeCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_PRIORITY_CONSTRAINTS));
        }
    }
}
