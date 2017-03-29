package seedu.watodo.logic.parser;

import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.SaveAsCommand;

/**
 * Parses input arguments and creates a new SaveAsCommand object
 */
public class SaveAsCommandParser {

    //TODO want? String newFilepath;

    /**
     * Parses the given {@code String} of arguments in the context of the SaveAsCommand
     * and returns an SaveAsCommand object for execution.
     */
    public Command parse(String newFilePath) {
        newFilePath.trim();

        if (!newFilePath.endsWith(".xml")) {
            return new IncorrectCommand(SaveAsCommand.MESSAGE_INVALID_FILE_PATH_FORMAT);
        }

        /*
        ///TODO below from find
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(newFilepath.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
         */

        return new SaveAsCommand(newFilePath);
    }

}
