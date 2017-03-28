package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.EditCommand;
import seedu.taskboss.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private static final String EMPTY_STRING = "";

    //@@author A0143157J
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_INFORMATION, PREFIX_RECURRENCE,
                        PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.
                splitPreamble(argsTokenizer.getPreamble().orElse(EMPTY_STRING), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setPriorityLevel(ParserUtil.
                    parsePriorityLevel(argsTokenizer.getValue(PREFIX_PRIORITY)));

            Optional<DateTime> startDateTimeOp = ParserUtil.
                    parseDateTime(argsTokenizer.getValue(PREFIX_START_DATE));
            // if user input is "edit INDEX sd/", remove the current startDateTime
            if (!startDateTimeOp.isPresent() && args.contains(PREFIX_START_DATE.getPrefix())) {
                editTaskDescriptor.setStartDateTime(Optional.of(new DateTime(EMPTY_STRING)));
            } else {
                editTaskDescriptor.setStartDateTime(startDateTimeOp);
            }

            Optional<DateTime> endDateTimeOp = ParserUtil.
                    parseDateTime(argsTokenizer.getValue(PREFIX_END_DATE));
            // if user input is "edit INDEX ed/", remove the current endDateTime
            if (!endDateTimeOp.isPresent() && args.contains(PREFIX_END_DATE.getPrefix())) {
                editTaskDescriptor.setEndDateTime(Optional.of(new DateTime(EMPTY_STRING)));
            } else {
                editTaskDescriptor.setEndDateTime(endDateTimeOp);
            }

            editTaskDescriptor.setInformation(ParserUtil.parseInformation
                    (argsTokenizer.getValue(PREFIX_INFORMATION)));

            // if user in put is "edit INDEX r/", set recurrence as NONE
            if (args.contains(PREFIX_RECURRENCE.getPrefix()) &&
                    argsTokenizer.getValue(PREFIX_RECURRENCE).get().equals(EMPTY_STRING)) {
                editTaskDescriptor.setRecurrence(Optional.of(new Recurrence(Frequency.NONE)));
            } else {
                editTaskDescriptor.setRecurrence(ParserUtil.parseRecurrence
                        (argsTokenizer.getValue(PREFIX_RECURRENCE)));
            }

            editTaskDescriptor.setCategories(parseCategoriesForEdit
                    (ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    //@@author
    /**
     * Parses {@code Collection<String> categories} into
     * an {@code Optional<UniqueCategoryList>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueCategoryList>} containing zero categories.
     */
    private Optional<UniqueCategoryList> parseCategoriesForEdit(Collection<String> categories)
            throws IllegalValueException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains(EMPTY_STRING)
                ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
