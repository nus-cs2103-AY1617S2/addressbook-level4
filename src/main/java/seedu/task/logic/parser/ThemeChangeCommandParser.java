package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ThemeChangeCommand;
import seedu.task.ui.Theme;

//@@author A0142487Y-generated
public class ThemeChangeCommandParser extends CommandParser {

    public Command parse(String args) {
        return Theme.getTheme(args) == null ? new
                IncorrectCommand(String.format(ThemeChangeCommand.MESSAGE_FAILURE, args))
                : new ThemeChangeCommand(args);
    }

}
