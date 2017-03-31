package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_XML_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.FILEPATH_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.MoveCommand;

// @@author A0114269E
/**
 * Parses input arguments and creates a new MoveCommand object
 */
public class MoveCommandParser {
    public static final String XML_FILE_EXT = "xml";
    /**
     * Parses the given {@code String} of path in the context of the MoveCommand
     * and returns a MoveCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = FILEPATH_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }
        if (!isValidXmlPath(args)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_XML_FORMAT, MoveCommand.MESSAGE_USAGE));
        }
        return new MoveCommand(args.trim());
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
