package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_DELIMITER;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.Duration;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Time;


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
        String[] preambleFields = args.split(PARAMETER_DELIMITER);
        Optional<Integer> index = ParserUtil.parseIndex(preambleFields[0]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        int numOfDates = ParserUtil.numOfDates(args);
        int currentDateCount = 0;
        for (int i = 0; i < preambleFields.length; i++) {
            try {
                Object fieldType = ParserUtil.determineType(preambleFields[i]);
                if (fieldType instanceof Name) {
                    editTaskDescriptor.setName(Optional.of((Name) fieldType));
                } else if (fieldType instanceof Date) {
                    if (numOfDates == 2) {
                        if (currentDateCount == 0) {
                            editTaskDescriptor.setStartDate(Optional.of((Date) fieldType));
                            currentDateCount++;
                        } else {
                            editTaskDescriptor.setEndDate(Optional.of((Date) fieldType));
                        }
                    } else {
                        //Incomplete implementation, might need to prompt
                        editTaskDescriptor.setEndDate(Optional.of((Date) fieldType));
                    }
                } else if (fieldType instanceof Time) {
                    editTaskDescriptor.setTime(Optional.of((Time) fieldType));
                } else if (fieldType instanceof Duration) {
                    editTaskDescriptor.setDuration(Optional.of((Duration) fieldType));
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }

        }
        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
