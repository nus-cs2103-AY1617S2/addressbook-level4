package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.DateTimeUtil;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.ListCommand;
import seedu.taskmanager.model.task.StartDate;

//@@author A0141102H
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    public Command parse(String args) {

        Set<String> keyWordSet = Collections.emptySet();
        String[] keyWordArray = null;

        /**
         * If the command "LIST" is used without any arguments, return an empty
         * set.
         */
        if (args.trim().isEmpty()) {
            return new ListCommand(keyWordSet);
        }

        if (args.trim().matches(StartDate.STARTDATE_VALIDATION_REGEX1)) {
            keyWordArray = new String[] { args.trim() };
            keyWordSet = new HashSet<>(Arrays.asList(keyWordArray));
            return new ListCommand(keyWordSet);
        }

        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        try {
            keyWordArray = new String[] { DateTimeUtil.getNewDate(args.trim()) };
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        keyWordSet = new HashSet<>(Arrays.asList(keyWordArray));
        return new ListCommand(keyWordSet);
    }

}
