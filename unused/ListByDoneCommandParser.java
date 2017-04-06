package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByDoneCommand;

    //@@author A0139975J
public class ListByDoneCommandParser extends CommandParser {

    private boolean isDone = true;

    
    @Override
    public Command parse(String args) {
        return new ListByDoneCommand(isDone);
    }

}
