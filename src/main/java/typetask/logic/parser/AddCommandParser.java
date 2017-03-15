package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
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
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME);
        argsTokenizer.tokenize(args);
        try {
            if (checkTaskType(argsTokenizer) == floatingTask) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get()
                );
            } else if (checkTaskType(argsTokenizer) == deadlineTaskWithTime) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        argsTokenizer.getValue(PREFIX_DATE).get(),
                        argsTokenizer.getValue(PREFIX_TIME).get()
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
        if (argsTokenizer.getValue(PREFIX_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTime;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
            return deadlineTaskWithoutTime;
        } else {
            return floatingTask;
        }
    }
}
