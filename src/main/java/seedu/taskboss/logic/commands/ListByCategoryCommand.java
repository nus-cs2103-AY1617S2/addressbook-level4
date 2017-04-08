package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.ui.JumpToCategoryListEvent;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

//@@author A0147990R
/**
 * Lists tasks under the specified category in TaskBoss to the user.
 */
public class ListByCategoryCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " OR l c/"
            + ": Lists all tasks with the specified "
            + "category name (case-sensitive) as a list with index numbers.\n"
            + "Parameters: c/CATEGORYNAME\n"
            + "Example: " + COMMAND_WORD + " c/project" + " || " + "l c/project";

    public static final String MESSAGE_SUCCESS = "Listed all tasks under category: %1$s";
    public static final String MESSAGE_CATEGORY_NOT_FOUND = "The category does not exist";
    private final Category category;

    public ListByCategoryCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTaskListByCategory(category);
 
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < 1) { //the category does not exist
            model.updateFilteredListToShowAll();
            throw new CommandException(String.format(MESSAGE_CATEGORY_NOT_FOUND));
        }

        EventsCenter.getInstance().post(new JumpToCategoryListEvent(category));
        return new CommandResult(String.format(MESSAGE_SUCCESS, category));
    }

}
