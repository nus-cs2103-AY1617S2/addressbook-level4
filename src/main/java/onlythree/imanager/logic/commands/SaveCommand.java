//@@author A0148052L

package onlythree.imanager.logic.commands;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import onlythree.imanager.commons.core.EventsCenter;
import onlythree.imanager.commons.events.storage.FileLocationChangedEvent;
import onlythree.imanager.commons.util.FileUtil;
import onlythree.imanager.storage.XmlTaskListStorage;


/**
 *
 * Save the address book data
 *
 */

public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " the path that you wish to save the file to/fileName.xml"
                                               + "For example: " + COMMAND_WORD + "data/File.xml";
    public static final String MESSAGE_SUCCESS = "Task list has been saved!";
    public static final String MESSAGE_INVALID_FILE_PATH = "Do not have the permission to access the file path "
                                                     + "chosen or cannot find file." + "Please change file path.";
    public static final String MESSAGE_INVALID_FILE_NAME = "Did not enter file name "
                                                           + "or did not end file name with '.xml'";

    private String filePath;

    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        if (isFileNameInvalid(filePath)) {
            return new CommandResult(String.format(MESSAGE_INVALID_FILE_NAME, filePath));
        }
        try {
            File file = new File(filePath);
            FileUtil.createIfMissing(file);
            String message = String.format(MESSAGE_SUCCESS, filePath);
            XmlTaskListStorage storage = new XmlTaskListStorage(filePath);
            storage.saveTaskList(model.getTaskList(), filePath);
            EventsCenter.getInstance().post(new FileLocationChangedEvent(filePath, model.getTaskList()));
            return new CommandResult(message);
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                           SaveCommand.MESSAGE_INVALID_FILE_PATH));
        }
    }

    private boolean isFileNameInvalid(String filePath) {
        return (!filePath.endsWith(".xml") || filePath.endsWith("\\.xml"));
    }
}
