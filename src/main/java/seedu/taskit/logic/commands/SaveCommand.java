//@@author A0141011J
package seedu.taskit.logic.commands;

import java.io.IOException;

import seedu.taskit.storage.XmlAddressBookStorage;

/**
 * Specify a specific folder and a file for data storage
 */
public class SaveCommand extends Command{

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_SUCCESS = "Saved to ";

    public static final String MESSAGE_INVALID_FILE = "The new file path is invalid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Save the task manager to the specified file path.\n"
            + "Parameters: FILEPATH (must be a string)\n"
            + "Example: " + COMMAND_WORD + " newFile.txt";

    public static final String MESSAGE_SAVE_SUCCESS = "Saved to ";

    private String newFilePath;

    private XmlAddressBookStorage newStorage;

    public SaveCommand(String newPath){
        newFilePath=newPath;
    }


    @Override
    public CommandResult execute() {
        try {
            newStorage = new XmlAddressBookStorage(this.newFilePath);
            newStorage.saveAddressBook(model.getAddressBook());
        } catch(IOException e){
             return new CommandResult(MESSAGE_INVALID_FILE);
        }
        return new CommandResult(String.format(MESSAGE_SAVE_SUCCESS) + newFilePath);
    }


}
