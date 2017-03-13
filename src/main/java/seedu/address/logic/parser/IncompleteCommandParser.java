package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncompleteCommand;
import seedu.address.logic.commands.IncompleteCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncompleteCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class IncompleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String argsOriginal) {
        assert argsOriginal != null;
        String args = argsOriginal + " p/5";
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_PRIORITY, PREFIX_INSTRUCTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = ParserUtil.parseIndex(argsOriginal);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncompleteCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            //editTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
            //editTaskDescriptor.setDate(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_DATE)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            //editTaskDescriptor.setInstruction(ParserUtil.parseInstruction(argsTokenizer.getValue(PREFIX_INSTRUCTION)));
            //editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(IncompleteCommand.MESSAGE_NOT_EDITED);
        }

        return new IncompleteCommand(index.get(), editTaskDescriptor);
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
