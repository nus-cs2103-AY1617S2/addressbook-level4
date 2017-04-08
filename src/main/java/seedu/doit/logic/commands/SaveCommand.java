package seedu.doit.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.storage.TaskManagerSaveChangedEvent;
import seedu.doit.commons.util.FileUtil;
import seedu.doit.logic.commands.exceptions.CommandException;

//@@author A0138909R
/**
 * Adds a task to the task manager.
 */
public class SaveCommand extends Command {

    public static final String XML_FILE_TYPE = ".xml";

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_PARAMETER = "FILE_PATH/FILE_NAME.xml";
    public static final String COMMAND_RESULT = "Saves DoIt¡¯s data at specified location and in specified file";
    public static final String COMMAND_EXAMPLE = "save folder1/savefile.xml\n"
            + "save C:/Users/USER/savefile.xml";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves all tasks to a new file location and name. "
            + "Parameters: FILE_PATH_IN_DOIT_FILE/FILE_NAME.xml\n" + "Example: " + COMMAND_WORD
            + " save/xml/in/this/file/as/name.xml";

    public static final String MESSAGE_SUCCESS = " Tasks saved at %1$s";
    public static final String MESSAGE_DUPLICATE_FILE = "Another file already exists in the file path!";
    public static final String MESSAGE_NOT_XML_FILE = "It must be a .xml file!\n" + MESSAGE_USAGE;
    public static final String MESSAGE_USING_SAME_FILE = " is the current file you are choosing. "
            + "It will be auto saved.";
    public static final String MESSAGE_CANNOT_CREATE_FILE = "Cannot create the .xml file!\n"
            + "Maybe you have the : character in file name.";
    public static final String MESSAGE_INVALID_FILE_NAME = "Invalid file path!\nCannot contain characters"
            + " * ? \" < > |\n" + MESSAGE_USAGE;
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);
    public final String saveFilePath;

    /**
     * Creates an SaveCommand.
     */
    public SaveCommand(String newFilePath) {
        assert newFilePath != null;
        this.saveFilePath = newFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        File file = new File(this.saveFilePath);
        if (this.saveFilePath.equals(this.storage.getTaskManagerFilePath())) {
            logger.info(this.saveFilePath + "is current file path. Do not need to save.");
            throw new CommandException(this.saveFilePath + MESSAGE_USING_SAME_FILE);
        }
        if (!FileUtil.isValidPath(this.saveFilePath)) {
            logger.info("Invalid File Name: " + this.saveFilePath);
            throw new CommandException(MESSAGE_INVALID_FILE_NAME);
        }
        if (!this.saveFilePath.endsWith(XML_FILE_TYPE)) {
            logger.info("File not of type xml: " + this.saveFilePath);
            throw new CommandException(MESSAGE_NOT_XML_FILE);
        }
        if (file.exists()) {
            logger.info("Duplicate file path: " + this.saveFilePath);
            throw new CommandException(MESSAGE_DUPLICATE_FILE);
        }
        try {
            FileUtil.createIfMissing(file);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CANNOT_CREATE_FILE);
        }
        TaskManagerSaveChangedEvent event = new TaskManagerSaveChangedEvent(this.model.getTaskManager(),
                this.saveFilePath);
        logger.info("Created event : " + event.toString());
        EventsCenter.getInstance().post(event);
        this.storage.handleTaskManagerSaveChangedEvent(event);
        logger.info("---------------------------------------HANDLED event : " + event.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.saveFilePath));

    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }

}
