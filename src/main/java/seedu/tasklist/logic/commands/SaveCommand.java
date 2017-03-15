package seedu.tasklist.logic.commands;

import java.io.IOException;

public class SaveCommand extends Command {
    
    public static final String COMMAND_WORD = "save";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the directory and file name of the data that is being saved.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/mytasklist.xml";
    
    public static final String MESSAGE_SUCCESS = "File is successfully saved to: %1$s";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path: %1$s";
    
 private final String filePath;
    
    public SaveCommand(String filePath){
        this.filePath = filePath;
    }
    
    @Override
    public CommandResult execute() {
        try{
            model.saveTaskList(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        }catch (IOException e){
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, filePath));
        }
        
    }

}
