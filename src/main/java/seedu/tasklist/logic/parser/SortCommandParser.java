package seedu.tasklist.logic.parser;

import seedu.tasklist.logic.commands.Command;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tasklist.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.SortCommand;
import seedu.tasklist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {
    
        /**
         * Parses the given {@code String} of arguments in the context of the SortCommand
         * and returns an SortCommand object for execution.
         */
        public Command parse(String args) {
            
            
       
    }

}
