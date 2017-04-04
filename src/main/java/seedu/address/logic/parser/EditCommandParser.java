package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_CHANGE_TASK_TO_EVENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.DateTimeException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    // @@author A0110491U
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_STARTDATE,
                PREFIX_ENDDATE, PREFIX_BYDATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_BYTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 3);

        Optional<Integer> index = preambleFields.get(1).flatMap(ParserUtil::parseIndexAlone);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Optional<String> typeToEdit = preambleFields.get(0);
        if (!typeToEdit.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        try {
            String type = typeToEdit.get();
            boolean priorityExists = argsTokenizer.getValue(PREFIX_PRIORITY).isPresent();
            boolean startdateExists = argsTokenizer.getValue(PREFIX_STARTDATE).isPresent();
            boolean enddateExists = argsTokenizer.getValue(PREFIX_ENDDATE).isPresent();
            boolean bydateExists = argsTokenizer.getValue(PREFIX_BYDATE).isPresent();
            boolean bytimeExists = argsTokenizer.getValue(PREFIX_BYTIME).isPresent();
            boolean starttimeExists = argsTokenizer.getValue(PREFIX_STARTTIME).isPresent();
            boolean endtimeExists = argsTokenizer.getValue(PREFIX_ENDTIME).isPresent();

            // trying to morph a task/deadline into an event
            if (priorityExists && (startdateExists || enddateExists || starttimeExists || endtimeExists)) {
                throw new IllegalValueException(MESSAGE_CANNOT_CHANGE_TASK_TO_EVENT);
            }
            // trying to morph a task/deadline into an event
            if ((bydateExists || bytimeExists)
                    && (startdateExists || enddateExists || starttimeExists || endtimeExists)) {
                throw new IllegalValueException(MESSAGE_CANNOT_CHANGE_TASK_TO_EVENT);
            }
            // trying to morph an event to a task/deadline
            if ((startdateExists || enddateExists || starttimeExists || endtimeExists)
                    && (bydateExists || priorityExists || bytimeExists)) {
                throw new IllegalValueException(MESSAGE_CANNOT_CHANGE_TASK_TO_EVENT);
            }
            //decide what to do depending on whether event or task is input
            if (type.equals("ev")) {
                setEventInfo(argsTokenizer, preambleFields, editEventDescriptor);
            } else if (type.equals("ts")) {
                setTaskInfo(argsTokenizer, preambleFields, editTaskDescriptor);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (DateTimeException dte) {
            return new IncorrectCommand(dte.getMessage());
        }

        if (!editEventDescriptor.isAnyFieldEdited() && !editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }
        String taskorevent = preambleFields.get(0).get();
        try {
            return new EditCommand(index.get(), editEventDescriptor, editTaskDescriptor, taskorevent);
        } catch (IllegalValueException ile) {
            return new IncorrectCommand(EditCommand.MESSAGE_ILLEGAL_EVENT_END_DATETIME);
        }
    }

    /**
     * This method sets the task information using the editTaskDescriptor
     * @param argsTokenizer
     * @param preambleFields
     * @param editTaskDescriptor
     * @throws IllegalValueException
     */
    private void setTaskInfo(ArgumentTokenizer argsTokenizer, List<Optional<String>> preambleFields,
            EditTaskDescriptor editTaskDescriptor) throws IllegalValueException, DateTimeException {
        editTaskDescriptor.setDescription(ParserUtil.parseDescription(preambleFields.get(2)));
        editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
        editTaskDescriptor.setByDate(ParserUtil.parseByDate(argsTokenizer.getValue(PREFIX_BYDATE)));
        editTaskDescriptor.setByTime(ParserUtil.parseByTime(argsTokenizer.getValue(PREFIX_BYTIME)));
        editTaskDescriptor.setLocation(ParserUtil.parseLocation(argsTokenizer.getValue(PREFIX_LOCATION)));
        editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
    }

    /**
     * This method sets the event information using the editEventDescriptor
     * @param argsTokenizer
     * @param preambleFields
     * @param editEventDescriptor
     * @throws IllegalValueException
     */
    private void setEventInfo(ArgumentTokenizer argsTokenizer, List<Optional<String>> preambleFields,
            EditEventDescriptor editEventDescriptor) throws IllegalValueException, DateTimeException {
        editEventDescriptor.setDescription(ParserUtil.parseDescription(preambleFields.get(2)));
        editEventDescriptor.setStartDate(ParserUtil.parseStartDate(argsTokenizer.getValue(PREFIX_STARTDATE)));
        editEventDescriptor.setEndDate(ParserUtil.parseEndDate(argsTokenizer.getValue(PREFIX_ENDDATE)));
        editEventDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
        editEventDescriptor.setEndTime(ParserUtil.parseEndTime(argsTokenizer.getValue(PREFIX_ENDTIME)));
        editEventDescriptor.setLocation(ParserUtil.parseLocation(argsTokenizer.getValue(PREFIX_LOCATION)));
        editEventDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
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
