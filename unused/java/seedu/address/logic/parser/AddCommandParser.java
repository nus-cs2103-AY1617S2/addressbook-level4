package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.task.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    //@@author A0164032U
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_GROUP);
        argsTokenizer.tokenize(args);
        try {
            if (!argsTokenizer.getEmpty(PREFIX_STARTDATE) && !argsTokenizer.getEmpty(PREFIX_ENDDATE)) {

                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_STARTDATE).get(),
                        argsTokenizer.getValue(PREFIX_ENDDATE).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );

            } else if (!argsTokenizer.getEmpty(PREFIX_ENDDATE)) {

                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_ENDDATE).get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );

            } else if (!argsTokenizer.getEmpty(PREFIX_STARTDATE)) {

                throw new NoSuchElementException("");

            } else {

                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_GROUP).get()
                        );
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
