package seedu.taskboss.logic.commands;

import seedu.taskboss.model.category.Category;

/**
 * Lists tasks under the specified category in TaskBoss to the user.
 */
public class ListByCategoryCommand extends Command{
    
    public static final String COMMAND_WORD = "listcategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks with the specified "
            + "category name (case-sensitive) as a list with index numbers.\n"
            + "Parameters: CATEGORYNAME"
            + "Example: " + COMMAND_WORD + " project";

    private final Category category;

    public ListByCategoryCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListByCategory(category);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
