package seedu.task.logic.parser;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.PostGoogleCalendarCommand;

public class PostGoogleCalendarCommandParser extends CommandParser {

    //@@author A0140063X-reused
    @Override
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new  PostGoogleCalendarCommand(PostGoogleCalendarCommand.NO_INDEX);
        }

        return new PostGoogleCalendarCommand(index.get());
    }
}
