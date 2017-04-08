package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.JumpToCategoryListEvent;
import seedu.taskboss.model.category.Category;

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
    private final Category category;

    public ListByCategoryCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListByCategory(category);
        EventsCenter.getInstance().post(new JumpToCategoryListEvent(category));
        return new CommandResult(String.format(MESSAGE_SUCCESS, category));
    }

}
