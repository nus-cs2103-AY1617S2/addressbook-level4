package seedu.onetwodo.logic.parser;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public Command parse(String args) {
        String argsTrimmed = args.trim();
        
        if (argsTrimmed.isEmpty()) {
            return new ListCommand("UNDONE");
        } else if (argsTrimmed.equals("done")) {
            return new ListCommand("DONE");
        } else if (argsTrimmed.equals("all")) {
            return new ListCommand("ALL");
        } else if (argsTrimmed.equals("undone")) {
            return new ListCommand("UNDONE");
        } else {
            return new IncorrectCommand("Invalid list command");
        }
    }
}
