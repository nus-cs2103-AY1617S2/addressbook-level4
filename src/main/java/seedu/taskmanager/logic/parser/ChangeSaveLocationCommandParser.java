package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.taskmanager.logic.commands.ChangeSaveLocationCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

//@@author A0142418L
public class ChangeSaveLocationCommandParser {

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ChangeSaveLocationCommand and returns an ChangeSaveLocationCommand object
     * for execution.
     */

    public Command parse(String args) {

        // assert args != null;

        String stringSaveLocation = args.trim();
        File checkLocation = new File(args.trim());

        if (("".equals(args))) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSaveLocationCommand.MESSAGE_USAGE));
        }
        if (!checkLocation.exists()) {
            return new IncorrectCommand(INVALID_SAVE_LOCATION + "\n" + ChangeSaveLocationCommand.MESSAGE_USAGE);
        }

        if (!(stringSaveLocation.substring(stringSaveLocation.length() - 1).equals("/"))) {
            stringSaveLocation += "/";
        }

        stringSaveLocation += "taskmanager.xml";

        File saveLocation = new File(stringSaveLocation.trim());

        return new ChangeSaveLocationCommand(saveLocation);
    }

}
