package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SaveCommandParser {
    private static final Logger logger = LogsCenter.getLogger(SaveCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     *
     * @throws IllegalValueException
     *             if not xml file type
     */
    public Command parse(String args) {
        assert args != null;
        String filePath = args.trim();
        logger.info("Argument for save is " + filePath);
        try {
            return new SaveCommand(filePath);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

    }

}
