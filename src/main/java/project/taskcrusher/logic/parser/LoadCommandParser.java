package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.LoadCommand;

//@@author A0127737X
/**
 * Parses the arguments for load command. Also checks if the user wants to create new file before loading.
 */
public class LoadCommandParser {

    public Command parse(String arguments) {
        String[] argElements = arguments.trim().split(" ");

        if (argElements.length == 1) {
            String filenameToLoad = argElements[0].trim();
            return new LoadCommand(filenameToLoad, !LoadCommand.IS_CREATE_NEW_FILE);
        } else if (argElements.length == 2) {
            String option = argElements[0].trim();
            if (option.equals("new")) {
                String filenameToLoad = argElements[1].trim();
                return new LoadCommand(filenameToLoad, LoadCommand.IS_CREATE_NEW_FILE);
            }
        }

        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }
}
