package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer datesTokenizer =
                new ArgumentTokenizer(PREFIX_BY, PREFIX_FROM, PREFIX_TO);
        datesTokenizer.tokenize(args);
        
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_BY, PREFIX_FROM, PREFIX_TO, PREFIX_TAG);
        
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_BY).isPresent(), argsTokenizer.getValue(PREFIX_BY),
                    argsTokenizer.getValue(PREFIX_FROM).isPresent(), argsTokenizer.getValue(PREFIX_FROM),
                    argsTokenizer.getValue(PREFIX_TO).isPresent(), argsTokenizer.getValue(PREFIX_TO),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    
    
    if(isFloatingTask(hasDeadline, hasStartDate, hasEndDate)) {
        this.toAdd = new Task(new Description(description), new UniqueTagList(tagSet));
    } else
    if(isDeadlineTask(hasDeadline, hasStartDate, hasEndDate)) {
        this.toAdd = new Task(new Description(description), new DateTime(deadline.get()), new UniqueTagList(tagSet));
    } else
    if(isEventTask(hasDeadline, hasStartDate, hasEndDate)) {
        this.toAdd = new Task(new Description(description), new DateTime(startDate.get()),
                              new DateTime(endDate.get()), new UniqueTagList(tagSet));
    } else {
    throw new IllegalValueException("Too many/few DATETIME arguments!");
    }
}

/**
 * Checks the type of task(floating, deadline or event) to be added
 * based on the DATETIME parameters entered by the user.
 * @throws IllegalValueException if both deadline and startDate are entered,
 * or if only one of startDate or endDate is entered
 */

private boolean isFloatingTask(boolean hasDeadline, boolean hasStartDate, boolean hasEndDate){
    return !hasDeadline && !hasStartDate && !hasEndDate;
}
private boolean isDeadlineTask(boolean hasDeadline, boolean hasStartDate, boolean hasEndDate){
    return hasDeadline && !hasStartDate && !hasEndDate;
}
private boolean isEventTask(boolean hasDeadline, boolean hasStartDate, boolean hasEndDate){
    return !hasDeadline && hasStartDate && hasEndDate;
}

}
