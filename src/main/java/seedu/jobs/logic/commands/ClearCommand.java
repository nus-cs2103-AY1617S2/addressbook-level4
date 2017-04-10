package seedu.jobs.logic.commands;

import seedu.jobs.model.TaskBook;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Description book has been cleared!";


    @Override
    public CommandResult execute() throws IllegalTimeException {
        assert model != null;
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
