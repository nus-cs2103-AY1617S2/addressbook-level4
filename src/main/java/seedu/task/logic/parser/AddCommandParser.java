package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_COMPLETIONSTATUS;
import static seedu.task.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {

        try {
            ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_COMPLETIONSTATUS, PREFIX_TAG);
            argsTokenizer.tokenize(args);

            String taskName = argsTokenizer.getPreamble().get();
            String startDate = "";
            String endDate = "";
            String completionStatus = "false";
            Set<String> tagSet = new HashSet<String>();
            System.out.println(args);
            if (args.contains("s/")) {
                startDate = argsTokenizer.getValue(PREFIX_STARTDATE).get();
            }
            if (args.contains("e/")) {
                endDate = argsTokenizer.getValue(PREFIX_ENDDATE).get();
            }
            if (args.contains("c/")) {
                completionStatus = argsTokenizer.getValue(PREFIX_COMPLETIONSTATUS).get();
            }
            if (args.contains("#")) {
                tagSet = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            }

            return new AddCommand(
                    taskName,
                    startDate,
                    endDate,
                    completionStatus,
                    tagSet);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
