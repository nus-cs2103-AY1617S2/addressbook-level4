package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.model.tag.UniqueTagList;

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
        String argsTrimmed = args.trim();
        if (argsTrimmed.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        char taskType = argsTrimmed.charAt(0);

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_DATE, PREFIX_END_DATE,
                PREFIX_RECUR, PREFIX_PRIORITY, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(argsTrimmed.substring(1));
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_START_DATE)));
            editTaskDescriptor.setDate(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_END_DATE)));
            editTaskDescriptor.setRecur(ParserUtil.parseRecur(argsTokenizer.getValue(PREFIX_RECUR)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor, taskType);
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
