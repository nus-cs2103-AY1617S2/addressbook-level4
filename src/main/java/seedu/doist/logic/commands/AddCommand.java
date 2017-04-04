package seedu.doist.logic.commands;

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.ui.JumpToListRequestEvent;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 * Adds a task to the to-do list.
 */
public class AddCommand extends Command {
    public static final String DEFAULT_COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD + ": Adds a task to Doist\n"
            + "Parameters: TASK_DESCRIPTION  [\\from START_TIME] [\\to END_TIME] [\\as PRIORITY] [\\under TAG...]\n"
            + "Parameters: TASK_DESCRIPTION  [\\by DEADLINE]\n"
            + "Example: " + DEFAULT_COMMAND_WORD + " Group meeting \\from 1600 \\to 1800 \\as IMPORTANT "
                    + "\\under school ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the to-do list";
    public static final String MESSAGE_NO_DESC = "Tasks must have description";

    private final Task toAdd;

    public AddCommand(Task taskToAdd) {
        toAdd = taskToAdd;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            int index  = model.addTask(toAdd);
            if (index >= 0) {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }
}






