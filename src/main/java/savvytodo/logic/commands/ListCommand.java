package savvytodo.logic.commands;

import java.util.Optional;

import savvytodo.commons.core.Messages;
import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.category.Category;
import savvytodo.model.task.Priority;

//@@A0124863A
/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks based on priority or category or all. "
            + "Parameters: [p/PRIORITY_LEVEL] or [c/CATEGORY] or none\n"
            + "Example: " + COMMAND_WORD + " "
            + "p/high or " + COMMAND_WORD + " "
            + "c/CS2103 or just " + COMMAND_WORD;

    public static final String LIST_BY_CATEGORY_PRIORITY_SUCCESS = "Listed all tasks in ";
    public static final String LIST_ALL_SUCCESS = "Listed all tasks";

    private Optional<String> priority;
    private Optional<String> category;

    public ListCommand(Optional<String> priority, Optional<String> category) throws IllegalValueException {
        this.priority = priority;
        this.category = category;
    }

    public Optional<Category> findCategoryInModel(String key) {
        for (Category c: model.getTaskManager().getCategoryList()) {
            if (c.categoryName.equalsIgnoreCase(key)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (priority.isPresent()) {

            try {
                Priority inputPriority = new Priority(priority.get());
                model.updateFilteredTaskList(inputPriority.getPredicate());
                return new CommandResult(LIST_BY_CATEGORY_PRIORITY_SUCCESS + priority.get() + " priority");

            } catch (IllegalValueException ie) {
                throw new CommandException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
            }

        } else if (category.isPresent()) {

            Optional<Category> categoryInModel = findCategoryInModel(category.get());

            if (categoryInModel.isPresent()) {
                Category validCategory = categoryInModel.get();
                model.updateFilteredTaskList(validCategory.getPredicate());
                return new CommandResult(LIST_BY_CATEGORY_PRIORITY_SUCCESS + category.get());

            } else {
                throw new CommandException(Messages.MESSAGE_CATEGORY_NOT_EXISTS);
            }

        } else {
            model.updateFilteredListToShowAll();
            return new CommandResult(LIST_ALL_SUCCESS);
        }
    }
}
