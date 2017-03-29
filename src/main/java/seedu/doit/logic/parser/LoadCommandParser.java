//@@author A0138909R
package seedu.doit.logic.parser;

import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.LoadCommand;

public class LoadCommandParser implements CommandParser {
    private static final Logger logger = LogsCenter.getLogger(LoadCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * LoadCommand and returns an LoadCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        assert args != null;
        String filePath = args.trim();
        logger.info("Argument for load is " + filePath);
        return new LoadCommand(filePath);

    }

}
