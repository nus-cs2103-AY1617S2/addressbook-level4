package seedu.taskmanager.logic.commands;

import java.util.Set;

//@@author A0141102H
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "LIST";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all tasks\n" + COMMAND_WORD
            + " day/date: List all uncompleted tasks with day/date\n";
    public static final String MESSAGE_SUCCESS_ALL_TASK = "Listed all uncompleted tasks";
    public static final String MESSAGE_SUCCESS_UNCOMPLETED_TASK_FOR_DATE = "Listed all uncompleted tasks for ";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ALL_TASK);
        } else {
            model.updateFilteredTaskListForListCommand(keywords, false);
            return new CommandResult(MESSAGE_SUCCESS_UNCOMPLETED_TASK_FOR_DATE + keywords.toString());
        }
    }
}
