package seedu.taskboss.logic.commands;

import java.io.File;
import java.io.IOException;

/**
 * Saves the data at specific filepath. Creates filepath if it does not exist
 */

public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                + "Saves the data at specific filepath.\n"
                + "Example: " + COMMAND_WORD
                + " C://user/desktop/taskboss";

    public static final String MESSAGE_SUCCESS = "The data has been saved!";

    public static final String MESSAGE_INVALID_FILEPATH = "The filepath is invalid.";

    private final String filepath;

    public SaveCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
        assert storage != null;
        storage.setFilePath(this.filepath);
        
        File file = new File(this.filepath);
        
        if (!file.exists() && !file.isDirectory()) {
            FileUtil.createIfMissing(file);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
