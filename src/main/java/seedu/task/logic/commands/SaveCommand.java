package seedu.task.logic.commands;

/**
 * Saves task manager in a different directory.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves the task manager in a different directory. "
            + "Parameters: PATHNAME \n"
            + "Example: " + COMMAND_WORD
            + " ";

    public static final String MESSAGE_SUCCESS = "File save at: %1$s";

    private String pathName;
    
    /**
     * Creates a Save command
     */
    public SaveCommand(String pathName) {
        this.pathName = pathName;
    }

    @Override
    public CommandResult execute() {
    	model.changeFilePath(pathName);
    	return new CommandResult(MESSAGE_SUCCESS);
    }

}
