package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.commons.events.storage.FileLocationChangedEvent;
import seedu.address.commons.util.FileUtil;


/**
 *
 * Save the address book data
 *
 */

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "the path that you wish to save the file to/fileName.xml"
                                               + "Example: " + COMMAND_WORD + "data/File.xml";
    public static final String MESSAGE_SUCCESS = "Task list has been saved!";
    public static final String MESSAGE_WRONG_EXTENSION = "Wrong extension. File must be .xml";
    public static final String MESSAGE_INVALID_FILE_PATH = "Do not have the permission to access the file path "
                                                     + "chosen" + "or cannot find file." + "Please change file path.";

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
            try {
                File file = new File(filePath);
                FileUtil.createIfMissing(file);
                message = String.format(MESSAGE_SUCCESS, filePath);
                EventsCenter.getInstance().post(new FileLocationChangedEvent(filePath));
                EventsCenter.getInstance().post(new TaskListChangedEvent(model.getTaskList()));
                return new CommandResult(message);
            } catch (IOException e) {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                           SaveCommand.MESSAGE_INVALID_FILE_PATH));
            }
        }
    }

    /*
     * Check whether the file is .xml extension
     * @para filePath will be checked
     * @return true if it has
     */

    private boolean isXMLExtension(String filepath) {
        if ("".equals(filepath)) {
            return false;
        } else {
            return filePath.endsWith(".xml");
        }
    }


}
