package typetask.logic.commands;

import java.io.IOException;

import typetask.commons.core.Messages;
import typetask.commons.util.FileUtil;
import typetask.logic.commands.exceptions.CommandException;
import typetask.storage.ModifyConfigData;

//@@author A0140010M
public class SettingCommand extends Command {

    public static final String SYMBOL_ASTERISK = "*";
    public static final String SYMBOL_CARET = "^";
    public static final String SYMBOL_HASH = "#";
    public static final String SYMBOL_PLUS = "+";
    public static final String COMMAND_WORD = "setting";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the default saving location of the file \n"
            + "Parameters: FilePath"
            + "Example: " + COMMAND_WORD
            + " c:\\desktop\\ ";
    public static final String MESSAGE_SUCCESS = "Default saving location is updated";

    String path;

    public SettingCommand (String filePath) {
        this.path = FileUtil.getPath(filePath) + "/taskManager.xml";
    }
    @Override
    public CommandResult execute() throws CommandException, IOException {
        if (isInvalidPath()) {
            return new CommandResult(Messages.MESSAGE_INVALID_PATH);
        }
        ModifyConfigData mcd = new ModifyConfigData(config);
        mcd.setTaskManagerFilePath(FileUtil.getPath(path));
        storage.setTaskManagerFilePath(path);
        storage.saveTaskManager(model.getTaskManager());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    //@@author A0144902L
    public boolean isInvalidPath() {
        return (path.contains(SYMBOL_PLUS) || path.contains (SYMBOL_HASH) ||
                path.contains (SYMBOL_CARET) || path.contains (SYMBOL_ASTERISK));
    }

}
