package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.EditCommand;
import seedu.ezdo.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.model.tag.UniqueTagList;

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
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_EMAIL, PREFIX_STARTDATE, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setEmail(ParserUtil.parseEmail(argsTokenizer.getValue(PREFIX_EMAIL)));
            editTaskDescriptor.setStartDate(ParserUtil.parseStartDate(argsTokenizer.getValue(PREFIX_STARTDATE)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
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
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
