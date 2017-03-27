package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import javafx.util.Pair;
import todolist.logic.commands.Command;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    // @@ A0143648Y
    private static Optional<Pair<Character, Integer>> index;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        Optional<Pair<Character, Integer>> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            index = DeleteCommandParser.index;
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }

        return new DeleteCommand(index.get());
    }

    public static void setIndex(Pair<Character, Integer> index) {
        DeleteCommandParser.index = Optional.of(index);
    }
}
