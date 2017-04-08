package project.taskcrusher.logic.commands;

import java.io.File;
import java.io.IOException;

import project.taskcrusher.commons.core.EventsCenter;
import project.taskcrusher.commons.events.BaseEvent;
import project.taskcrusher.commons.events.storage.LoadNewStorageFileEvent;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.logic.commands.exceptions.CommandException;

//@@author A0127737X
/** loads an xml storage file. The user may specify if he/she wants to create a missing file,
 *  by providing the word "new" before the filename. or load an existing xml file. The loading of new file
 *  is achieved by posting LoadNewStorageFileEvent which is handled at the high-level MainApp instance.
 */
public class LoadCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": loads an xml storage file with the name given\n"
            + "To create and load a new file, type in new before file name. Otherwise, it loads an existing file\n"
            + "Parameters: [new] XML_FILE_NAME";

    public static final String MESSAGE_LOAD_SUCCESS = "Loaded file %1$s";
    public static final String MESSAGE_INVALID_FILENAME = "Invalid file name given";
    public static final String MESSAGE_FILE_ALREADY_EXISTS = "The file %1$s already exists";
    public static final String MESSAGE_FILE_NONEXISTENT = "The file %1$s does not exist";
    public static final String MESSAGE_INVALID_EXTENSION = "Only xml files are supported for data storage";
    public static final String XML_EXTENSION = ".xml";
    public static final boolean IS_CREATE_NEW_FILE = true;

    public final String filenameToLoad;
    public final boolean isCreateNewFile;

    public LoadCommand(String filenameToLoad, boolean isCreateNewFile) {
        assert filenameToLoad != null;
        this.filenameToLoad = filenameToLoad;
        this.isCreateNewFile = isCreateNewFile;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (filenameToLoad.isEmpty()) {
            throw new CommandException(MESSAGE_USAGE);
        }
        if (!filenameToLoad.endsWith(XML_EXTENSION)) {
            throw new CommandException(MESSAGE_INVALID_EXTENSION);
        }

        if (isCreateNewFile) {
            if (FileUtil.isFileExists(new File(filenameToLoad))) {
                throw new CommandException(String.format(MESSAGE_FILE_ALREADY_EXISTS, filenameToLoad));
            } else {
                try {
                    FileUtil.createIfMissing(new File(filenameToLoad));
                } catch (IOException e) {
                    throw new CommandException(MESSAGE_INVALID_FILENAME);
                }
            }
        } else {
            if (!FileUtil.isFileExists(new File(filenameToLoad))) {
                throw new CommandException(String.format(MESSAGE_FILE_NONEXISTENT, filenameToLoad));
            }
        }

        raise (new LoadNewStorageFileEvent(filenameToLoad));
        return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, filenameToLoad));
    }

    private void raise(BaseEvent e) {
        EventsCenter.getInstance().post(e);
    }
}
