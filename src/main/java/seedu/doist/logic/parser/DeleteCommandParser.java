package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.DeleteCommand;
import seedu.doist.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        List<Optional<Integer>> optionalIndices = ParserUtil.parseIndices(args);
        for (Optional<Integer> optionalIndex : optionalIndices) {
            if (!optionalIndex.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                          DeleteCommand.MESSAGE_USAGE));
            }
        }
        int[] indices = new int[optionalIndices.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = optionalIndices.get(i).get().intValue();
        }
        return new DeleteCommand(indices);
    }
}
