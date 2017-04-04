package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.SaveCommand;

//@@author A0141928B
/**
 * Parses input argument and changes the save location
 */
public class SaveCommandParser {

    public Command parse(String args) {
        assert args != null;

        return new SaveCommand(args);
    }

}
