package seedu.onetwodo.logic.parser;

import seedu.onetwodo.logic.commands.SaveToCommand;

//@@author A0139343E
/**
 * Parses input arguments and creates a new object related to file transfer
 * File transfer objects include save to, export and import
 */
public class FileTransferCommandParser {

    public static final String EMPTY_INPUT = "";
    public static final String EMPTY_WINDOW_PATH = "\\";
    public static final String EMPTY_MAC_PATH = "/";
    public static final String XML_EXTENSION = ".xml";
    public static final String PATH_SPLIT_REGEX = "\\s+";
    public static final int SIZE_ONE = 1;
    public static final int SIZE_TWO = 2;
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = SIZE_ONE;

    protected boolean isXmlFormat(String args) {
        String subString = args.substring(args.length() - 4);
        return subString.equals(XML_EXTENSION);
    }

    protected boolean isOverWrittingFormat(String args) {
        return args.equalsIgnoreCase(SaveToCommand.COMMAND_WORD_OVERWRITE);
    }

    protected boolean isValidPath(String args) {
        return !args.equals(EMPTY_WINDOW_PATH) &&
                !args.equals(EMPTY_MAC_PATH) &&
                args.length() > XML_EXTENSION.length() &&
                isXmlFormat(args);
    }

}
