//@@author A0163720M
package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.logic.commands.SaveFileCommand;
/**
 * Parses input arguments and creates a new SaveFileCommand object
 */
public class SaveFileCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SaveFileCommand
     * and returns a SaveFileCommand object for execution.
     * @throws ParseException
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer();
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(null), 1);
        String saveFilePath = preambleFields.get(0).get();
        File saveFile = new File(saveFilePath);

        if (!saveFile.isFile()) {
            return new IncorrectCommand(String.format(MESSAGE_FILE_NOT_FOUND));
        }

        return new SaveFileCommand(saveFilePath);
    }
}
//@@author
