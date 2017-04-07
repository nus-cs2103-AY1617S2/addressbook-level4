package project.taskcrusher.logic.commands;

import java.io.File;
import java.io.IOException;

import project.taskcrusher.commons.core.EventsCenter;
import project.taskcrusher.commons.events.BaseEvent;
import project.taskcrusher.commons.events.storage.LoadNewStorageFileEvent;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.logic.commands.exceptions.CommandException;

//@@author A0127737X
/** loads a new xml storage file. If the file does not exist, create a new one and set it as the storage file
 *  This is achieved by posting LoadNewStorageFileEvent which is handled at the high-level MainApp instance.
 */
public class LoadCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": loads the storage file with the name given\n "
            + "if the file does not exist, a new file will be created and set as the new storage file\n"
            + "Parameters: XML_FILE_NAME";

    public static final String MESSAGE_LOAD_SUCCESS = "Loaded file %1$s";
    public static final String MESSAGE_INVALID_FILENAME = "Invalid file name given";
    public static final String MESSAGE_INVALID_EXTENSION = "Only xml files are supported for data storage";
    public static final String XML_EXTENSION = ".xml";

    public final String filenameToLoad;

    public LoadCommand(String filenameToLoad) {
        assert filenameToLoad != null;
        this.filenameToLoad = filenameToLoad.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!filenameToLoad.endsWith(XML_EXTENSION)) {
            throw new CommandException(MESSAGE_INVALID_EXTENSION);
        }

        try {
            FileUtil.createIfMissing(new File(filenameToLoad));
            raise (new LoadNewStorageFileEvent(filenameToLoad));
            return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, filenameToLoad));
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_INVALID_FILENAME);
        }
    }

    private void raise(BaseEvent e) {
        EventsCenter.getInstance().post(e);
    }

}
