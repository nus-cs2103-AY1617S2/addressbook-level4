//@@author A0139925U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.INDEX_DELIMITER;

import java.util.ArrayList;
import java.util.List;

import seedu.tache.commons.util.StringUtil;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.CompleteCommand;
import seedu.tache.logic.commands.IncorrectCommand;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class CompleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        String[] preambleFields = args.trim().split(INDEX_DELIMITER);
        if (preambleFields.length == 0 || args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        List<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < preambleFields.length; i++) {
          //Checking all arguments are valid and creating list
            String currentIndex = preambleFields[i].trim();
            if (!StringUtil.isUnsignedInteger(currentIndex)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                CompleteCommand.MESSAGE_USAGE));
            } else {
                indexList.add(Integer.parseInt(currentIndex));
            }
        }

        return new CompleteCommand(indexList);
    }

}
