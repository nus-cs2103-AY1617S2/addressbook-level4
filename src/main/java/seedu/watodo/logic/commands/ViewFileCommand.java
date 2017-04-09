package seedu.watodo.logic.commands;

import java.util.Optional;

import seedu.watodo.commons.core.Config;
import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.commons.util.ConfigUtil;
//@@author A0141077L-reused
/**
 * To view the current storage file location.
 */
public class ViewFileCommand extends Command {

    public static final String COMMAND_WORD = "viewfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the current storage file location.\n"
            + "Example: " + COMMAND_WORD;

    public static final String VIEW_FILE_MESSAGE = "Storage file is currently located at %s";

    @Override
    public CommandResult execute() {
        Config currConfig = getConfig();
        return new CommandResult(String.format(VIEW_FILE_MESSAGE, currConfig.getWatodoFilePath()));
    }

    private Config getConfig() {
        Config initialisedConfig;
        try {
            Optional<Config> optionalConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            initialisedConfig = optionalConfig.orElse(new Config());
        } catch (DataConversionException dce) {
            initialisedConfig = new Config();
        }
        return initialisedConfig;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
