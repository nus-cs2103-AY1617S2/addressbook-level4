package seedu.onetwodo.logic.parser;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import seedu.onetwodo.logic.commands.SaveToCommand;

//@@author A0139343E
/**
 * Parses input arguments and creates a new object related to file transfer
 * File transfer objects include save to, export and import
 */
public class FileTransferCommandParser {

    public static final String EMPTY_INPUT = "";
    public static final String XML_EXTENSION = ".xml";
    public static final String PATH_SPLIT_REGEX = "\\s+";
    public static final String INVALID_FILE_NAME_REGEX = "[~#@*+%{}<>\\[\\]|\"_^]";
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
        return  args.length() > XML_EXTENSION.length() &&
                isXmlFormat(args);
    }

//@@author A0139343E-unused
/*
    protected boolean hasInvalidName(String args) {
        String subString = args.substring(0,  args.length() - 4);
        Pattern pattern = Pattern.compile("INVALID_FILE_NAME_REGEX");
        Matcher matcher = pattern.matcher(subString);
        return matcher.find();
    }
*/
}
