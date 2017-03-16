package seedu.address.logic.commands;


import java.io.File;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.storage.FileLocationChangedEvent;


/**
 *
 * Save the address book data
 *
 */

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "Save";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "the path that you wish to save the file to/fileName.xml"
                                               + "Example: " + COMMAND_WORD + "data/File.xml";
    public static final String MESSAGE_SUCCESS = "Task list has been saved!";
    public static final String MESSAGE_FILE_EXISTS = "File already exists";
    public static final String MESSAGE_WRONG_EXTENSION = "Wrong extension. File must be .xml";
    public static final String MESSAGE_NO_PERMISSION = "Do not have the permission to access the file path "
                                                     + "chosen.";

    private String filePath;

    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        String message;
        if (!isXMLExtension(filePath)) {
            return new CommandResult(MESSAGE_WRONG_EXTENSION);
        } else {
            if (doesFileExists(filePath)) {
                return new CommandResult(MESSAGE_FILE_EXISTS);
            } else {
                if (canAssessFile(filePath)) {
                    message = String.format(MESSAGE_SUCCESS, filePath);
                } else return new CommandResult(MESSAGE_NO_PERMISSION);
            }
        }

        EventsCenter.getInstance().post(new FileLocationChangedEvent(filePath));
        EventsCenter.getInstance().post(new TaskListChangedEvent(model.getTaskList()));
        return new CommandResult(message);
    }

    /*
     * Check whether the file is .xml extension
     * @para filePath will be checked
     * @return true if it has
     */

    private boolean isXMLExtension(String filepath) {
        if (filepath.equals("")) {
            return false;
        } else {
            return filePath.endsWith(".xml");
        }
    }

    /*
     * Check whether the file exists already
     * @para filePath will be checked
     * @return true if file path given already exists
     */

    private boolean doesFileExists(String filePath) {
        File newfile = new File(filePath);
        return newfile.exists();
    }

    /*
     * Check whether can write to file or not
     * @para filePath will be checked
     * @return true if file path give can be assessed
     */

    private boolean canAssessFile(String filePath) {
        try {
            File file = new File(filePath).getParentFile();
            return file.canWrite();
        } catch (Exception e) {
            return false;
        }
    }
}
