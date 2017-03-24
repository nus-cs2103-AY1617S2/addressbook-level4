package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.ListByTagCommand;

public class ListByTagCommandParser extends CommandParser {

    public Command parse(String arguments) {
        return new ListByTagCommand(arguments);
    }

}
