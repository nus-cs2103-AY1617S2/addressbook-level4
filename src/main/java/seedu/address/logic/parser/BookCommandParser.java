package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import java.util.NoSuchElementException;

import seedu.address.logic.commands.BookCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

//@@author A0162877N
/**
* Parses input arguments and creates a new BookCommand object
*/
public class BookCommandParser extends Parser {

    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_BOOK_DATE, PREFIX_LABEL);
        argsTokenizer.tokenize(args);
        try {
            String title = argsTokenizer.getPreamble().get();
            return new BookCommand(title,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)),
                    argsTokenizer.getValue(PREFIX_BOOK_DATE).get().split(","));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookCommand.MESSAGE_USAGE));
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
