package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ThemeChangeCommand;

public class ThemeChangeCommandParser extends CommandParser {

    public Command parse(String args) {
        return new ThemeChangeCommand(args);
    }

}
