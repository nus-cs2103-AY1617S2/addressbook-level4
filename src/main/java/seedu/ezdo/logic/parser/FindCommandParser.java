package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.commons.util.SearchParameters;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.parser.ArgumentTokenizer.Prefix;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.TaskDate;

//@@author A0141010L
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        assert args != null;

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STARTDATE, PREFIX_DUEDATE,
                PREFIX_RECUR, PREFIX_TAG);
        String[] splitNames = tokenize(args, argsTokenizer);

        Optional<Priority> findPriority = null;
        Optional<TaskDate> findStartDate = null;
        Optional<TaskDate> findDueDate = null;
        Optional<Recur> findRecur = null;
        Set<String> findTags = null;
        boolean searchBeforeStartDate = false;
        boolean searchBeforeDueDate = false;
        boolean searchAfterStartDate = false;
        boolean searchAfterDueDate = false;

        try {

            Optional<String> optionalStartDate = getOptionalValue(argsTokenizer, PREFIX_STARTDATE);
            Optional<String> optionalDueDate = getOptionalValue(argsTokenizer, PREFIX_DUEDATE);

            boolean[] booleanDateArray = setBooleanParameters(optionalStartDate, optionalDueDate);

            int beforeStartIndex = 0; // indicates the position in array
            int beforeDueIndex = 1;
            int afterStartIndex = 2;
            int afterDueIndex = 3;

            searchBeforeStartDate = booleanDateArray[beforeStartIndex];
            searchBeforeDueDate = booleanDateArray[beforeDueIndex];
            searchAfterStartDate = booleanDateArray[afterStartIndex];
            searchAfterDueDate = booleanDateArray[afterDueIndex];

            optionalStartDate = setOptionalStartDate(optionalStartDate);
            optionalDueDate = setOptionalDueDate(optionalDueDate);

            boolean isFind = true;
            findStartDate = ParserUtil.parseStartDate(optionalStartDate, isFind);
            findDueDate = ParserUtil.parseDueDate(optionalDueDate, isFind);
            findRecur = ParserUtil.parseRecur(getOptionalValue(argsTokenizer, PREFIX_RECUR));
            findTags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            findPriority = ParserUtil.parsePriority(getOptionalValue(argsTokenizer, PREFIX_PRIORITY));

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        Set<String> keywords = new HashSet<String>(Arrays.asList(splitNames));
        SearchParameters searchParameters = new SearchParameters.Builder().name(keywords).priority(findPriority)
                .startDate(findStartDate).dueDate(findDueDate).recur(findRecur).tags(findTags)
                .startBefore(searchBeforeStartDate).dueBefore(searchBeforeDueDate).startAfter(searchAfterStartDate)
                .dueAfter(searchAfterDueDate).build();

        return new FindCommand(searchParameters);
    }

    /**
     * Get the optional value of a specified {@code prefix} from a
     * {@code tokenizer}
     */
    private Optional<String> getOptionalValue(ArgumentTokenizer tokenizer, Prefix prefix) {
        Optional<String> optionalString;
        if (!tokenizer.getValue(prefix).isPresent()) {
            optionalString = Optional.empty();
        } else {
            optionalString = Optional.of(tokenizer.getValue(prefix).get());
        }
        return optionalString;
    }

    /**
     * Removes "before" prefix from the start of a given {@code String} of
     * taskDate
     */
    private Optional<String> parseFindBefore(Optional<String> taskDate) {
        Optional<String> optionalDate;
        String taskDateString = taskDate.get();
        String commandString = taskDateString.substring(6, taskDateString.length()).trim();
        optionalDate = Optional.of(commandString);
        return optionalDate;
    }

    /**
     * Removes "after" prefix from the start of a given String {@code String} of
     * taskDate
     */
    private Optional<String> parseFindAfter(Optional<String> taskDate) {
        Optional<String> optionalDate;
        String taskDateString = taskDate.get();
        String commandString = taskDateString.substring(5, taskDateString.length()).trim();
        optionalDate = Optional.of(commandString);
        return optionalDate;
    }

    /**
     * Checks if an optional {@code String} of taskDate has the prefix "before"
     * or "After"
     */
    private boolean isFindBefore(Optional<String> taskDate) {

        if (!taskDate.isPresent()) {
            return false;
        }

        String taskDateString = taskDate.get();
        int prefixLength = 6;
        String prefixToCompare1 = "before";
        String prefixToCompare2 = "Before";
        int prefixLength = prefixToCompare1.length();


        if (taskDateString.length() <= prefixLength) {
            return false;
        }

        String byPrefix = taskDateString.substring(0, prefixLength);
        return byPrefix.equals(prefixToCompare1) || byPrefix.equals(prefixToCompare2);

    }

    /**
     * Checks if an optional {@code String} of taskDate has the prefix "after"
     * or "After"
     */
    private boolean isFindAfter(Optional<String> taskDate) {

        if (!taskDate.isPresent()) {
            return false;
        }

        String taskDateString = taskDate.get();
        String prefixToCompare1 = "after";
        String prefixToCompare2 = "After";
        int prefixLength = prefixToCompare1.length();


        if (taskDateString.length() <= prefixLength) {
            return false;
        }

        String byPrefix = taskDateString.substring(0, prefixLength);
        return byPrefix.equals(prefixToCompare1) || byPrefix.equals(prefixToCompare2);

    }

    /**
     * Check whether user is finding before or after the taskdate and returns a
     * boolean array of size 4 with 4 boolean values
     */
    private boolean[] setBooleanParameters(Optional<String> optionalStartDate, Optional<String> optionalDueDate) {

        boolean searchBeforeStartDate = false;
        boolean searchBeforeDueDate = false;
        boolean searchAfterStartDate = false;
        boolean searchAfterDueDate = false;

        if (isFindBefore(optionalStartDate)) {
            searchBeforeStartDate = true;
        }

        if (isFindBefore(optionalDueDate)) {
            searchBeforeDueDate = true;
        }

        if (isFindAfter(optionalStartDate)) {
            searchAfterStartDate = true;
        }

        if (isFindAfter(optionalDueDate)) {
            searchAfterDueDate = true;
        }

        return new boolean[] { searchBeforeStartDate, searchBeforeDueDate, searchAfterStartDate, searchAfterDueDate };
    }

    private Optional<String> setOptionalStartDate(Optional<String> optionalStartDate) {

        if (isFindBefore(optionalStartDate)) {
            optionalStartDate = parseFindBefore(optionalStartDate);
        }

        if (isFindAfter(optionalStartDate)) {
            optionalStartDate = parseFindAfter(optionalStartDate);
        }

        return optionalStartDate;
    }

    private Optional<String> setOptionalDueDate(Optional<String> optionalDueDate) {

        if (isFindBefore(optionalDueDate)) {
            optionalDueDate = parseFindBefore(optionalDueDate);
        }

        if (isFindAfter(optionalDueDate)) {
            optionalDueDate = parseFindAfter(optionalDueDate);
        }

        return optionalDueDate;
    }

    /**
     * Get the {@code Names} to find, if any, from {@code Find} arguments
     */
    private String[] tokenize(String args, ArgumentTokenizer argsTokenizer) {
        argsTokenizer.tokenize(args);
        String namesToMatch = argsTokenizer.getPreamble().orElse("");
        String[] splitNames = namesToMatch.split("\\s+");
        return splitNames;
    }
}
