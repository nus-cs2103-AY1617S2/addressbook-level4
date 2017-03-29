package typetask.logic.commands;

import java.io.File;
import java.io.IOException;

import typetask.commons.exceptions.DataConversionException;
import typetask.commons.util.FileUtil;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.ReadOnlyTaskManager;
import typetask.storage.XmlFileStorage;

//@@author A0140010M
public class UseCommand extends Command {
    public static final String COMMAND_WORD = "use";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Uses the taskManager from another location\n"
            + "Parameters: FilePath\n"
            + "Example: " + COMMAND_WORD
            + " c:\\desktop\\ ";
    public static final String MESSAGE_SUCCESS = "Task Manager is updated";

    String path;

    public UseCommand (String filePath) {
        this.path = FileUtil.getPath(filePath) + "/taskManager.xml";
    }
    @Override
    public CommandResult execute() throws CommandException, IOException, DataConversionException {
        File file = new File(this.path);
        ReadOnlyTaskManager taskManager = XmlFileStorage.loadDataFromSaveFile(file);
        model.resetData(taskManager);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
