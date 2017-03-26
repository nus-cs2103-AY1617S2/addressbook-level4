package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;
import typetask.logic.commands.SaveCommand;

public class SaveCommandParser {
    public Command parse(String args) {

        if (args.equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        } else if (args.substring(args.trim().length() - 3).equals(".xml")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Specify the folder Location"));
        }

        return new SaveCommand(args.trim());
    }
}
