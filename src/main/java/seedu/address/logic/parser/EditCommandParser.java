package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROMDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODATE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

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
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_TAG, PREFIX_FROMDATE, PREFIX_TODATE,
                        PREFIX_BYDATE, PREFIX_STARTTIME, PREFIX_ENDTIME);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditActivityDescriptor editActivityDescriptor = new EditActivityDescriptor();
        try {
            boolean priorityExists = argsTokenizer.getValue(PREFIX_PRIORITY).isPresent();
            boolean fromdateExists = argsTokenizer.getValue(PREFIX_FROMDATE).isPresent();
            boolean todateExists = argsTokenizer.getValue(PREFIX_TODATE).isPresent();
            boolean bydateExists = argsTokenizer.getValue(PREFIX_BYDATE).isPresent();
            boolean starttimeExists = argsTokenizer.getValue(PREFIX_STARTTIME).isPresent();
            boolean endtimeExists = argsTokenizer.getValue(PREFIX_ENDTIME).isPresent();

            if (priorityExists && (bydateExists || fromdateExists || todateExists || starttimeExists ||
                    endtimeExists)) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            if (bydateExists && (fromdateExists || todateExists || starttimeExists)) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            if (fromdateExists && (bydateExists || priorityExists)) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            if (todateExists && (bydateExists || priorityExists)) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            if (starttimeExists && (bydateExists || priorityExists)) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            if (endtimeExists && priorityExists) {
                throw new IllegalValueException("Event or Deadline or Task");
            }

            editActivityDescriptor.setDescription(ParserUtil.parseDescription(preambleFields.get(1)));
            editActivityDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editActivityDescriptor.setFromDate(ParserUtil.parseFromDate(argsTokenizer.getValue(PREFIX_FROMDATE)));
            editActivityDescriptor.setToDate(ParserUtil.parseToDate(argsTokenizer.getValue(PREFIX_TODATE)));
            editActivityDescriptor.setByDate(ParserUtil.parseByDate(argsTokenizer.getValue(PREFIX_BYDATE)));
            editActivityDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
            editActivityDescriptor.setEndTime(ParserUtil.parseEndTime(argsTokenizer.getValue(PREFIX_ENDTIME)));
            editActivityDescriptor.setLocation(ParserUtil.parseLocation(argsTokenizer.getValue(PREFIX_LOCATION)));
            editActivityDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editActivityDescriptor);
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
