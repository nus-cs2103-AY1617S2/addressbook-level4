package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;

public abstract class CommandParser {
    
    public abstract Command parse(String args);

}
