package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByDoneCommand;
    //@@author A0139975J
public class ListByDoneCommandParser extends CommandParser {

    private boolean isDone = true;
    //@@author A0139975J
    @Override
    public Command parse(String args) {
        // TODO Auto-generated method stub
        return new ListByDoneCommand(isDone);
    }

}
