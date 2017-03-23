package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_PRIORITY;
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
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_PRIORITY, PREFIX_INFORMATION,
                        PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(argsTokenizer.getValue(PREFIX_NAME)));
            editTaskDescriptor.setPriorityLevel(ParserUtil.
                    parsePriorityLevel(argsTokenizer.getValue(PREFIX_PRIORITY)));

            Optional<DateTime> tempStartDateTimeOp = ParserUtil.parseDateTime(argsTokenizer.getValue(PREFIX_START_DATE));
            // if user input is "edit INDEX sd/", remove the current startDateTime
            if (!tempStartDateTimeOp.isPresent() && args.contains("sd/")) {
                editTaskDescriptor.setStartDateTime(Optional.of(new DateTime("")));
            } else {
                editTaskDescriptor.setStartDateTime(ParserUtil.
                        parseDateTime(argsTokenizer.getValue(PREFIX_START_DATE)));
            }

            Optional<DateTime> tempEndDateTimeOp = ParserUtil.parseDateTime(argsTokenizer.getValue(PREFIX_END_DATE));
            // if user input is "edit INDEX ed/", remove the current endDateTime
            if (!tempEndDateTimeOp.isPresent() && args.contains("ed/")) {
                editTaskDescriptor.setEndDateTime(Optional.of(new DateTime("")));
            } else {
                editTaskDescriptor.setEndDateTime(ParserUtil.
                        parseDateTime(argsTokenizer.getValue(PREFIX_END_DATE)));
            }

            editTaskDescriptor.setInformation(ParserUtil.parseInformation
                    (argsTokenizer.getValue(PREFIX_INFORMATION)));
            editTaskDescriptor.setCategories(parseCategoriesForEdit
                    (ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

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
        Collection<String> categorySet = categories.size() == 1 && categories.contains("")
                ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
