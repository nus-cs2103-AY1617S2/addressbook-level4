package seedu.taskboss.logic.parser;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.ListByCategoryCommand;
import seedu.taskboss.model.category.Category;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListByCategoryCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListByCategoryCommand
     * and returns an ListByCategoryCommand object for execution.
     */
    public Command parse(String args) {
        try {
            Category category = new Category(args);
            return new ListByCategoryCommand(category);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
