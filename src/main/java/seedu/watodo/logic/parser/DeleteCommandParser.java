package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.google.common.primitives.Ints;

import edu.emory.mathcs.backport.java.util.Collections;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.DeleteCommand;
import seedu.watodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        String[] indicesInString = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInString.length];

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            // To convert string array to int array
            try {
                filteredTaskListIndices[i] = Integer.parseInt(indicesInString[i]);
            } catch (NumberFormatException nfe) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            // To check if indices are valid
            Optional<Integer> index = ParserUtil.parseIndex(indicesInString[i]);
            if (!index.isPresent()) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }

        // To sort int array
        List<Integer> list = Ints.asList(filteredTaskListIndices);
        Collections.sort(list, comparator);
        filteredTaskListIndices = Ints.toArray(list);

        return new DeleteCommand(filteredTaskListIndices);
    }

    // Comparator to sort list in descending order
    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    };

}
