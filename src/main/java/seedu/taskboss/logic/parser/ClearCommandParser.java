package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.NoSuchElementException;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.ClearByCategoryCommand;
import seedu.taskboss.logic.commands.ClearCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.model.category.Category;

//@@author A0147990R
/**
 * Parses input arguments and creates a new ClearCommand
 */
public class ClearCommandParser {

    private static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public Command parse(String args) {

        String categoryName;
        if (EMPTY_STRING.equals(args)) {
            return new ClearCommand();
        }

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);

        try {
            categoryName = argsTokenizer.getValue(PREFIX_CATEGORY).get();
            Category category = new Category(categoryName);
            return new ClearByCategoryCommand(category);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClearByCategoryCommand.MESSAGE_USAGE));
        }

    }

}
