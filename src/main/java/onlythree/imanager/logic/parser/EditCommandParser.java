package onlythree.imanager.logic.parser;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static onlythree.imanager.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.EditCommand;
import onlythree.imanager.logic.commands.EditCommand.EditTaskDescriptor;
import onlythree.imanager.logic.commands.IncorrectCommand;
import onlythree.imanager.model.tag.UniqueTagList;

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
        DateTimeExtractor dateTimeExtractor = new DateTimeExtractor(args);
        // process StartEndDateTime first because it is more likely to fail due to more constraints
        // e.g. from [some date to some date] will be parsed as a single date
        // if we process only the startDateTime first
        dateTimeExtractor.processRawStartEndDateTime();
        // give priority to parsing raw deadline before processing the startDateTime and endDateTime separately
        // e.g. so cases such as [edit 10 test by 2 days from 25 Apr] works
        // Note that however [edit 10 test by 2 days later from 25 Apr] will actually fall through
        // and have no deadline found as later is not a recognized token by Natty.
        // Thus two date groups of dates [2 days] and [25 Apr] will be the output.
        dateTimeExtractor.processRawDeadline();
        // since the example above have fell through, the Task name will become [test by 2 days later]
        // and the start date-time becomes 25 Apr if the task already has an end date-time.
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
