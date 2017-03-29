package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_DELIMITER;

import java.util.HashSet;
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
        String[] taskFields = args.split(PARAMETER_DELIMITER);
        Set<String> tagSet = new HashSet<String>();
        if (taskFields.length == 0) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } else {
            String name = taskFields[0];
            Optional<String> startDateTime = Optional.empty();
            Optional<String> endDateTime = Optional.empty();
            for (int i = 1; i < taskFields.length; i++) {
                String currentChunk = taskFields[i];
                if (ParserUtil.hasDate(currentChunk) || ParserUtil.hasTime(currentChunk)) {
                    if (!endDateTime.isPresent()) {
                        endDateTime = Optional.of(taskFields[i]);
                    } else if (!startDateTime.isPresent()) {
                        startDateTime = endDateTime;
                        endDateTime = Optional.of(taskFields[i]);
                    } else {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                AddCommand.MESSAGE_USAGE));
                    }
                } else {
                    tagSet.add(taskFields[i]);
                }
            }
            try {
                return new AddCommand(name, startDateTime, endDateTime, tagSet);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                return new IncorrectCommand(e.getMessage());
            }
        }
    }

}
