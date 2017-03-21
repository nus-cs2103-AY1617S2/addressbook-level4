package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.taskmanager.logic.commands.ChangeSaveLocationCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

public class ChangeSaveLocationCommandParser {

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ChangeSaveLocationCommand and returns an ChangeSaveLocationCommand object
     * for execution.
     */

    public Command parse(String args) {

        // assert args != null;

        String stringSaveLocation = args;
        File checkLocation = new File(args.trim());

        if (("".equals(stringSaveLocation))) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSaveLocationCommand.MESSAGE_USAGE));
        }
        if (!checkLocation.exists()) {
            return new IncorrectCommand(String.format(ChangeSaveLocationCommand.MESSAGE_USAGE, INVALID_SAVE_LOCATION));
        }

        if (!(stringSaveLocation.substring(stringSaveLocation.length() - 1).equals("/"))) {
            stringSaveLocation += "/";
        }

        stringSaveLocation += "taskmanager.xml";
        File saveLocation = new File(stringSaveLocation.trim());

        return new ChangeSaveLocationCommand(saveLocation);
    }

}
