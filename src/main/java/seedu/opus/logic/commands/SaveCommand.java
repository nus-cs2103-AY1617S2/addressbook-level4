package seedu.opus.logic.commands;

import seedu.opus.commons.core.Config;
import seedu.opus.commons.util.FileUtil;
import seedu.opus.commons.util.StringUtil;

//@@author A0148081H
/** Allow the user to specify a folder as the data storage location **/
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_FORMAT = "save LOCATION";
    public static final String COMMAND_DESCRIPTION = "saves task data at specified location";
    public static final String COMMAND_EXAMPLE = "save data/opus.xml";

    public static final String MESSAGE_SUCCESS = "New save location: %1$s";
    public static final String MESSAGE_LOCATION_DEFAULT = "Save location set to default: %1$s";

    public static final String MESSAGE_LOCATION_INACCESSIBLE = "The specified location is inaccessible;"
            + "try running Opus as administrator.";
    public static final String MESSAGE_FILE_EXISTS = "The specified file already exists";
    public static final String MESSAGE_PATH_WRONG_FORMAT = "The specified path is in the wrong format."
            + "Example: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + " - "
            + COMMAND_DESCRIPTION + "\n"
            + COMMAND_FORMAT + "\n"
            + "Example: " + COMMAND_WORD + "data/opus.xml";

    private String pathToFile;

    public SaveCommand(String location) {
        this.pathToFile = location.trim();
    }

    @Override
    public CommandResult execute() {
        assert pathToFile != null;

        if (pathToFile.equalsIgnoreCase("default")) {
            String defaultLocation = Config.DEFAULT_SAVE_LOCATION;
            model.changeSaveLocation(defaultLocation);
            return new CommandResult(String.format(MESSAGE_LOCATION_DEFAULT, defaultLocation));
        }

        if (doesFileExist()) {
            return new CommandResult(MESSAGE_FILE_EXISTS);
        }

        if (!isPathCorrectFormat()) {
            return new CommandResult(MESSAGE_PATH_WRONG_FORMAT);
        }

        if (!isPathAvailable()) {
            return new CommandResult(MESSAGE_LOCATION_INACCESSIBLE);
        }

        model.changeSaveLocation(pathToFile);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pathToFile));
    }

    private boolean isPathCorrectFormat() {
        return StringUtil.isValidPathToFile(pathToFile);
    }

    private boolean isPathAvailable() {
        return FileUtil.isPathAvailable(pathToFile);
    }

    private boolean doesFileExist() {
        return FileUtil.doesFileExist(pathToFile);
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
