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

        File checkLocation = new File(args.trim());

        if (args.equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSaveLocationCommand.MESSAGE_USAGE));
        }
        if (!checkLocation.exists()) {
            return new IncorrectCommand(String.format(ChangeSaveLocationCommand.MESSAGE_USAGE, INVALID_SAVE_LOCATION));
        }

        if (!(args.substring(args.length()-1).equals("/"))){
            args += "/";
        }

        String stringLocation = new String(args + "taskmanager.xml");
        File saveLocation = new File(stringLocation.trim());

        return new ChangeSaveLocationCommand(saveLocation);
    }

}
