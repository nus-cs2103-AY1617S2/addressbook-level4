package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import typetask.logic.commands.Command;
import typetask.logic.commands.SettingCommand;
import typetask.logic.commands.IncorrectCommand;

public class SettingCommandParser {
    public Command parse(String args) {

        if (args.equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettingCommand.MESSAGE_USAGE));
        }

        return new SettingCommand(args.trim());
    }
}
