package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;

public class RenameCategoryCommand extends Command {

    public static final String COMMAND_WORD = "name";
    public static final String COMMAND_WORD_SHORT = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Renames a category.\n"
            + "Parameters: OLD_CATEGORY_NAME NEW_CATEGORY_NAME.\n"
            + "Example: " + COMMAND_WORD + " Work Workplace"
            + " || " + COMMAND_WORD_SHORT + " Home HomeSweetHome";

    public static final String MESSAGE_SUCCESS = "Category successfully renamed!";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in TaskBoss";

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
        model.updateFilteredTaskListByCategory(oldCategory);

        try {
            model.renameCategory(oldCategory, newCategory);
            model.updateFilteredTaskListByCategory(newCategory);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UniqueCategoryList.DuplicateCategoryException e) {
            return new CommandResult(MESSAGE_DUPLICATE_CATEGORY);
        }

    }

}
