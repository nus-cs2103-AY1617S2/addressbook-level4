//@@author A0163720M
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.SaveFileCommand;
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
