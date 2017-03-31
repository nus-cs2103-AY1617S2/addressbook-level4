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
        assert args != null;
        // TODO use the new dateTimeExtractor class
        DateTimeExtractor dateTimeExtractor = new DateTimeExtractor(args);
        // TODO Returns an exception in a method? Doesn't make sense
        // Returns a string? seems brittle, therefore to rewrite the class to preserve state
        try {
            // process StartEndDateTime first because it is more constrained
            // TODO there are special from and to cases so maybe we don't have to necessarily process
            // this first. create a special optional regex for this?
            //dateTimeExtractor.processStartEndDateTime();
            dateTimeExtractor.processRawStartEndDateTime();
        } catch (IllegalValueException e) {
            // Dates can't be parsed so we silently skip first
            // all other exceptions have been handled
            // Pass rose from Uncle to Jane by tmr
            // we should not return an error because that case is a valid task
            System.out.println("No date is found for start and end date");
        }
        // TODO Returns an exception in a method? Doesn't make sense
        // Returns a string? seems brittle
        try {
            dateTimeExtractor.processRawDeadline();
        } catch (IllegalValueException e) {
            // No date is found so we silently skip
            System.out.println("No date found for deadline!");
        }

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

            editTaskDescriptor.setRawDeadline(dateTimeExtractor.getProcessedRawDeadline());
            editTaskDescriptor.setRawStartDateTime(dateTimeExtractor.getProcessedStartDateTime());
            editTaskDescriptor.setRawEndDateTime(dateTimeExtractor.getProcessedEndDateTime());


            //editTaskDescriptor.setDeadline(
            //        ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_DEADLINE_DATETIME)));
            //editTaskDescriptor.setStartEndDateTime(
            //        ParserUtil.parseStartEndDateTime(argsTokenizer.getValue(PREFIX_START_DATETIME),
            //                                         argsTokenizer.getValue(PREFIX_END_DATETIME)));
            editTaskDescriptor.setTagList(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        return new EditCommand(index.get(), editTaskDescriptor);
        // TODO note that there is a NoFieldEditedException handled in execute, thus to change
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
