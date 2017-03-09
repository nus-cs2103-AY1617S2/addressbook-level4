package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.util.Date;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;

public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        try {
            if (!args.isEmpty() && args.contains("by ")) {
                ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DEADLINE);
                argsTokenizer.tokenize(args);
                List<Date> endDateList = new DateTimeParser().parse(argsTokenizer.getValue(PREFIX_DEADLINE).get()).get(0).getDates();
                List<Date> startDateList = new DateTimeParser().parse("now").get(0).getDates();
                return new ListCommand(startDateList.get(0), endDateList.get(0));
            }
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand();
    }

}
