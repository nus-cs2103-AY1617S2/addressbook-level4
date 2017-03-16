package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.logic.commands.exceptions.CommandException;

/**
 * Change the directory of taskmanager.xml file to user-specified path to allow cloud service sync. 
 * Path matching is case sensitive.
 */
public class ChangeDirCommand extends Command {
    public static final String COMMAND_WORD = "changedir";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the directory of the taskmanager."
            + "xml file to allow user to sync with cloud services"
            + "Parameters: PATH...\n"
            + "Example: " + COMMAND_WORD + " /User/admin/Documents/taskmanager.xml";
    
    public static final String MESSAGE_SUCCESS = "TaskManager directory changed to ";
    public static final String MESSAGE_ERROR_LOAD = "Cannot load from current preferences.json";
    public static final String MESSAGE_ERROR_SAVE = "Failed to save config file : ";
    
    private final String newPath;
    
    public ChangeDirCommand(String path) {
        this.newPath = path;
    }
    
    @Override
    public CommandResult execute() throws CommandException {
        Config newConfig;
        String configFilePathUsed;
        
        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            newConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR_LOAD);
        }
        
        newConfig.setTaskManagerFilePath(this.newPath);
        
        try {
            ConfigUtil.saveConfig(newConfig, configFilePathUsed);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_SAVE + StringUtil.getDetails(e));
        }
        
        return new CommandResult(MESSAGE_SUCCESS + this.newPath);
    }
}
