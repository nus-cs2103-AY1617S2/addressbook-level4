//@@author A0139925U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.logic.parser.CliSyntax.EDIT_PARAMETER_DELIMITER;
import static seedu.tache.logic.parser.CliSyntax.END_DATE_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.END_DATE_PARAMETER_2;
import static seedu.tache.logic.parser.CliSyntax.END_DATE_PARAMETER_3;
import static seedu.tache.logic.parser.CliSyntax.END_TIME_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.END_TIME_PARAMETER_2;
import static seedu.tache.logic.parser.CliSyntax.END_TIME_PARAMETER_3;
import static seedu.tache.logic.parser.CliSyntax.NAME_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.NAME_PARAMETER_2;
import static seedu.tache.logic.parser.CliSyntax.PARAMETER_DELIMITER;
import static seedu.tache.logic.parser.CliSyntax.START_DATE_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.START_DATE_PARAMETER_2;
import static seedu.tache.logic.parser.CliSyntax.START_DATE_PARAMETER_3;
import static seedu.tache.logic.parser.CliSyntax.START_TIME_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.START_TIME_PARAMETER_2;
import static seedu.tache.logic.parser.CliSyntax.START_TIME_PARAMETER_3;
import static seedu.tache.logic.parser.CliSyntax.TAG_PARAMETER;
import static seedu.tache.logic.parser.CliSyntax.TAG_PARAMETER_2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Name;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    public static final String MESSAGE_INVALID_PARAMETER = "Invalid parameter given. Valid parameters" +
                                                   " include name, start_date, start_time, end_date, end_time and tags";

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

        for (int i = 1; i < preambleFields.length; i++) {
            try {
                String updateParameter = preambleFields[i].substring(0, preambleFields[i].replaceAll("^\\s+", "")
                                         .indexOf(EDIT_PARAMETER_DELIMITER) + 1).trim();
                String updateValue = preambleFields[i].substring(preambleFields[i].replaceAll("^\\s+", "")
                                     .indexOf(EDIT_PARAMETER_DELIMITER) + 1).trim();

                switch(updateParameter) {
                case NAME_PARAMETER:
                case NAME_PARAMETER_2:
                    editTaskDescriptor.setName(Optional.of(new Name(updateValue)));
                    break;
                case START_DATE_PARAMETER:
                case START_DATE_PARAMETER_2:
                case START_DATE_PARAMETER_3:
                    editTaskDescriptor.setStartDate(Optional.of(updateValue));
                    break;
                case END_DATE_PARAMETER:
                case END_DATE_PARAMETER_2:
                case END_DATE_PARAMETER_3:
                    editTaskDescriptor.setEndDate(Optional.of(updateValue));
                    break;
                case START_TIME_PARAMETER:
                case START_TIME_PARAMETER_2:
                case START_TIME_PARAMETER_3:
                    editTaskDescriptor.setStartTime(Optional.of(updateValue));
                    break;
                case END_TIME_PARAMETER:
                case END_TIME_PARAMETER_2:
                case END_TIME_PARAMETER_3:
                    editTaskDescriptor.setEndTime(Optional.of(updateValue));
                    break;
                case TAG_PARAMETER:
                case TAG_PARAMETER_2:
                    editTaskDescriptor.setTags(parseTagsForEdit(Arrays.asList(updateValue
                                                                              .split(EDIT_PARAMETER_DELIMITER))));
                    break;
                default:
                    throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
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
