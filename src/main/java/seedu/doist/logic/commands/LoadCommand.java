package seedu.doist.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.storage.XmlTodoListStorage;

//@@author A0140887W
/**
 * Load a todoList xml file into Doist, replacing current data
 */
public class LoadCommand extends Command {

    public String path;

    public static final String DEFAULT_COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ":\n" + "Loads an external XML data file into Doist and overrides existing tasks." + "\n"
            + "You can use both absolute and relative file paths to a data file. Use // to seperate directories.\n\t"
            + "Parameters: filePath " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + " C:/Users/todolist.xml";

    public static final String MESSAGE_SUCCESS = "New data from %1$s loaded! Tasks will be replaced.";
    public static final String MESSAGE_UNABLE_TO_READ = "Unable to read file!";
    public static final String MESSAGE_INVALID_FILE = "Invalid data file! Unable to load.";
    public static final String MESSAGE_NOT_FILE = "This is not a file. Unable to load.";
    public static final String MESSAGE_NOT_EXIST = "File does not exist. Unable to load.";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path entered! \n%1$s";


    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            File file = new File(path);
            if (!file.isFile()) {
                throw new CommandException(MESSAGE_NOT_FILE);
            }
            Optional<ReadOnlyTodoList> todoList = new XmlTodoListStorage(file.getCanonicalPath()).readTodoList();
            if (todoList.isPresent()) {
                model.resetData(todoList.get());
            } else {
                throw new CommandException(MESSAGE_NOT_EXIST);
            }
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_INVALID_FILE);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_READ);
        } catch (SecurityException e) {
            throw new CommandException(MESSAGE_UNABLE_TO_READ);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, path), true);
    }
}
