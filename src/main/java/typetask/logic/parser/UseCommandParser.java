package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import typetask.commons.util.FileUtil;
import typetask.logic.commands.Command;
import typetask.logic.commands.IncorrectCommand;
import typetask.logic.commands.UseCommand;

public class UseCommandParser {
    public Command parse(String args) {

        if (args.equals("")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UseCommand.MESSAGE_USAGE));
        } else if (args.substring(args.trim().length() - 3).equals(".xml")) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Specify the folder Location"));
        } else if (!FileUtil.isFileExists(new File(FileUtil.getPath(args.trim()+ "\\taskManager.xml")))) {
            return new IncorrectCommand(
                    String.format("File does not exists."));
        }

        return new UseCommand(args.trim());
    }
}
