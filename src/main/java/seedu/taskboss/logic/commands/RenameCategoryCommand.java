package seedu.taskboss.logic.commands;

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
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in TaskBoss.";
    public static final String MESSAGE_DOES_NOT_EXIST_CATEGORY = "category does not exist in TaskBoss.";
    public static final String MESSAGE_FAIL_ALL_TASK_CATEGORY_CANNOT_MODIFY = "All task category cannot be renamed";
    public static final String MESSAGE_FAIL_DONE_CATEGORY_CANNOT_MODIFY = "Done category cannot be renamed";


    public final String oldCategory;
    public final String newCategory;

    public RenameCategoryCommand(String oldCategory, String newCategory) {
        this.oldCategory = oldCategory;
        this.newCategory = newCategory;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException, InvalidDatesException {
        assert model != null;

        Category oldCategory = new Category(this.oldCategory);
        Category newCategory = new Category(this.newCategory);

        String messageThrown = null;

        if(oldCategory.equals(new Category("Alltasks"))) {
            messageThrown = MESSAGE_FAIL_ALL_TASK_CATEGORY_CANNOT_MODIFY;
            throw new CommandException(RenameCategoryCommand.
                    MESSAGE_FAIL_ALL_TASK_CATEGORY_CANNOT_MODIFY);
        }

        if(oldCategory.equals(new Category("Done"))) {
            messageThrown = MESSAGE_FAIL_DONE_CATEGORY_CANNOT_MODIFY;
            throw new CommandException(RenameCategoryCommand.
                    MESSAGE_FAIL_DONE_CATEGORY_CANNOT_MODIFY);
        }

        model.updateFilteredTaskListByCategory(oldCategory);

        try {
            model.renameCategory(oldCategory, newCategory);
            model.updateFilteredTaskListByCategory(newCategory);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            return new CommandResult(MESSAGE_DUPLICATE_CATEGORY);
        }  catch (CommandException ce) {
            return new CommandResult(messageThrown);
        }
    }
}
