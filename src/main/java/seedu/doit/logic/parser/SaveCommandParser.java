package seedu.doit.logic.parser;

import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.SaveCommand;

//@@author A0138909R
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SaveCommandParser implements CommandParser {
    private static final Logger logger = LogsCenter.getLogger(SaveCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     *
     * @throws IllegalValueException
     *             if not xml file type
     */
    @Override
    public Command parse(String args) {
        assert args != null;
        String filePath = args.trim();
        logger.info("Argument for save is " + filePath);
        return new SaveCommand(filePath);

    }

}
