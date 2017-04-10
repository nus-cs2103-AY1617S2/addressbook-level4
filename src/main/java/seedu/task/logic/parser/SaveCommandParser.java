package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.SaveCommand;

public class SaveCommandParser extends CommandParser {

    public Command parse(String args) {
        return new SaveCommand(args.trim());
    }

}
