package seedu.doit.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.storage.TaskManagerLoadChangedEvent;
import seedu.doit.commons.exceptions.DataConversionException;
import seedu.doit.commons.util.FileUtil;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.util.SampleDataUtil;

public class LoadCommand extends Command {

    private static final String LOGGER_LOADING_FROM = "IN LOADCOMMAND loading from : ";
    private static final String LOGGER_HANDLED_EVENT = "HANDLED event : ";
    private static final String LOGGER_CREATED_EVENT = "Created event : ";
    private static final String LOGGER_INVALID_FILE_NAME = "Invalid File Name: ";
    public static final String COMMAND_WORD = "load";
    public static final String COMMAND_PARAMETER = "FILE_PATH/FILE_NAME.xml";
    public static final String COMMAND_RESULT = "Loads an existing DoIt's data at specified "
            + "location and in specified file";
    public static final String COMMAND_EXAMPLE = "load folder1/savefile.xml\n" + "load C:/Users/USER/savefile.xml";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads all tasks in the specified file location. "
            + "Parameters: FILE_PATH_IN_DOIT_FILE/FILE_NAME.xml\n" + "Example: " + COMMAND_WORD
            + " load/xml/in/this/file/as/name.xml";

    public static final String MESSAGE_SUCCESS = " Tasks loaded at %1$s";

    public static final String MESSAGE_INVALID_FILE_NAME = "Invalid file path!\n" + MESSAGE_USAGE;
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);
    public final String loadFilePath;

    /**
     * Creates an LoadCommand.
     */
    public LoadCommand(String newFilePath) {
        assert newFilePath != null;
        this.loadFilePath = newFilePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        File file = new File(this.loadFilePath);
        Optional<ReadOnlyTaskManager> newData;
        try {
            newData = this.storage.readTaskManager(this.loadFilePath);
            logger.info(LOGGER_LOADING_FROM + this.loadFilePath);
        } catch (DataConversionException dce) {
            dce.printStackTrace();
            throw new CommandException(MESSAGE_INVALID_FILE_NAME);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new CommandException(MESSAGE_INVALID_FILE_NAME);
        }
        if (!FileUtil.isFileExists(file)) {
            logger.info(LOGGER_INVALID_FILE_NAME + this.loadFilePath);
            throw new CommandException(MESSAGE_INVALID_FILE_NAME);
        }
        TaskManagerLoadChangedEvent event = new TaskManagerLoadChangedEvent(newData, this.loadFilePath);
        this.model.loadData(newData.orElseGet(SampleDataUtil::getSampleTaskManager));
        logger.info(LOGGER_CREATED_EVENT + event.toString());
        EventsCenter.getInstance().post(event);
        logger.info(LOGGER_HANDLED_EVENT + event.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.loadFilePath));

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
