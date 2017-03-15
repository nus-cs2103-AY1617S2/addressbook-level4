package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_DELIMITER;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;

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
            if (ParserUtil.hasDate(args) || ParserUtil.hasTime(args)) {
                return parseTask(args);
            } else {
                return parseFloatingTask(args);
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private static Command parseFloatingTask(String args) throws NoSuchElementException, IllegalValueException {
        String[] taskFields = args.split(PARAMETER_DELIMITER);
        String name = taskFields[0];
        Set<String> tagSet = new HashSet<String>();
        for (int i = 1; i < taskFields.length; i++) {
            tagSet.add(taskFields[i]);
        }
        return new AddCommand(name, tagSet);
    }

    private static Command parseTask(String args) throws NoSuchElementException, IllegalValueException {
        String[] taskFields = args.split(PARAMETER_DELIMITER);
        if (taskFields.length < 2) {
            throw new NoSuchElementException();
        } else {
            String name = taskFields[0];
            String startDate = ParserUtil.parseDate(taskFields[1]);
            String startTime = ParserUtil.parseTime(taskFields[1]);
            Optional<String> endDate = Optional.empty();
            Optional<String> endTime = Optional.empty();
            Set<String> tagSet = new HashSet<String>();
            if (taskFields.length > 2) {
                if (ParserUtil.isValidDate(taskFields[2])) {
                    endDate = Optional.of(ParserUtil.parseDate(taskFields[2]));
                }
                if (ParserUtil.isValidTime(taskFields[2])) {
                    endTime = Optional.of(ParserUtil.parseTime(taskFields[2]));
                }
                int tagIndexStart = 2;
                if (endDate.isPresent() || endTime.isPresent()) {
                    tagIndexStart = 3;
                }
                for (int i = tagIndexStart; i < taskFields.length; i++) {
                    tagSet.add(taskFields[i]);
                }
            }
            return new AddCommand(name, startDate, startTime, endDate, endTime, tagSet);
        }
    }

}
