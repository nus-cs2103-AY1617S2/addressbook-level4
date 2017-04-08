package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.NoSuchElementException;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.ListByCategoryCommand;
import seedu.taskboss.model.category.Category;

/**
 * Parses input arguments and creates a new ListCommand object or ListByCategoryCommand object
 */
public class ListCommandParser {

    //@@author A0147990R
    private static final String EMPTY_STRING = "";
    private static final String BUILDIN_ALLTAKS = "Alltasks";

    /**
     * Returns a ListCommand if there is no argument.
     * Otherwise parses the given {@code String} of arguments in the context of the ListByCategoryCommand
     * and returns an ListByCategoryCommand object for execution.
     */
    public Command parse(String args) {
        if (EMPTY_STRING.equals(args.trim())) {
            try {
                return new ListByCategoryCommand(new Category(BUILDIN_ALLTAKS));
            } catch (IllegalValueException e) {
                // never reach here as BUILDIN_ALLTAKS is a valid category name
                e.printStackTrace();
            }
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);

        try {
            String categoryName = argsTokenizer.getValue(PREFIX_CATEGORY).get();
            Category category = new Category(categoryName);
            return new ListByCategoryCommand(category);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByCategoryCommand.MESSAGE_USAGE));
        }

    }

}
