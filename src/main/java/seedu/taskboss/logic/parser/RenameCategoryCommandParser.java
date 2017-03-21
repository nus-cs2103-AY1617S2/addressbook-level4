package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.RenameCategoryCommand;

public class RenameCategoryCommandParser {

    private static final int NUM_CATEGORIES_PARAM = 2;
    private static final int INDEX_OLD_CATEGORY = 0;
    private static final int INDEX_NEW_CATEGORY = 1;
    private static final String FORMAT_ALPHANUMERIC = "[A-Za-z0-9]+";
    private static final String ERROR_NON_ALPHANUMERIC = "Category names should be alphanumeric.";

    public Command parse(String args) {
        String[] categories = ParserUtil.parseRenameCategory(args);
        if (categories.length != NUM_CATEGORIES_PARAM) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCategoryCommand.MESSAGE_USAGE));
        } else if (!categories[INDEX_OLD_CATEGORY].matches(FORMAT_ALPHANUMERIC) ||
            !categories[INDEX_NEW_CATEGORY].matches(FORMAT_ALPHANUMERIC)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ERROR_NON_ALPHANUMERIC));
        } 

        
        return new RenameCategoryCommand(categories[INDEX_OLD_CATEGORY], categories[INDEX_NEW_CATEGORY]);
    }
}
