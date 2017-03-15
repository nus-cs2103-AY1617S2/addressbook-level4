package seedu.tasklist.logic.commands;

import java.io.IOException; 

import seedu.tasklist.logic.commands.exceptions.CommandException;

/**
 * Load user data file for task list.
 */
public class LoadCommand extends Command {
    
    public static final String COMMAND_WORD = "load";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": loads data from specified location.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/mytasklist.xml";

    public static final String MESSAGE_SUCCESS = "Data successfully loaded from: %1$s";
    public static final String MESSAGE_FAILURE = "The file cannot be loaded: %1$s";
 
    private String filePath;
    
    public LoadCommand(String filePath){
        this.filePath = filePath.trim();
    }
    
    @Override
    public CommandResult execute() throws CommandException {
       assert filePath != null;
       try{
           model.loadTaskList(filePath);
           return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
       }catch (IOException e){
           return new CommandResult(String.format(MESSAGE_FAILURE, filePath));
       }
    }


}
