package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;
import static seedu.todolist.commons.core.GlobalConstants.TODO_TYPES;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_COMPLETE_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TODO_TYPE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    //@@author A0163720M
    // Format used to parse Date with zero time
    private static final String NO_TIME_FORMAT = "dd/MM/yyyy";
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws IllegalValueException
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TAG, PREFIX_COMPLETE_TIME, PREFIX_TODO_TYPE);
        argsTokenizer.tokenize(args);
        // Fetch the keyword string before the prefix
        Optional<String> keywordsString = argsTokenizer.getPreamble();
        Optional<List<String>> tags = argsTokenizer.getAllValues(PREFIX_TAG);
        Optional<String> startTime = argsTokenizer.getValue(PREFIX_START_TIME);
        Optional<String> endTime = argsTokenizer.getValue(PREFIX_END_TIME);
        Optional<String> completeTime = argsTokenizer.getValue(PREFIX_COMPLETE_TIME);
        Optional<String> todoType = argsTokenizer.getValue(PREFIX_TODO_TYPE);

        // User must enter either the search keyword or at least one parameter
        if (!(keywordsString.isPresent() || tags.isPresent() || startTime.isPresent()
            || endTime.isPresent() || completeTime.isPresent() || todoType.isPresent())) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        try {
            Set<String> keywordsSet = new HashSet<>();
            Date startTimeSet = null;
            Date endTimeSet = null;
            Object completeTimeSet = null;
            String todoTypeSet = null;
            final Set<Tag> tagsSet = new HashSet<>();

            if (keywordsString.isPresent()) {
                final String[] keywords = keywordsString.get().split("\\s+");
                keywordsSet.addAll(Arrays.asList(keywords));
            }
            if (startTime.isPresent()) {
                startTimeSet = parseDateParameter(startTime.get());
            }
            //@@author A0163786N
            if (endTime.isPresent()) {
                endTimeSet = parseDateParameter(endTime.get());
            }
            if (completeTime.isPresent()) {
                if (completeTime.get().equals("") || completeTime.get().equals("not")) {
                    completeTimeSet = completeTime.get();
                } else {
                    completeTimeSet = parseDateParameter(completeTime.get());
                }
            }
            //@@author A0163720M
            // string must be 'floating', 'event', or 'deadline'
            if (todoType.isPresent() && TODO_TYPES.contains(todoType.get())) {
                todoTypeSet = todoType.get();
            }
            if (tags.isPresent()) {
                // Store the individual tag strings in a set
                final Set<String> tagsStrings = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));

                for (String tagName : tagsStrings) {
                    tagsSet.add(new Tag(tagName));
                }
            }
            return new FindCommand(keywordsSet, startTimeSet, endTimeSet,
                    completeTimeSet, todoTypeSet, new UniqueTagList(tagsSet));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Date parseDateParameter(String dateString) throws IllegalValueException {
        if (dateString.equals("yesterday")) {
            return getDateOffsetWithZeroTime(0);
        } else if (dateString.equals("today")) {
            return getDateOffsetWithZeroTime(1);
        } else if (dateString.equals("tomorrow")) {
            return getDateOffsetWithZeroTime(2);
        } else {
            return StringUtil.parseDate(dateString, DATE_FORMAT);
        }
    }

    private Date getDateOffsetWithZeroTime(int offset) throws IllegalValueException {
        DateFormat formatter = new SimpleDateFormat(NO_TIME_FORMAT);
        Date todayWithZeroTime = StringUtil.parseDate(formatter.format(new Date()), NO_TIME_FORMAT);

        Calendar c = Calendar.getInstance();
        c.setTime(todayWithZeroTime);
        c.add(Calendar.DATE, offset);
        return c.getTime();
    }
    //@@author
}
