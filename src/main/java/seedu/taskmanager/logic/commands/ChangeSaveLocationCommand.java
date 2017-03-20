package seedu.taskmanager.logic.commands;

import java.io.File;

import seedu.taskmanager.logic.commands.exceptions.CommandException;

public class ChangeSaveLocationCommand extends Command {

    public static final String COMMAND_WORD = "SAVE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the save location of task manager.\n"
    // + "Example: " + COMMAND_WORD
    // + " eat lunch ON thursday\n"
            + "Type HELP for user guide with detailed explanations of all commands";

    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";
   
    private final File toSave;
    
    public ChangeSaveLocationCommand(File location) {
        this.toSave = location;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // TODO Auto-generated method stub
        return null;
    }

}
