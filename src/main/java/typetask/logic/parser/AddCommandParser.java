package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_TIME;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.NoSuchElementException;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.AddCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    private final int floatingTask = 0;
    private final int deadlineTaskWithTime = 1;
    private final int deadlineTaskWithoutTime = 2;
    private final int deadlineTaskWithTimeNoDate = 3;
    private final int eventTaskWithoutTime = 4;
    private final int eventTaskWithTime = 5;
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE,
                        PREFIX_END_TIME,PREFIX_END_DATE);
        argsTokenizer.tokenize(args);
        try {
            if (checkTaskType(argsTokenizer) == floatingTask) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get()
                );
            } else if (checkTaskType(argsTokenizer) == eventTaskWithTime) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_START_DATE).get(),
                        argsTokenizer.getValue(PREFIX_END_DATE).get(),
                        argsTokenizer.getValue(PREFIX_TIME).get(),
                        argsTokenizer.getValue(PREFIX_END_TIME).get()
                );
            } else if (checkTaskType(argsTokenizer) == eventTaskWithoutTime) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_START_DATE).get(),
                        argsTokenizer.getValue(PREFIX_END_DATE).get(),
                        eventTaskWithoutTime
                );
            } else if (checkTaskType(argsTokenizer) == deadlineTaskWithTime) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_DATE).get(),
                        argsTokenizer.getValue(PREFIX_TIME).get()
                );
            } else if (checkTaskType(argsTokenizer) == deadlineTaskWithTimeNoDate) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_TIME).get(),
                        deadlineTaskWithTimeNoDate
                        );
            } else {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_DATE).get()
                        );
            }

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    public int checkTaskType(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_TIME).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_TIME).isPresent()) {
            return eventTaskWithTime;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTime;
        }  else if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return eventTaskWithoutTime;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
            return deadlineTaskWithoutTime;
        } else if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTimeNoDate;
        } else {
            return floatingTask;
        }
    }
}
