package seedu.doist.logic.parser;

import java.util.ArrayList;
import java.util.List;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.SortCommand.SortType;

//@@author A0140887W
public class SortCommandParser {
    public Command parse(String argument) {
        // Remove trailing whitespace
        String processedArgument = argument.trim();
        // remove all leading spaces, new line characters etc
        processedArgument = processedArgument.replaceAll("^\\s+", "");
        // remove extra spaces in between, change to single space
        processedArgument = processedArgument.replaceAll("\\s+", " ");
        processedArgument = processedArgument.toUpperCase();

        String[] arguments = processedArgument.split(" ");

        if (!areValidSortArguments(arguments)) {
            return new IncorrectCommand(String.format(SortCommand.MESSAGE_SORT_CONSTRAINTS, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(stringArrayToSortTypeList(arguments));
    }

    /**
     * Returns true if a given string array has a valid sort arguments
     */
    public static boolean areValidSortArguments(String[] arguments) {
        for (String argument : arguments) {
            if (!checkIfValidSortType(argument)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkIfValidSortType(String argument) {
        return argument.equals(SortType.PRIORITY.toString()) ||
                argument.equals(SortType.ALPHA.toString()) ||
                argument.equals(SortType.TIME.toString());
    }

    private static List<SortType> stringArrayToSortTypeList(String[] arguments) {
        List<SortType> list = new ArrayList<SortType>();
        try {
            for (String argument : arguments) {
                list.add(SortType.valueOf(argument));
            }
        } catch (IllegalArgumentException e) {
            assert false : "Should check that arguments are valid sort type before converting to list";
        }
        return list;
    }
}
