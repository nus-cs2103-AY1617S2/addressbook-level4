//@@Liu Yulin A0148052L

package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FileLocationChangedEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.storage.XmlTaskListStorage;


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

    private String filePath;

    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
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


}
