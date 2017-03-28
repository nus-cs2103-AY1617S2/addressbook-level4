package seedu.taskmanager.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.taskmanager.model.task.ReadOnlyTask;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "LIST";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all tasks\n" + COMMAND_WORD
            + " today: List all uncompleted tasks with today's date\n";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS1 = "Listed all uncompleted tasks for today";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredTaskListForListCommand(keywords, false);
            return new CommandResult(MESSAGE_SUCCESS1);
        }
    }
}
