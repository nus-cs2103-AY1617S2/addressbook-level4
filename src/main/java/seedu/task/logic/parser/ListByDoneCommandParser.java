package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByDoneCommand;

public class ListByDoneCommandParser extends CommandParser {

    private boolean isDone = true;
    @Override
    public Command parse(String args) {
        // TODO Auto-generated method stub
        return new ListByDoneCommand(isDone);
    }

}
