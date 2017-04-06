package seedu.jobs.logic.commands;

import seedu.jobs.commons.exceptions.IllegalValueException;

public class DisplayCommand extends Command {
    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to JOBS. "
            + "Parameters: add name/TASK_NAME [start/START_TIME end/END_TIME recur/PERIOD desc/DESCRIPTION tag/TAGS] \n"
            + "Example: " + COMMAND_WORD
            + " name/tutorial start/13/01/17 11:00 end/13/01/17 12:00 recur/7 desc/\"Tutorial of CS2103\" tag/CS2103";

    public static final String MESSAGE_SUCCESS = "Calendar Displayed";

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public DisplayCommand() {
        
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.display();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
