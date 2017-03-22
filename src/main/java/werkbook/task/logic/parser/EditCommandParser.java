package werkbook.task.logic.parser;

import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_STARTDATETIME;
import static werkbook.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.EditCommand;
import werkbook.task.logic.commands.EditCommand.EditTaskDescriptor;
import werkbook.task.logic.commands.IncorrectCommand;
import werkbook.task.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DESCRIPTION, PREFIX_STARTDATETIME,
                PREFIX_ENDDATETIME, PREFIX_TAG);
        try {
            argsTokenizer.tokenize(args);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        List<Optional<String>> preambleFields = ParserUtil
                .splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor
                    .setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setStartDateTime(
                    ParserUtil.parseStartDateTime(argsTokenizer.getValue(PREFIX_STARTDATETIME)));
            editTaskDescriptor
                    .setEndDateTime(ParserUtil.parseEndDateTime(argsTokenizer.getValue(PREFIX_ENDDATETIME)));
            editTaskDescriptor
                    .setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
