package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.FilterCommand;
import seedu.task.logic.commands.IncorrectCommand;

//@@author A0164061N
public class FilterCommandParser {

    public Command parse(String args) {
        args = args.toLowerCase();
        args = args.trim();
        final Matcher matcher = PATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        final String keyword = matcher.group("keyword");
        return new FilterCommand(keyword);
    }

}
