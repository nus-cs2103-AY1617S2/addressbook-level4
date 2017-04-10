package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.GetGoogleCalendarCommand;

//@@author A0140063X-reused
public class GetGoogleCalendarCommandParser extends CommandParser {

    @Override
    public Command parse(String args) {
        return new GetGoogleCalendarCommand();
    }

}
