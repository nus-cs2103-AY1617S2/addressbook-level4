package seedu.address.logic.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.ConfigChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.storage.JsonUserConfigStorage;
import seedu.address.storage.Storage;

//@@ author A0121668A

/*
 * Reads WhatsLeft from a specific location
 */
public class ReadCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final String COMMAND_WORD = "read";
    public static final Object MESSAGE_USAGE = COMMAND_WORD
            + ":  Reads the WhatsLeft task storage from given location filepath.\n" + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Downloads/Desktop/CS2103" + " 1\n" + "Example: " + COMMAND_WORD
            + " /Users/Andy/Downloads";

    public static final String MESSAGE_READ_WHATSLEFT_SUCCESS = "Successfully read WhatsLeft from: %1$s";
    private static final String MESSAGE_SAVE_CONFIG_ERROR = "Failed to read stored WhatsLeft tasklist";
    private static final String MESSAGE_START_WITH_NEW_WHATSLEFT = "Data file not found. "
            + "Will be starting with a sample WhatsLeft";

    private String newFilePath;
    private static Config config;
    private static Storage storage;
    private static JsonUserConfigStorage jsonUserConfigStorage;

    public ReadCommand(String filePath) {
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
        try {
            String fileToRead = newFilePath + "/whatsleft.xml";
            Optional<ReadOnlyWhatsLeft> whatsleftToRead = storage.readWhatsLeft(fileToRead);
            config.setWhatsLeftFilePath(fileToRead);
            saveConfig();
            indicateConfigChanged();
            resetWhatsLeft(whatsleftToRead);
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_FILEPATH);
        } catch (DataConversionException dce) {
            throw new CommandException(Messages.MESSAGE_DATA_CONVERSION_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_READ_WHATSLEFT_SUCCESS, newFilePath));
    }

    /**
     * Update the model with new WhatsLeft data Start a new WhatsLeft record, if
     * there is no whatLeft file found in the specified location.
     *
     * @param whatsleftToRead
     * @throws IOException
     */
    private void resetWhatsLeft(Optional<ReadOnlyWhatsLeft> whatsleftToRead) throws IOException {
        ReadOnlyWhatsLeft newWhatsLeft;
        if (!whatsleftToRead.isPresent()) {
            newWhatsLeft = new WhatsLeft();
            logger.info(MESSAGE_START_WITH_NEW_WHATSLEFT);
        } else {
            newWhatsLeft = whatsleftToRead.get();
        }
        model.resetData(newWhatsLeft);
        storage.saveWhatsLeft(newWhatsLeft);
    }

    private void indicateConfigChanged() {
        EventsCenter.getInstance().post(new ConfigChangedEvent(config));
    }

    /**
     * Update configuration file with new configuration
     *
     * @throws CommandException
     */
    private void saveConfig() throws CommandException {
        try {
            jsonUserConfigStorage.saveUserConfig(config);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_SAVE_CONFIG_ERROR);
        }
    }
}
