package seedu.task.logic.commands;

import java.io.File;

import seedu.task.commons.util.FileUtil;

//@@author A0142939W
/**
 * Saves task manager in a different directory.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD_1 = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Saves the task manager in a different directory. "
            + "Parameters: PATHNAME \n"
            + "Example: " + COMMAND_WORD_1
            + " ";

    public static final String MESSAGE_SUCCESS = "File save at: %1$s";
    public static final String MESSAGE_FAILURE_DIRECTORY = "The path %1$s leads to a directory";

    private String pathName;

    /**
     * Creates a Save command
     */
    public SaveCommand(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public CommandResult execute() {
        File file = new File(pathName);

        if (FileUtil.isFileDirectory(file)) {
            return new CommandResult(String.format(MESSAGE_FAILURE_DIRECTORY, pathName));
        }

        model.changeFilePath(pathName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pathName));
    }

}
