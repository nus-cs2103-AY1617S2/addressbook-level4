package typetask.logic.commands;

import java.io.IOException;

import typetask.commons.util.FileUtil;
import typetask.logic.commands.exceptions.CommandException;
import typetask.storage.ModifyConfigData;

public class SettingCommand extends Command {

    public static final String COMMAND_WORD = "setting";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the default saving location of the file \n"
            + "Parameters: FilePath"
            + "Example: " + COMMAND_WORD
            + " c:\\desktop\\ ";
    public static final String MESSAGE_SUCCESS = "Defaut saving location is updated";

    String path;

    public SettingCommand (String filePath) {
        this.path = FileUtil.getPath(filePath) + "/taskManager.xml";
    }
    @Override
    public CommandResult execute() throws CommandException, IOException {
        ModifyConfigData mcd = new ModifyConfigData(config);
        mcd.setTaskManagerFilePath(FileUtil.getPath(path));
        storage.setTaskManagerFilePath(path);
        storage.saveTaskManager(model.getTaskManager());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }


}
