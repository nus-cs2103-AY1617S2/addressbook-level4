package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_AS;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.EditCommand;
import seedu.doist.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.TaskDate;

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
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_AS,
                PREFIX_UNDER, PREFIX_BY, PREFIX_FROM, PREFIX_TO);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);
        ArrayList<String> tokens = ParserUtil.getParameterKeysFromString(args);
        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent() ||
            !argsTokenizer.validateTokens(tokens) ||
            (argsTokenizer.validateDate(tokens) == ArgumentTokenizer.DATE_INVALID)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setDesc(ParserUtil.parseDesc(preambleFields.get(1)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_AS)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_UNDER))));
            Date startDate = null;
            Date endDate = null;
            int dateFormat = argsTokenizer.getDateFormat();
            switch (dateFormat) {
            case ArgumentTokenizer.DATE_NIL : break;
            case ArgumentTokenizer.DATE_BY :  String deadline = argsTokenizer.getValue(PREFIX_BY).get();
                if (deadline.isEmpty()) {
                    editTaskDescriptor.setDates(Optional.of(new TaskDate()));
                } else {
                    startDate = TaskDate.parseDate(deadline);
                    endDate = TaskDate.parseDate(deadline);
                    boolean validDate = TaskDate.validateDate(startDate, endDate);
                    if (!validDate) {
                        throw new IllegalValueException("Incorrect Dates");
                    }
                    editTaskDescriptor.setDates(Optional.of(new TaskDate(startDate, endDate)));
                }
                                              break;
            case ArgumentTokenizer.DATE_FROM : String start = argsTokenizer.getValue(PREFIX_FROM).get();
                String end = argsTokenizer.getValue(PREFIX_TO).get();
                if (start.isEmpty() && end.isEmpty()) {
                    editTaskDescriptor.setDates(Optional.of(new TaskDate()));
                } else {
                    startDate = TaskDate.parseDate(start);
                    endDate = TaskDate.parseDate(end);
                    boolean validDate = TaskDate.validateDate(startDate, endDate);
                    if (!validDate) {
                        throw new IllegalValueException("Incorrect Dates");
                    }
                    editTaskDescriptor.setDates(Optional.of(new TaskDate(startDate, endDate)));
                }
                                               break;
            default : break;
            }

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
