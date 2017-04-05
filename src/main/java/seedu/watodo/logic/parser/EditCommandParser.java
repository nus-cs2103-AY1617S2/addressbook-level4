package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.ParserUtil.INDEX_FIRST_ARG;
import static seedu.watodo.logic.parser.ParserUtil.INDEX_SECOND_ARG;
import static seedu.watodo.logic.parser.ParserUtil.NUMFIELD_SPLIT_BY_WHITESPACE;
import static seedu.watodo.logic.parser.ParserUtil.WHITESPACE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private final String KEYWORD_REMOVEDATES = "REMOVEDATES";

    //@@author A0143076J
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     *
     * @throws IllegalValueException
     */
    public Command parse(String args) throws IllegalValueException {
        assert args != null;

        boolean hasEditDate = false;
        boolean hasRemoveDate = false;
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        //checks if the args format by the user is correct
        String splitedArgs[] = args.split(WHITESPACE, NUMFIELD_SPLIT_BY_WHITESPACE);
        if (splitedArgs.length != NUMFIELD_SPLIT_BY_WHITESPACE) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE)); 
        }

        // get index of the task to edit
        Optional<Integer> index = ParserUtil.parseIndex(splitedArgs[INDEX_FIRST_ARG]);
        args = splitedArgs[INDEX_SECOND_ARG];
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        //check if user wants to remove dates of task
        if (args.equals(KEYWORD_REMOVEDATES)) {
            hasRemoveDate = true;
            return new EditCommand(index.get(), editTaskDescriptor, hasEditDate, hasRemoveDate);
        }

        try {
            // get any dates arguments entered by the user
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parse(args);
            putDates(dateTimeParser.getStartDate(), dateTimeParser.getEndDate(),
                      editTaskDescriptor, hasEditDate);

            // get any tags arguments entered by the user
            String argsWithDatesExtracted = dateTimeParser.getUnparsedArgs();
            TagsParser tagsParser = new TagsParser();
            tagsParser.parse(argsWithDatesExtracted);
            putTags(tagsParser.getTags(), editTaskDescriptor);

            //get any new task description entered by the user
            String argsWithDatesAndTagsExtracted = tagsParser.getUnparsedArgs();
            putDescription(argsWithDatesAndTagsExtracted, editTaskDescriptor);

            if (!editTaskDescriptor.isAnyFieldEdited()) {
                return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
            }

            return new EditCommand(index.get(), editTaskDescriptor, hasEditDate, hasRemoveDate);

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    /**
     * Sets the startDate and endDate in the editTaskDescriptor
     * Parameters are set to {@code Optional.empty()} if the user does not input them
     */
    private void putDates(Optional<String> startDate, Optional<String> endDate,
            EditTaskDescriptor editTaskDescriptor, boolean hasEditDate) throws IllegalValueException {
        if (startDate.isPresent() || endDate.isPresent()) {
            hasEditDate = true;
        }
        editTaskDescriptor.setStartDate(ParserUtil.parseDateTime(startDate));
        editTaskDescriptor.setEndDate(ParserUtil.parseDateTime(endDate));
    }

    /**
     * Sets the tags in the editTaskDescriptor
     * Parameters are set to {@code Optional.empty()} if the user does not input them
     */
    private void putTags(Set<String> tags, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        editTaskDescriptor.setTags(parseTagsForEdit(tags));
    }

    /** Sets the description in the editTaskDescriptor
     *  Parameters are set to {@code Optional.empty()} if the user does not input them
     */
    private void putDescription(String description, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        final String EMPTY_STRING = "";
        if (description.equals(EMPTY_STRING)) {
            editTaskDescriptor.setTaskName(ParserUtil.parseDescription(Optional.empty()));
        } else {
            editTaskDescriptor.setTaskName(ParserUtil.parseDescription(Optional.of(description)));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
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
