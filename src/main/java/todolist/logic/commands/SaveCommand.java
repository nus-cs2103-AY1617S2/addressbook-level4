package todolist.logic.commands;

//@@ A0143648Y

import java.io.IOException;
import java.util.logging.Logger;

import todolist.commons.core.Config;
import todolist.commons.core.LogsCenter;
import todolist.model.ReadOnlyToDoList;
import todolist.storage.Storage;
import todolist.storage.StorageManager;

/** Save command that saves current data file to a new filepath.
 * 
 * */

public class SaveCommand extends Command {
    
    private Logger logger = LogsCenter.getLogger(SaveCommand.class.getName());
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
            + "Saves data file to new location specified. "
            + "New folders with the file can be auto-created as long as given directory is valid.\n"
            + "Main Directory will the dafault save location for any valid but unspecifed file path\n"
            + "Example: " + COMMAND_WORD + " C: /Users/Computin/Desktop/CS2103" + "Take note: No spacing after :\n"
            + "Parameters: FILEPATH (must be valid)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Computing/Desktop/CS2103";
    
    private static final String MESSAGE_SUCCESS = "Data successfully saved to new location.";
    private static final String MESSAGE_INVALID_PATH = "Filepath given is invalid. Filepath will be reset to old path." + "\n\n" + MESSAGE_USAGE;
    
 //   private static Config config;
    private String newStorageFilePath, oldStorageFilePath;
    private ReadOnlyToDoList toDoList;
    private static Storage storage;
    
    public SaveCommand(String newStorageFilePath) {
        this.oldStorageFilePath = Config.getToDoListFilePath();
        logger.info("Old file path: " + oldStorageFilePath);
        
        this.newStorageFilePath = newStorageFilePath.trim().replace("\\", "/") + "/todolist.xml";
        logger.info("New file path: " + this.newStorageFilePath);
        
        setStorage(new StorageManager(Config.getToDoListFilePath(), Config.getUserPrefsFilePath()));
    }
    
    public static void setStorage(Storage s) {
        storage = s;
    }

    @Override
    public CommandResult execute() {
        
        toDoList = model.getToDoList();
        
        Config.setToDoListFilePath(newStorageFilePath);
        try {
            storage.saveToDoList(toDoList, newStorageFilePath);
        } catch (IOException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    private void handleInvalidFilePathException() { 
        logger.info("Error writing to filepath. Handling data save exception.");
        
        Config.setToDoListFilePath(oldStorageFilePath);  //set back to old filepath
        
        try {
            storage.saveToDoList(toDoList, newStorageFilePath);
        } catch (IOException e) {
            logger.severe("Error saving task manager");
        }

    }

}