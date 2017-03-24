package seedu.onetwodo.logic.parser;


import seedu.onetwodo.logic.commands.SaveToCommand;

/**
 * Parses input arguments and creates a new object related to file transfer
 * File transfer objects include save to, export and import
 */
public class FileTransferCommandParser {

    public static String EMPTY_INPUT = "";
    public static String EMPTY_WINDOW_PATH = "\\";
    public static String EMPTY_MAC_PATH = "/";
    public static String XML_EXTENSION = ".xml";
    public static String PATH_SPLIT_REGEX = "\\s+";
    public static int SIZE_ONE = 1;
    public static int SIZE_TWO = 2;
    public static int INDEX_ZERO = 0;
    public static int INDEX_ONE = SIZE_ONE;

    protected boolean isXmlFormat(String args) {
        String subString = args.substring(args.length() - 4);
        return subString.equals(XML_EXTENSION);
    }

    protected boolean isOverWrittingFormat(String args) {
        return args.equalsIgnoreCase(SaveToCommand.COMMAND_WORD_OVERWRITE);
    }

    protected boolean isValidPath(String args) {
        return args.equals(EMPTY_WINDOW_PATH) ||
                args.equals(EMPTY_MAC_PATH) ||
                args.length() <= XML_EXTENSION.length() ||
                !isXmlFormat(args);
    }

}
