package typetask.logic.commands;

import java.io.File;
import java.io.IOException;

import typetask.commons.core.Messages;
import typetask.commons.util.FileUtil;
import typetask.logic.commands.exceptions.CommandException;
import typetask.storage.XmlFileStorage;
import typetask.storage.XmlSerializableTaskManager;

//@@author A0140010M
public class SaveCommand extends Command {

    public static final String SYMBOL_ASTERISK = "*";
    public static final String SYMBOL_CARET = "^";
    public static final String SYMBOL_HASH = "#";
    public static final String SYMBOL_PLUS = "+";
    public static final String COMMAND_WORD = "save";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ": Saves/Creates a copy of the file to a specified location\n"
            + "Parameters: FilePath\n"
            + "Example: " + COMMAND_WORD
            + " c:\\desktop\\ ";
    public static final String MESSAGE_SUCCESS = "A copy of the file is saved.";

    private String path;

    public SaveCommand (String filePath) {
        this.path = FileUtil.getPath(filePath) + "/taskManager.xml";
    }
    @Override
    public CommandResult execute() throws CommandException, IOException {
        if (isInvalidPath()) {
            return new CommandResult(Messages.MESSAGE_INVALID_PATH);
        }
        File file = new File(this.path);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(model.getTaskManager()));

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    //@@author A0144902L
    public boolean isInvalidPath() {
        return (path.contains(SYMBOL_PLUS) || path.contains (SYMBOL_HASH) ||
                path.contains (SYMBOL_CARET) || path.contains (SYMBOL_ASTERISK));
    }
}
