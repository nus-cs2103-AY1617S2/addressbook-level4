package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.LoadCommand;

public class LoadCommandParser {

    public Command parse(String arguments) {
        String[] argumentsArray = arguments.trim().split(" ");

        if (argumentsArray.length == 1) {
            String filenameToLoad = argumentsArray[0].trim();
            return new LoadCommand(filenameToLoad, !LoadCommand.IS_CREATE_NEW_FILE);
        } else if (argumentsArray.length == 2) {
            String option = argumentsArray[0].trim();
            if (option.equals("new")) {
                String filenameToLoad = argumentsArray[1].trim();
                return new LoadCommand(filenameToLoad, LoadCommand.IS_CREATE_NEW_FILE);
            }
        }

        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }
}
