package org.teamstbf.yats.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.Config;
import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.exceptions.DataConversionException;
import org.teamstbf.yats.commons.util.ConfigUtil;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.storage.XmlTaskManagerStorage;

// @@author A0139448U
/*
 * Changes the location files are saved to
 */
public class ChangeSaveLocationCommand extends Command {

	public final static String COMMAND_WORD = "save";
	public final static String MESSAGE_WRONG_INPUT = "Illegal input.";
	public final static String MESSAGE_DUPLICATE_FILE_PATH = "Given file directory is already data save location.";
	public final static String MESSAGE_CHANGE_SUCCESS = "Save location changed to: ";
	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Changes the file directory on disk that task manager data is saved to "
			+ "Existing save location will be overwritten by the new save location file directory.\n"
			+ "Parameters: New File Directory" + "Example: " + COMMAND_WORD + " /Users/dionngg/Desktop/others";

	private static final Logger logger = LogsCenter.getLogger(ChangeSaveLocationCommand.class);

	private File saveLocation;

	public ChangeSaveLocationCommand(File location) {
		this.saveLocation = location;
	}

	@Override
	public CommandResult execute() throws CommandException {
		Config newConfig;
		try {
			Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
			newConfig = configOptional.orElse(new Config());
			newConfig.setTaskManagerFilePath(saveLocation.toString());
			ConfigUtil.saveConfig(newConfig, Config.DEFAULT_CONFIG_FILE);
			XmlTaskManagerStorage.setTaskManagerFilePath(saveLocation.toString());
			model.saveTaskManager();
		} catch (Config.DuplicateFileException dupExcep) {
			throw new CommandException(MESSAGE_DUPLICATE_FILE_PATH);
		} catch (IOException ioe) {
			throw new CommandException(MESSAGE_WRONG_INPUT);
		} catch (DataConversionException e) {
			logger.warning("Config file at " + Config.DEFAULT_CONFIG_FILE + " is not in the correct format. "
					+ "Using default config properties");
		}
		return new CommandResult(MESSAGE_CHANGE_SUCCESS + saveLocation.toString());
	}

}
