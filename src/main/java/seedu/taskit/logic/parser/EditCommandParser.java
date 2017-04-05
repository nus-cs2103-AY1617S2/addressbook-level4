package seedu.taskit.logic.parser;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.taskit.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskit.logic.commands.IncorrectCommand;
import seedu.taskit.model.tag.UniqueTagList;

//@@author A0141872E
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
                new ArgumentTokenizer(PREFIX_TITLE, PREFIX_START, PREFIX_END, PREFIX_PRIORITY, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_NOT_EDITED));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
                editTaskDescriptor.setTitle(ParserUtil.parseTitle(argsTokenizer.getValue(PREFIX_TITLE)));
                editTaskDescriptor.setStart(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_START)));
                editTaskDescriptor.setEnd(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_END)));
                editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
                editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        }catch (IllegalValueException ive) {
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

        Collection<String> tagSet = tags.size() == 1 && tags.contains("null") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
