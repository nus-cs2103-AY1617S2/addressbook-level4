package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_XML_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.FILEPATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.taskmanager.logic.commands.LoadCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

// @@author A0114269E
/**
 * Parses input arguments and creates a new ChangeDirectoryCommand object
 */
public class LoadCommandParser {
    public static final String XML_FILE_EXT = "xml";
    /**
     * Parses the given {@code String} of path in the context of the ChangeDirectoryCommand
     * and returns a ChangeDirectoryCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = FILEPATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }
        if (!isValidXmlPath(args)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_XML_FORMAT, LoadCommand.MESSAGE_USAGE));
        }
        return new LoadCommand(args.trim());
    }

    /**
     * Checks if the given file path is a path to an XML file
     */
    public boolean isValidXmlPath (String args) {
        String filePath = args.trim();
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if (extension.equals(XML_FILE_EXT)) {
            return true;
        }
        return false;
    }
}
