package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.List;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_GROUP);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setEndDate(ParserUtil.parseEndDate(argsTokenizer.getValue(PREFIX_END_DATE)));
            editTaskDescriptor.setStartDate(ParserUtil.parseStartDate(argsTokenizer.getValue(PREFIX_START_DATE)));
            editTaskDescriptor.setGroup(ParserUtil.parseGroup(argsTokenizer.getValue(PREFIX_GROUP)));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */


}
