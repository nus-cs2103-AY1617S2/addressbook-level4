package seedu.doist.logic.parser;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.SortCommand.SortType;

public class SortCommandParser {
    public Command parse(String argument) {
        // Remove trailing whitespace, spaces and change to upper case
        String processedArgument = argument.trim();
        processedArgument = processedArgument.replaceAll(" ", "");
        processedArgument = processedArgument.toUpperCase();

        if (!isValidSortArgument(processedArgument)) {
            return new IncorrectCommand(String.format(SortCommand.MESSAGE_SORT_CONSTRAINTS, SortCommand.MESSAGE_USAGE));
        }
        SortType sortType = SortType.valueOf(processedArgument);
        return new SortCommand(sortType);
    }

    /**
     * Returns true if a given string is a valid sort argument
     */
    public static boolean isValidSortArgument(String argument) {
        // Only priority for now
        return argument.equals(SortType.PRIORITY.toString());
    }
}
