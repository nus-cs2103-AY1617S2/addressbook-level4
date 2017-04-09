package seedu.whatsleft.logic.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatsleft.MainApp;
import seedu.whatsleft.commons.core.Config;
import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.LogsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.events.model.ConfigChangedEvent;
import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.storage.Storage;

//@@author A0121668A

/**
 * Reads WhatsLeft from a specific location
 */
public class ReadCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final String COMMAND_WORD = "read";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":  Reads the WhatsLeft task storage from given location filepath.\n" + "Parameters: FILEPATH\n"
            + "Example (Relative Path): " + COMMAND_WORD + " ./Data/WhatsLeft.xml" + " \n"
            + "Example (Absolute Path): " + COMMAND_WORD + " /Users/Andy/Downloads/WhatsLeft.xml";

    public static final String MESSAGE_READ_WHATSLEFT_SUCCESS = "Successfully read WhatsLeft from: %1$s";
    private static final String MESSAGE_SAVE_CONFIG_ERROR = "Failed to read stored WhatsLeft tasklist";
    private static final String MESSAGE_START_WITH_NEW_WHATSLEFT = "Data file not found. "
            + "Will be starting with a sample WhatsLeft";

    private String newFilePath;
    private static Config config;
    private static Storage storage;

    public ReadCommand(String filePath) {
        this.newFilePath = filePath;
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
            Optional<ReadOnlyWhatsLeft> whatsleftToRead = storage.readWhatsLeft(newFilePath);
            config.setWhatsLeftFilePath(newFilePath);
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
            storage.saveUserConfig(config);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_SAVE_CONFIG_ERROR);
        }
    }
}
