package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.primitives.Ints;

import edu.emory.mathcs.backport.java.util.Collections;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.UnmarkCommand;

//@@author A0141077L-reused
/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser {
    private static final Integer NEGATIVE_NUMBER = -1;
    int[] filteredTaskListIndices;

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     */
    public Command parse(String args) {
        try {
            checkIndexFound(args);
            getOptionalIntArrayFromString(args);
            checkValidIndices();
            checkForDuplicateIndices();
            sortIntArray();
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        return new UnmarkCommand(filteredTaskListIndices);
    }

    private void checkIndexFound(String args) throws IllegalValueException {
        if (args.isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
    }

    private void getOptionalIntArrayFromString(String args) {
        String[] indicesInStringArray = args.split("\\s+");
        this.filteredTaskListIndices = new int[indicesInStringArray.length];

        //Sets index as NEGATIVE_NUMBER if it is not a positive unsigned integer
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            Optional<Integer> optionalIndex = ParserUtil.parseIndex(indicesInStringArray[i]);
            filteredTaskListIndices[i] = optionalIndex.orElse(NEGATIVE_NUMBER);
        }
    }

    private void checkValidIndices() throws IllegalValueException {
        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            if (filteredTaskListIndices[i] == NEGATIVE_NUMBER) {
                throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                        UnmarkCommand.MESSAGE_USAGE);
            }
        }
    }

    private void checkForDuplicateIndices() throws IllegalValueException {
        List<Integer> indicesAsInteger = Ints.asList(filteredTaskListIndices);
        Set<Integer> indicesHashSet = new HashSet<Integer>();
        for (Integer index : indicesAsInteger) {
            if (!indicesHashSet.add(index)) {
                throw new IllegalValueException(UnmarkCommand.MESSAGE_DUPLICATE_INDICES);
            }
        }
    }

    private void sortIntArray() {
        List<Integer> tempIndicesList = Ints.asList(filteredTaskListIndices);
        Collections.sort(tempIndicesList, comparator);
        filteredTaskListIndices = Ints.toArray(tempIndicesList);
    }

    // Comparator to sort list in descending order
    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    };

}
