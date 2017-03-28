package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.logic.parser.CliSyntax.PREFIX_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_END_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_START_DATE;
import static typetask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import typetask.commons.exceptions.IllegalValueException;
import typetask.logic.commands.AddCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;
//@@author A0139926R
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    private final int floatingTask = 0;
    private final int deadlineTaskWithTime = 1;
    private final int deadlineTaskWithDate = 2;
    private final int eventTask = 3;
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_START_DATE, PREFIX_END_DATE);
        argsTokenizer.tokenize(args);
        try {
            int taskType = checkTaskType(argsTokenizer);
            if (taskType == eventTask) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        getDate(argsTokenizer.getValue(PREFIX_START_DATE).get()),
                        getDate(argsTokenizer.getValue(PREFIX_END_DATE).get())
                );
            } else if (taskType == deadlineTaskWithDate) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        getDate(argsTokenizer.getValue(PREFIX_DATE).get())
                );
            } else if (taskType == deadlineTaskWithTime) {
                return new AddCommand(
                        argsTokenizer.getPreamble().get(),
                        getDate(argsTokenizer.getValue(PREFIX_TIME).get())
                        );
            } else {
                return new AddCommand(
                        argsTokenizer.getPreamble().get()
                        );
            }

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
  //@@author A0139926R
    public int checkTaskType(ArgumentTokenizer argsTokenizer) {
        if (argsTokenizer.getValue(PREFIX_START_DATE).isPresent() &&
                argsTokenizer.getValue(PREFIX_END_DATE).isPresent()) {
            return eventTask;
        } else if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
            return deadlineTaskWithDate;
        } else if (argsTokenizer.getValue(PREFIX_TIME).isPresent()) {
            return deadlineTaskWithTime;
        } else {
            return floatingTask;
        }
    }
  //@@author A0139926R
    public String getDate(String date) {
        List<Date> dates = DateParser.parse(date);
        String nattyDate = dates.get(0).toString();
        String[] splitDate = nattyDate.split(" ");
        String finalizedDate = splitDate[0] + " " + splitDate[1] + " " + splitDate[2] +
                " " + splitDate[3];
        return finalizedDate;
    }
}
