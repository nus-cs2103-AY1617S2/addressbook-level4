package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import todolist.logic.commands.Command;
import todolist.logic.commands.IncorrectCommand;
import todolist.logic.commands.SaveCommand;

//@@author A0143648Y

/**
 * Parses arguments in the context of the save command.
 *
 * @param args
 *            full command args string
 * @return the prepared command
 * 
 * 
 */
public class SaveCommandParser {

    private static final Pattern SAVE_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

    public SaveCommandParser() {
    }

    public Command parse(String args) {
        final Matcher matcher = SAVE_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        return new SaveCommand(args);
    }
}
