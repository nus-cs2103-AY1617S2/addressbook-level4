package seedu.geekeep.logic.parser;

import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_ENDING_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_STARTING_DATETIME;
import static seedu.geekeep.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.UpdateCommand;
import seedu.geekeep.logic.commands.UpdateCommand.UpdateTaskDescriptor;
import seedu.geekeep.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_STARTING_DATETIME, PREFIX_ENDING_DATETIME, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
        try {
            updateTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
            updateTaskDescriptor.setEndDateTime(
                    ParserUtil.parseEndDateTime(argsTokenizer.getValue(PREFIX_ENDING_DATETIME)));
            updateTaskDescriptor.setStartDateTime(
                    ParserUtil.parseStartDateTime(argsTokenizer.getValue(PREFIX_STARTING_DATETIME)));
            updateTaskDescriptor.setDescription(
                    ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            updateTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!updateTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(index.get(), updateTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
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
