package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    //@@author A0140023E
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        DateTimeExtractor dateTimeExtractor = extractDateTimes(args);

        // TODO ArgumentTokenizer became very irrelevant in this class but is it still relevant for other classes?
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(dateTimeExtractor.getProcessedArgs());
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));

            // set raw dates as they are to be referenced from the task's previous dates
            editTaskDescriptor.setRawDeadline(dateTimeExtractor.getProcessedRawDeadline());
            editTaskDescriptor.setRawStartDateTime(dateTimeExtractor.getProcessedRawStartDateTime());
            editTaskDescriptor.setRawEndDateTime(dateTimeExtractor.getProcessedRawEndDateTime());

            editTaskDescriptor.setTagList(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Extracts date-times from the arguments if they exist and returns a {@link DateTimeExtractor} with the
     * processed raw date-times if they exist.
     *
     * @param args the arguments to extract date/time from
     */
    private DateTimeExtractor extractDateTimes(String args) {
        // TODO extract comments for testing
        DateTimeExtractor dateTimeExtractor = new DateTimeExtractor(args);
        // process StartEndDateTime first because it is more likely to fail due to more constraints
        // e.g. from [some date to some date] will be parsed as a single date if we process only the
        // startDateTime first
        // Pass rose from Uncle to Jane by tmr
        // we should not return an error because that case is a valid task
        dateTimeExtractor.processRawStartEndDateTime();
        // constraints for deadline are looser so it is less likely to fail
        dateTimeExtractor.processRawDeadline();
        // TODO process from later
        // because for example edit 10 test by 2 days from 25 Apr
        // note again that edit 10 test by 2 days later from 25 Apr will ignore the from later
        dateTimeExtractor.processRawStartDateTime();
        dateTimeExtractor.processRawEndDateTime();

        return dateTimeExtractor;
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
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
