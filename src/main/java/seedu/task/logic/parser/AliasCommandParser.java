package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.AliasCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

public class AliasCommandParser {

    public Command parse(String args) {
        Pattern pt = Pattern.compile(AliasCommand.COMMAND_STRINGS_VALIDATION_REGEX);
        Matcher matcher = pt.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AliasCommand.MESSAGE_USAGE));
        }
        String alias = matcher.group("alias");
        String original = matcher.group("original");
        if (alias.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AliasCommand.MESSAGE_INVALID_ALIAS_COMMAND_STRING));
        }
        if (original.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AliasCommand.MESSAGE_INVALID_ORIGNINAL_COMMAND_STRING));
        }
        return new AliasCommand(alias, original);
    }
}
