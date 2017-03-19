//@@author A0163720M
package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;

public class SaveFileCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(SaveFileCommand.class);
    private String saveFilePath;
    public static final String MESSAGE_ERROR = "Cannot write to a file that does not exist.";
    public static final String MESSAGE_SUCCESS = "Save file updated!";
    public static final String COMMAND_WORD = "savefile";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the save file location.\n"
            + "Parameters: PATH (must be a valid path string pointing to existing .xml file)\n"
            + "Example: " + COMMAND_WORD + " data/newtodolist.xml";

    public SaveFileCommand (String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        File f = new File(saveFilePath);

        if (!f.exists() || f.isDirectory()) {
            throw new CommandException(MESSAGE_ERROR);
        }

        Config config = new Config();
        config.setTodoListFilePath(saveFilePath);

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(config, saveFilePath);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
