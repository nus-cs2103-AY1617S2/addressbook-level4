package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_DATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_RECURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_COMPLETED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS_INCOMPLETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_START;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.label.UniqueLabelList;

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
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_TIMEINTERVAL_START, PREFIX_TIMEINTERVAL_END, PREFIX_LABEL,
                        PREFIX_STATUS_COMPLETED, PREFIX_STATUS_INCOMPLETE, PREFIX_CLEAR_DATES, PREFIX_REMOVE_RECURRENCE,
                        PREFIX_RECURRENCE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setStartTime(ParserUtil.parseDeadline(
                    argsTokenizer.getValue(PREFIX_TIMEINTERVAL_START)));
            editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_TIMEINTERVAL_END)));
            if (!editTaskDescriptor.isDateEdited() && args.trim().contains(PREFIX_DEADLINE.getPrefix())) {
                editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_DEADLINE)));
            }
            if (args.trim().contains(PREFIX_CLEAR_DATES.getPrefix())) {
                editTaskDescriptor.setClearDates(Optional.ofNullable(true));
            }
            if (args.trim().contains(PREFIX_STATUS_COMPLETED.getPrefix())) {
                editTaskDescriptor.setIsCompleted(Optional.ofNullable(true));
            } else if (args.trim().contains(PREFIX_STATUS_INCOMPLETE.getPrefix())) {
                editTaskDescriptor.setIsCompleted(Optional.ofNullable(false));
            }
            if (args.trim().contains(PREFIX_REMOVE_RECURRENCE.getPrefix())) {
                editTaskDescriptor.setRemoveRecurrence(Optional.ofNullable(true));
                editTaskDescriptor.setIsRecurring(Optional.ofNullable(false));
                editTaskDescriptor.setRecurrence(Optional.empty());
            }
            if (args.trim().contains(PREFIX_RECURRENCE.getPrefix())) {
                editTaskDescriptor.setIsRecurring(Optional.ofNullable(true));
                editTaskDescriptor.setRecurrence(ParserUtil.parseRecurrence(argsTokenizer.getValue(PREFIX_RECURRENCE)));
            }
            editTaskDescriptor.setLabels(parseLabelsForEdit(ParserUtil.toSet(
                    argsTokenizer.getAllValues(PREFIX_LABEL))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalDateTimeValueException ipve) {
            return new IncorrectCommand(ipve.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> labels} into an {@code Optional<UniqueTagList>} if {@code labels} is non-empty.
     * If {@code labels} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero labels.
     */
    private Optional<UniqueLabelList> parseLabelsForEdit(Collection<String> labels) throws IllegalValueException {
        assert labels != null;

        if (labels.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> labelSet = labels.size() == 1 && labels.contains("") ? Collections.emptySet() : labels;
        return Optional.of(ParserUtil.parseLabels(labelSet));
    }

}
