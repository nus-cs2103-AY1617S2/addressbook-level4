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
    public static String PATH_SPLIT_REGEX = "\\s+";
    public static int SIZE_ONE = 1;
    public static int SIZE_TWO = 2;
    public static int INDEX_ZERO = 0;
    public static int INDEX_ONE = SIZE_ONE;

    /**
     * Parses the given {@code String} of arguments in the context of the SaveToCommand
     * and returns an SaveToCommand object for execution.
     */
    public Command parse(String argument) {
        assert argument != null;
        String args = argument.trim().toLowerCase();
        SaveToCommand command;
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        }
        // check if the number of word in the input is correct
        String[] argArray = args.split(PATH_SPLIT_REGEX);
        int argSize = argArray.length;
        if (argSize != SIZE_ONE && argSize != SIZE_TWO) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        }

        // check if overwrite exist in the string
        String overwriteWord, pathInput;
        if (argSize == SIZE_ONE) {
            pathInput = argArray[INDEX_ZERO];
            command = new SaveToCommand(pathInput);
            command.setIsOverWriting(false);
        } else {    // has 2 arguments
            pathInput = argArray[INDEX_ONE];
            overwriteWord = argArray[INDEX_ZERO];
            command = new SaveToCommand(pathInput);
            if (!isOverWrittingFormat(overwriteWord)) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SaveToCommand.MESSAGE_USAGE));
            } else {
                command.setIsOverWriting(true);
            }
        }

        // checks if path is valid
        if (pathInput.equals(EMPTY_WINDOW_PATH)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (pathInput.equals(EMPTY_MAC_PATH)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (pathInput.length() <= XML_EXTENSION.length()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else if (!isXmlFormat(pathInput)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SaveToCommand.MESSAGE_USAGE));
        } else {
            return command;
        }
    }


    private boolean isXmlFormat(String args) {
        String subString = args.substring(args.length() - 4);
        return subString.equals(XML_EXTENSION);
    }

    private boolean isOverWrittingFormat(String args) {
        return args.equalsIgnoreCase(SaveToCommand.COMMAND_WORD_OVERWRITE);
    }

}
