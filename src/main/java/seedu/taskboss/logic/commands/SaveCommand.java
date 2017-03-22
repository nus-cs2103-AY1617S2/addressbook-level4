package seedu.taskboss.logic.commands;

/**
 * Saves the data at specific filepath. Creates filepath if it does not exist
 */

public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_WORD_SHORT = "sv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                + "Saves the data at specific filepath.\n"
                + "Example: " + COMMAND_WORD
                + " C://user/desktop/taskboss";

    public static final String MESSAGE_SUCCESS = "The data has been saved!";

    public static final String MESSAGE_INVALID_FILEPATH = "The filepath is invalid.";

    private final String filepath;

    public SaveCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
        assert storage != null;

        if (filepath.contains("+") || filepath.contains ("#") ||
                filepath.contains ("^") || filepath.contains ("*")) {
            return new CommandResult(MESSAGE_INVALID_FILEPATH);
        } 

        try {
            storage.setFilePath(filepath);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_INVALID_FILEPATH);
        }
}
