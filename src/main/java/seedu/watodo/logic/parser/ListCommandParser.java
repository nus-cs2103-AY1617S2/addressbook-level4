package seedu.watodo.logic.parser;

import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.ListAllCommand;
import seedu.watodo.logic.commands.ListCommand;
import seedu.watodo.logic.commands.ListDeadlineCommand;
import seedu.watodo.logic.commands.ListDoneCommand;
import seedu.watodo.logic.commands.ListEventCommand;
import seedu.watodo.logic.commands.ListFloatCommand;
import seedu.watodo.logic.commands.ListUndoneCommand;

//@@author A0139872R-reused
/**
 * Parses input arguments into various types of list commands for execution.
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns its respective ListCommand objects for execution.
     */
    public Command parse(String args) {
        if (args.contains("#")) {
            return new ListTagCommandParser().parse(args);
        }

        switch (args) {

        case ListAllCommand.ARGUMENT:
            return new ListAllCommand();

        case ListDeadlineCommand.ARGUMENT:
            return new ListDeadlineCommand();

        case ListDoneCommand.ARGUMENT:
            return new ListDoneCommand();

        case ListEventCommand.ARGUMENT:
            return new ListEventCommand();

        case ListFloatCommand.ARGUMENT:
            return new ListFloatCommand();

        case ListUndoneCommand.ARGUMENT:
            return new ListUndoneCommand();
          
        case ListCommand.ARGUMENT:
            return new ListCommand();

        default:
            return new ListDateCommandParser().parse(args);
        }
    }
}
