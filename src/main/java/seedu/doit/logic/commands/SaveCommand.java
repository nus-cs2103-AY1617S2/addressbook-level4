package seedu.doit.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.storage.TaskManagerSaveChangedEvent;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.commons.util.FileUtil;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.storage.TaskManagerStorage;

/**
 * Adds a task to the task manager.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all tasks to a new file location and name. "
            + "Parameters: FILE_PATH_IN_DOIT_FILE/FILE_NAME.xml\n" + "Example: " + COMMAND_WORD
            + " save/xml/in/this/file/as/name.xml";

    public static final String MESSAGE_SUCCESS = " Tasks saved at %1$s";
    public static final String MESSAGE_DUPLICATE_FILE = "This file already exists in the file path";
    public static final String MESSAGE_PATH_DOES_NOT_EXIST = "The file path does not exists";

    public final String saveFilePath;
    public TaskManagerStorage taskManagerStorage;
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);

    /**
     * Creates an SaveCommand using raw values.
     *
     * @throws IllegalValueException
     *             if file path does not exists
     */
    public SaveCommand(String newFilePath) throws IllegalValueException {
        assert newFilePath != null;
        this.saveFilePath = newFilePath;
        File file = new File(this.saveFilePath);

        if (!FileUtil.isFileExists(file)) {
            throw new IllegalValueException(MESSAGE_PATH_DOES_NOT_EXIST);
        }

    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        File file = new File(this.saveFilePath);
        logger.info("created file : " + file.toString());
        try {
            FileUtil.createIfMissing(file);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
        logger.info("created file : " + file.toString());
        TaskManagerSaveChangedEvent event = new TaskManagerSaveChangedEvent(this.model.getTaskManager(),
                this.saveFilePath);
        logger.info("created event : " + event.toString());
        EventsCenter.getInstance().post(event);
        this.storage.handleTaskManagerSaveChangedEvent(event);
        logger.info("---------------------------------------HANDLED event : " + event.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.saveFilePath));

    }

}
