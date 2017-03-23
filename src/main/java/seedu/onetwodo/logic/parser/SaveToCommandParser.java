package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;

/**
 * Parses input arguments and creates a new SaveTo object
 */
public class SaveToCommandParser {

    public static String EMPTY_INPUT = "";
    public static String EMPTY_WINDOW_PATH = "\\";
    public static String EMPTY_MAC_PATH = "/";
    public static String XML_EXTENSION = ".xml";

    
    /**
     * Parses the given {@code String} of arguments in the context of the SaveToCommand
     * and returns an SaveToCommand object for execution.
     */
    public Command parse(String argument) {
        assert argument != null;
        String args = argument.trim().toLowerCase();
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (args.equals(EMPTY_WINDOW_PATH)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (args.equals(EMPTY_MAC_PATH)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (args.length() <= XML_EXTENSION.length()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (!isXmlFormat(args)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else {
            return new SaveToCommand(args);
        }
    }


    private boolean isXmlFormat(String args) {
        String subString = args.substring(args.length() - 4);
        return subString.equals(XML_EXTENSION);
    }

}
