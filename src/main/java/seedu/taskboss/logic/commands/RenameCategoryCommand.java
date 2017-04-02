package seedu.taskboss.logic.commands;

import java.util.logging.Logger;

import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;

//@@author A0143157J
/**
 * Renames an existing category in TaskBoss.
 */
public class RenameCategoryCommand extends Command {

    private final Logger logger = LogsCenter.getLogger(RenameCategoryCommand.class);

    private static final String EMPTY_STRING = "";
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

    //@@author A0143157J
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in TaskBoss.";
    public static final String MESSAGE_DOES_NOT_EXIST_CATEGORY = "This category does not exist in TaskBoss.";

    public final String oldCategory;
    public final String newCategory;

    public RenameCategoryCommand(String oldCategory, String newCategory) {
        this.oldCategory = oldCategory;
        this.newCategory = newCategory;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException,
                                        InvalidDatesException {
        assert model != null;

        Category oldCategory = new Category(this.oldCategory);
        Category newCategory = new Category(this.newCategory);

        try {
            logger.info("Attempting to rename category");
            checkBuiltINCategoryViolation(oldCategory, newCategory);
            model.renameCategory(oldCategory, newCategory);
            model.updateFilteredTaskListByCategory(newCategory);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (BuiltInCategoryException dce) {
            logger.info("User attempted to modify built-in categories' names. Throwing CommandException");
            throwCommandExceptionForBuiltInCategory(dce);
            return new CommandResult(EMPTY_STRING); // will never reach this statement
        } catch (DuplicateCategoryException e) {
            logger.info("User attempted to create duplicate categories. Returning user feedback");
            throw new CommandException(MESSAGE_DUPLICATE_CATEGORY);
        }
    }

    //@@author A0144904H
    private void throwCommandExceptionForBuiltInCategory(BuiltInCategoryException dce)
            throws CommandException {
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

    private void checkBuiltINCategoryViolation(Category oldCategory, Category newCategory)
            throws BuiltInCategoryException, IllegalValueException {
        if (oldCategory.equals(new Category(AddCommand.BUILT_IN_ALL_TASKS))) {
            throw new BuiltInCategoryException(MESSAGE_ALL_TASK_CATEGORY_CANNOT_RENAME);
        } else if (oldCategory.equals(new Category(AddCommand.BUILT_IN_DONE))) {
            throw new BuiltInCategoryException(MESSAGE_DONE_CATEGORY_CANNOT_RENAME);
        } else if (newCategory.equals(new Category(AddCommand.BUILT_IN_DONE))) {
            throw new BuiltInCategoryException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_DONE);
        } else if (newCategory.equals(new Category(AddCommand.BUILT_IN_ALL_TASKS))) {
            throw new BuiltInCategoryException(MESSAGE_CATEGORY_CANNOT_RENAME_TO_ALL_TASKS);
        }
    }

}
