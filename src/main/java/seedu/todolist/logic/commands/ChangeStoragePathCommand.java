package seedu.todolist.logic.commands;

import java.io.IOException;

import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.util.ConfigUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;

//@@author A0139633B
/*
 * Changes the save location of save file
 */
public class ChangeStoragePathCommand extends Command {

    public static final String COMMAND_WORD = "changestorage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes where the to-do list is saved. "
            + "Parameters: PATH_TO_FILE\n"
            + "Example: " + COMMAND_WORD
            + " different_folder";

    public static final String MESSAGE_SUCCESS = "Storage path changed to: %1$s";
    public static final String MESSAGE_FAILURE = "Error saving to path: %1$s";

    private String commandText;
    private final String path;

    //takes in an absolute path
    public ChangeStoragePathCommand(String path) {
    	this.path = path + "/todolist.xml";
    }

	@Override
	public CommandResult execute() throws CommandException {
		assert config != null;
		//config.setToDoListFilePath(this.path);
		try {
			config.setToDoListFilePath(this.path);
			ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
			return new CommandResult(String.format(MESSAGE_SUCCESS, this.path));
		} catch (IOException e) {
			e.printStackTrace(); //is this necessary?
			return new CommandResult(String.format(MESSAGE_FAILURE, this.path));
		}
	}

	@Override
	public boolean isMutating() {
		return true;
	}

	@Override
	public String getCommandText() {
		return commandText;
	}
}