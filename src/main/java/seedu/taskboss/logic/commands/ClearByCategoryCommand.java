package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.ReadOnlyTask;

//@@author A0147990R
public class ClearByCategoryCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_SHORT = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " OR c c/"
            + ": Clears all tasks with the specified "
            + "category name (case-sensitive) as a list with index numbers.\n"
            + "Parameters: c/CATEGORYNAME\n"
            + "Example: " + COMMAND_WORD + " c/project" + " || " + "c c/project";

    public static final String MESSAGE_CLEAR_TASK_SUCCESS = "Clear all tasks under the category";
    public static final String MESSAGE_CATEGORY_NOT_FOUND = "The category does not exist";

    private final Category category;

    public ClearByCategoryCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws IllegalValueException {
        model.updateFilteredTaskListByCategory(category);
        UnmodifiableObservableList<ReadOnlyTask> taskListWithCategory = model.getFilteredTaskList();

        if (taskListWithCategory.size() < 1) {
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_CATEGORY_NOT_FOUND));
        }
        model.clearTasksByCategory(category);
        return new CommandResult(String.format(MESSAGE_CLEAR_TASK_SUCCESS));
    }

}
