package seedu.today.logic.parser;

import static seedu.today.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.List;

import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.logic.commands.AddCommand;
import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.IncorrectCommand;

//@@author A0144422R
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends SeperableParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String arguments) {

        this.args = arguments;

        // find and remove tags
        String[] tags = getTags();

        // find and remove starting time and deadline if the syntax is "<name>
        // from <starting time> to <deadline>"
        List<Date> startingTimeAndDeadline = getStartingTimeAndDeadline();
        if (startingTimeAndDeadline != null) {
            try {
                return new AddCommand(args.trim(), startingTimeAndDeadline.get(CliSyntax.DEADLINE_INDEX),
                        startingTimeAndDeadline.get(CliSyntax.STARTING_INDEX), tags);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        }

        // find and remove starting time and deadline if the syntax is "<name>
        // due <deadline>"
        Date deadline = getDeadline();
        if (deadline != null) {
            try {
                return new AddCommand(args.trim(), deadline, tags);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        }
        try {
            return new AddCommand(args.trim(), tags);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

}
