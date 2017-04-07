package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByNotDoneCommand;

//@@author A0139975J-unused
// merged with list command
public class ListByNotDoneCommandParser extends CommandParser {

    private boolean isDone = false;

    @Override
    public Command parse(String args) {
        return new ListByNotDoneCommand(isDone);
    }

}
