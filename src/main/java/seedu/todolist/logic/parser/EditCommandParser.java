package seedu.todolist.logic.parser;


import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.EditCommand;
import seedu.todolist.logic.commands.EditCommand.EditTodoDescriptor;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {
    //@@author A0165043M
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TAG, PREFIX_TAG_ADD);
        argsTokenizer.tokenize(args);
        try {
            Optional<String> startTime = formatAndCheckValidTime(argsTokenizer.getValue(PREFIX_START_TIME));
            Optional<String> endTime = formatAndCheckValidTime(argsTokenizer.getValue(PREFIX_END_TIME));;
            EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();
            List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);
            Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
            if (!index.isPresent() || (startTime.isPresent() && !endTime.isPresent())) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            setEditTodoDescriptor(editTodoDescriptor, startTime, endTime);
            editTodoDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTodoDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));

            Optional<UniqueTagList> addTags = parseTagsForEdit(ParserUtil.toSet(
                    argsTokenizer.getAllValues(PREFIX_TAG_ADD)));
            if (addTags.isPresent()) {
                addTags.get().reverseOrder();
                editTodoDescriptor.setAddTags(addTags);
            }
            if (!editTodoDescriptor.isAnyFieldEdited() && !startTime.isPresent() && !endTime.isPresent()) {
                return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditCommand(index.get(), editTodoDescriptor);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (ParseException pe) {
            return new IncorrectCommand(pe.getMessage());
        }
    }

    private void setEditTodoDescriptor(EditTodoDescriptor editTodoDescriptor,
            Optional<String> startTime, Optional<String> endTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
            if (startTime.isPresent()) {
                if (!startTime.get().equals("")) {
                    editTodoDescriptor.setStartTime(dateFormat.parse(startTime.get()));
                } else {
                    editTodoDescriptor.setStartTime(getTodayPlusDays(0));
                }
            }
            if (endTime.isPresent()) {
                if (!endTime.get().equals("")) {
                    editTodoDescriptor.setEndTime(dateFormat.parse(endTime.get()));
                } else {
                    editTodoDescriptor.setEndTime(getTodayPlusDays(1));
                }
            }
        } catch (NoSuchElementException | ParseException e) {

        }
    }

    private Date getTodayPlusDays(int addDays) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, addDays);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateTimeFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
        try {
            dt = dateTimeFormat.parse("12:00AM" + " " + dateFormat.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    private Optional<String> formatAndCheckValidTime (Optional<String> time) throws ParseException {
        if (!time.equals(Optional.empty()) && !time.get().equals("")) {
            try {
                String[] dateAndTime = time.get().split(" ");
                if (dateAndTime.length == 1) { //date only
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.parse(time.get());
                    Optional<String> midnightPlusDate = Optional.of("12:00AM " + time.get());
                    return midnightPlusDate;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("h:mma dd/MM/yyyy");
                    dateFormat.parse(time.get());
                    return time;
                }
            } catch (ParseException e) {
                throw new ParseException(AddCommand.MESSAGE_INVALID_TIME, 0);
            }
        } else {
            return time;
        }
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
