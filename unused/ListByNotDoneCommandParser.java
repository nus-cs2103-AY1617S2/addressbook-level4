package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByNotDoneCommand;

//@@author A0139975J
public class ListByNotDoneCommandParser extends CommandParser {

    private boolean isDone = false;
    
    @Override
    public Command parse(String args) {
       // TODO Auto-generated method stub
        return new ListByNotDoneCommand(isDone);
    }

}
