package seedu.jobs.logic.commands;

import java.io.IOException;

import com.google.common.eventbus.Subscribe;
import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.events.storage.SavePathChangedEventException;

/* Change save path
 */
//@@author A0130979U

public class PathCommand extends Command {
    
    public static final String COMMAND_WORD = "path";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change save path. "
            + "Parameters: path [filename] \n"
            + "Example: " + COMMAND_WORD
            + " taskbook.xml";
    private String path;
    private boolean isValid;
    public static final String MESSAGE_SUCCESS = "Save path has been successfully updated \n";
    public static final String MESSAGE_INVALID_FILE_PATH = "This path is invalid";
    
    public PathCommand(String path) {
        this.path = path;
        this.isValid = true;
        EventsCenter.getInstance().registerHandler(this);
    }
    
    @Override
    public CommandResult execute() throws IOException {
        assert model != null;
        
        model.changePath(path);
        if(!isValid){
            throw new IOException(MESSAGE_INVALID_FILE_PATH);   
        } 
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    
    @Subscribe
    public void handleSavePathChangedEventException(SavePathChangedEventException event){
        isValid = false;
    }
    
}
//@@author
