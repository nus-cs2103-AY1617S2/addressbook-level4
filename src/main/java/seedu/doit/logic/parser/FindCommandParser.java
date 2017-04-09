package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_END;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_START;
import static seedu.doit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.IncorrectCommand;

//@@author A0146809W
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser {

    public static final String NO_NAME_AFTER_PREFIX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            "There must be a name present after n/.\nExample: find n/Project Work");
    public static final String NO_PRIORITY_AFTER_PREFIX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            "There must be at least a valid priority present after p/. Valid priorities are high, med and low only.\n"
                    + "Examples:\nfind p/high\nfind p/high low");
    public static final String NO_STARTTIME_AFTER_PREFIX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            "There must be at least a valid start time or date present after s/ in a MM/DD/YYYY HH:MM format.\n"
                    + "Examples:\nfind s/23:59\nfind s/04/31/2020\nfind s/04/31/2020 23:59");
    public static final String NO_TAGS_AFTER_PREFIX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            "There must be a tag name present after t/.\nExample: find t/Friends");
    public static final String NO_ENDTIME_AFTER_PREFIX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            "There must be at least a valid end time or date present after e/ in a MM/DD/YYYY HH:MM format.\n"
                    + "Examples:\nfind e/23:59\nfind e/04/31/2020\nfind e/04/31/2020 23:59");

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */

    @Override
    public Command parse(String args) {

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NAME, PREFIX_PRIORITY, PREFIX_START, PREFIX_END,
                PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<String> nameKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_NAME)
                .orElse(Collections.emptyList());
        Set<String> nameKeyWordSet = new HashSet<>(nameKeyWords);
        List<String> priorityKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_PRIORITY)
                .orElse(Collections.emptyList());
        Set<String> priorityKeyWordSet = new HashSet<>(priorityKeyWords);
        List<String> startTimeKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_START)
                .orElse(Collections.emptyList());
        Set<String> startKeyWordSet = new HashSet<>(startTimeKeyWords);
        List<String> endTimeKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_END)
                .orElse(Collections.emptyList());
        Set<String> endKeyWordSet = new HashSet<>(endTimeKeyWords);
        List<String> tagsKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_TAG)
                .orElse(Collections.emptyList());
        Set<String> tagsKeyWordSet = new HashSet<>(tagsKeyWords);
        List<String> descriptionKeyWords = argsTokenizer.getAllValuesAfterPrefixAndSpaces(PREFIX_DESCRIPTION)
                .orElse(Collections.emptyList());
        Set<String> descKeyWordSet = new HashSet<>(descriptionKeyWords);

        if (isAllKeyWordSetEmpty(nameKeyWordSet, priorityKeyWordSet, startKeyWordSet, endKeyWordSet, tagsKeyWordSet,
                descKeyWordSet)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (nameKeyWords.contains("")) {
            return new IncorrectCommand(NO_NAME_AFTER_PREFIX);
        }
        if (!isValidPriorityKeyWordsPresent(priorityKeyWords, priorityKeyWordSet)) {
            return new IncorrectCommand(NO_PRIORITY_AFTER_PREFIX);
        }
        if (startTimeKeyWords.contains("")) {
            return new IncorrectCommand(NO_STARTTIME_AFTER_PREFIX);
        }
        if (endTimeKeyWords.contains("")) {
            return new IncorrectCommand(NO_ENDTIME_AFTER_PREFIX);
        }
        if (tagsKeyWords.contains("")) {
            return new IncorrectCommand(NO_TAGS_AFTER_PREFIX);
        }

        // keywords delimited by whitespace
        return new FindCommand(nameKeyWordSet, startKeyWordSet, endKeyWordSet, priorityKeyWordSet, tagsKeyWordSet,
                descKeyWordSet);

    }

    /**
     * Checks if the priority keywords inputed by user is valid
     *
     * @param priorityKeyWords
     * @param priorityKeyWordSet
     * @return true if high med and low are either present in the priority
     *         keywords list else false if non of them present.
     */
    private boolean isValidPriorityKeyWordsPresent(List<String> priorityKeyWords, Set<String> priorityKeyWordSet) {
        if (priorityKeyWordSet.isEmpty()) {
            return true;
        }
        if (priorityKeyWords.contains("")) {
            return false;
        } else if (priorityKeyWords.contains("high") || priorityKeyWords.contains("med")
                || priorityKeyWords.contains("low")) {
            return true;
        }
        return false;
    }

    private boolean isAllKeyWordSetEmpty(Set<String> nameKeyWordSet, Set<String> priorityKeyWordSet,
            Set<String> startKeyWordSet, Set<String> deadlineKeyWordSet, Set<String> tagsKeyWordSet,
            Set<String> descKeyWordSet) {

        return nameKeyWordSet.isEmpty() && priorityKeyWordSet.isEmpty() && startKeyWordSet.isEmpty()
                && deadlineKeyWordSet.isEmpty() && tagsKeyWordSet.isEmpty() && descKeyWordSet.isEmpty();
    }

}
