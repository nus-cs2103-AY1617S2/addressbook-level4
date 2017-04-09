package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.EditCommand;
import seedu.taskmanager.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.model.tag.UniqueTagList;

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
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_DESCRIPTION,
                PREFIX_REPEAT, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            if (ParserUtil.parseTitle(preambleFields.get(1)).isPresent()) {
                editTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
            }
            if (argsTokenizer.getValue(PREFIX_STARTDATE).isPresent()) {
                editTaskDescriptor.setStartDate(ParserUtil.parseStartDate(argsTokenizer.getValue(PREFIX_STARTDATE)));
            }
            if (argsTokenizer.getValue(PREFIX_ENDDATE).isPresent()) {
                editTaskDescriptor.setEndDate(ParserUtil.parseEndDate(argsTokenizer.getValue(PREFIX_ENDDATE)));
            }
            if (argsTokenizer.getValue(PREFIX_DESCRIPTION).isPresent()) {
                editTaskDescriptor
                        .setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            }
            // @@author A0140032E
            if (argsTokenizer.getValue(PREFIX_REPEAT).isPresent()) {
                editTaskDescriptor.setRepeat(ParserUtil.parseRepeat(argsTokenizer.getValue(PREFIX_REPEAT)));
            }
            // @@author
            if (argsTokenizer.getAllValues(PREFIX_TAG).isPresent()) {
                editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
            }
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
