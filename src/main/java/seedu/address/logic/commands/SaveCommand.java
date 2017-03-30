package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.ConfigChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.storage.JsonUserConfigStorage;
import seedu.address.storage.Storage;

//@@ author A0121668A

/*
 * Saves WhatsLeft to a designated location
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ":  Saves the current WhatsLeft location to the given filepath.\n" + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Downloads/Desktop/CS2103" + " 1\n" + "Example: " + COMMAND_WORD
            + " /Users/Andy/Downloads";

    public static final String MESSAGE_CHANGE_FILEPATH_SUCCESS = "Saved WhatsLeft to: %1$s";
    private static final String MESSAGE_SAVE_CONFIG_ERROR = "Failed to save configuration file";

    private String newFilePath;
    private static Config config;
    private static Storage storage;
    private static JsonUserConfigStorage jsonUserConfigStorage;

    public SaveCommand(String filePath) {
        this.newFilePath = filePath;
        jsonUserConfigStorage = new JsonUserConfigStorage(Config.DEFAULT_CONFIG_FILE);
    }

    public static void setStorage(Storage storageToSet) {
        storage = storageToSet;
    }

    public static void setConfig(Config configToset) {
        config = configToset;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyWhatsLeft whatsLeftToSave = model.getWhatsLeft();
        try {
            String fileToSave = newFilePath + "/whatsleft.xml";
            storage.saveWhatsLeft(whatsLeftToSave, fileToSave);
            config.setWhatsLeftFilePath(fileToSave);
            saveConfig();
            indicateConfigChanged();
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILEPATH);
        }
        return new CommandResult(String.format(MESSAGE_CHANGE_FILEPATH_SUCCESS, newFilePath));
    }

    private void indicateConfigChanged() {
        EventsCenter.getInstance().post(new ConfigChangedEvent(config));
    }

    private void saveConfig() throws CommandException {
        try {
            jsonUserConfigStorage.saveUserConfig(config);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_SAVE_CONFIG_ERROR);
        }
    }
}
