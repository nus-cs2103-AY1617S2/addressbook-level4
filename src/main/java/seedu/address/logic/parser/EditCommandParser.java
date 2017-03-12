package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.label.UniqueLabelList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE, CliSyntax.PREFIX_TIMEINTERVAL_START, CliSyntax.PREFIX_TIMEINTERVAL_END, PREFIX_LABEL);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setDeadline(ParserUtil.parseAddress(argsTokenizer.getValue(PREFIX_DEADLINE)));
            editTaskDescriptor.setLabels(parseLabelsForEdit(ParserUtil.toSet(
                    argsTokenizer.getAllValues(PREFIX_LABEL))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalDateTimeValueException ipve) {
            return new IncorrectCommand(ipve.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> labels} into an {@code Optional<UniqueTagList>} if {@code labels} is non-empty.
     * If {@code labels} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero labels.
     */
    private Optional<UniqueLabelList> parseLabelsForEdit(Collection<String> labels) throws IllegalValueException {
        assert labels != null;

        if (labels.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> labelSet = labels.size() == 1 && labels.contains("") ? Collections.emptySet() : labels;
        return Optional.of(ParserUtil.parseLabels(labelSet));
    }

}
