package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    //@@author A0163673Y
    public static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_TAG, PREFIX_START, PREFIX_END, PREFIX_DUEDATE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(
                argsTokenizer.getPreamble().orElse(EMPTY_STRING), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(preambleFields.get(1)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
            editTaskDescriptor = parseDueDate(editTaskDescriptor, argsTokenizer);
            editTaskDescriptor = parseDuration(editTaskDescriptor, argsTokenizer);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    // helper method to handle parsing of due date
    private EditTaskDescriptor parseDueDate(EditTaskDescriptor editTaskDescriptor,
            ArgumentTokenizer argsTokenizer) throws IllegalValueException {
        if (argsTokenizer.getValue(PREFIX_DUEDATE).isPresent()
                && argsTokenizer.getValue(PREFIX_DUEDATE).get().equals(EMPTY_STRING)) {
            editTaskDescriptor.setDeleteDueDate(true);
        } else {
            editTaskDescriptor.setDueDate(ParserUtil.parseDueDate(argsTokenizer.getValue(PREFIX_DUEDATE)));
        }
        return editTaskDescriptor;
    }

    //helper method to handle parsing of duration
    private EditTaskDescriptor parseDuration(EditTaskDescriptor editTaskDescriptor,
            ArgumentTokenizer argsTokenizer) throws IllegalValueException {
        if (argsTokenizer.getValue(PREFIX_START).isPresent()
                && argsTokenizer.getValue(PREFIX_END).isPresent()
                && argsTokenizer.getValue(PREFIX_START).get().equals(EMPTY_STRING)
                && argsTokenizer.getValue(PREFIX_END).get().equals(EMPTY_STRING)) {
            editTaskDescriptor.setDeleteDuration(true);
        } else {
            editTaskDescriptor.setDurationStart(ParserUtil.parseString(argsTokenizer.getValue(PREFIX_START)));
            editTaskDescriptor.setDurationEnd(ParserUtil.parseString(argsTokenizer.getValue(PREFIX_END)));
        }
        return editTaskDescriptor;
    }
    //@@author

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
        Collection<String> tagSet = tags.size() == 1 && tags.contains(EMPTY_STRING) ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
