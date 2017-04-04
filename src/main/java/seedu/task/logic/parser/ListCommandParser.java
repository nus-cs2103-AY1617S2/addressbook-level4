package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListCommand;

//@@author A0139975J-reused
public class ListCommandParser extends CommandParser {


    @Override
    public Command parse(String args) {
        // TODO Auto-generated method stub
        return new ListCommand(args);
    }

}
