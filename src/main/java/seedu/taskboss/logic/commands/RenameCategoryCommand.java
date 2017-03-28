package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.exceptions.DefaultCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;

//@@author A0143157J
/**
 * Renames an existing category in TaskBoss.
 */
public class RenameCategoryCommand extends Command {

    public static final String COMMAND_WORD = "name";
    public static final String COMMAND_WORD_SHORT = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Renames an existing category.\n"
            + "Parameters: OLD_CATEGORY_NAME NEW_CATEGORY_NAME.\n"
            + "Example: " + COMMAND_WORD + " Work Workplace"
            + " || " + COMMAND_WORD_SHORT + " Home HomeSweetHome";

    public static final String MESSAGE_SUCCESS = "Category successfully renamed!";

    //@@author A0144904H
    public static final String MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME = "All Tasks category cannot be renamed";
    public static final String MESSAGE_DONE_CATEGORY_CANNOT_RENAME = "Done category cannot be renamed";
    public static final String MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE = "Cannot rename a category to Done";
    public static final String MESSAGE_CATEGORY_CANNOT_RENAME_TO_ALL_TASKS = "Cannot rename a category to AllTasks";

    //@@author
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in TaskBoss.";
    public static final String MESSAGE_DOES_NOT_EXIST_CATEGORY = "This category does not exist in TaskBoss.";


    public final String oldCategory;
    public final String newCategory;

    public RenameCategoryCommand(String oldCategory, String newCategory) {
        this.oldCategory = oldCategory;
        this.newCategory = newCategory;
    }

    //@@author A0144904H
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException,
                                              InvalidDatesException, DefaultCategoryException {
        assert model != null;

        Category oldCategory = new Category(this.oldCategory);
        Category newCategory = new Category(this.newCategory);

        try {
            detectErrors(oldCategory, newCategory);
            try {
                model.renameCategory(oldCategory, newCategory);
                model.updateFilteredTaskListByCategory(newCategory);
                return new CommandResult(MESSAGE_SUCCESS);
            } catch (UniqueCategoryList.DuplicateCategoryException e) {
                return new CommandResult(MESSAGE_DUPLICATE_CATEGORY);
            }

        } catch (DefaultCategoryException dce) {
            if (dce.getMessage().equals(MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME)) {
                throw new CommandException(MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME);
            } else if (dce.getMessage().equals(MESSAGE_DONE_CATEGORY_CANNOT_RENAME)) {
                throw new CommandException(MESSAGE_DONE_CATEGORY_CANNOT_RENAME);
            } else if (dce.getMessage().equals(MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE)) {
                throw new CommandException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE);
            } else {
                throw new CommandException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_ALL_TASKS);
            }
        }


    }

    //@@author A0144904H
    /**
     * @param oldCategory
     * @param newCategory
     * @throws IllegalValueException
     * @throws DefaultCategoryException
     */
    private void detectErrors(Category oldCategory, Category newCategory)
                                            throws IllegalValueException, DefaultCategoryException {

        if (oldCategory.equals(new Category(AddCommand.DEFAULT_ALL_TASKS))) {
            throw new DefaultCategoryException(MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME);
        } else if (oldCategory.equals(new Category(AddCommand.DEFAULT_DONE))) {
            throw new DefaultCategoryException(MESSAGE_DONE_CATEGORY_CANNOT_RENAME);
        } else if (newCategory.equals(new Category(AddCommand.DEFAULT_DONE))) {
            throw new DefaultCategoryException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE);
        } else if (newCategory.equals(new Category(AddCommand.DEFAULT_ALL_TASKS))) {
            throw new DefaultCategoryException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_ALL_TASKS);
        }
    }

}
