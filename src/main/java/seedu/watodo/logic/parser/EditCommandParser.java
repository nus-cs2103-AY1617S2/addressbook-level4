package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.ParserUtil.INDEX_FIRST_ARG;
import static seedu.watodo.logic.parser.ParserUtil.INDEX_SECOND_ARG;
import static seedu.watodo.logic.parser.ParserUtil.NUMFIELD_SPLIT_BY_WHITESPACE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // get index of the task to edit
        List<Optional<String>> argsFields = ParserUtil.splitArgs(args, NUMFIELD_SPLIT_BY_WHITESPACE);
        Optional<Integer> index = argsFields.get(INDEX_FIRST_ARG).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

         //checks if the args format by the user is correct
        if (argsFields.get(INDEX_SECOND_ARG).isPresent()) {
            args = argsFields.get(INDEX_SECOND_ARG).get();
        } else {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        //check if user wants to remove dates of task
        final Pattern KEYWORD_REMOVEDATES = Pattern.compile("\\bREMOVEDATES\\b");
        Matcher matcher = KEYWORD_REMOVEDATES.matcher(args);

        if (matcher.find()) {
            System.out.println("found: " + " : "
                    + matcher.start() + " - " + matcher.end());
            hasRemoveDate = true;
            args = matcher.replaceFirst(ParserUtil.WHITESPACE);
            //REMOVEDATES should only be typed at most once in the args
            if (matcher.find()) {
                return new IncorrectCommand(DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
            }
        }

        try {
            // get any dates arguments entered by the user
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parse(args);

            // get any tags arguments entered by the user
            String argsWithDatesExtracted = dateTimeParser.getUnparsedArgs();
            TagsParser tagsParser = new TagsParser();
            tagsParser.parse(argsWithDatesExtracted);

            //get any new task description entered by the user
            String argsWithDatesAndTagsExtracted = tagsParser.getUnparsedArgs();

            editTaskDescriptor = setArguments(dateTimeParser.getStartDate(), dateTimeParser.getEndDate(),
                    tagsParser.getTags(), argsWithDatesAndTagsExtracted, editTaskDescriptor);

            if (editTaskDescriptor.getStartDate().isPresent() || editTaskDescriptor.getEndDate().isPresent()) {
                hasEditDate = true;
                if (hasRemoveDate) {
                    throw new IllegalValueException(DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
                }
            }

            return new EditCommand(index.get(), editTaskDescriptor, hasEditDate, hasRemoveDate);

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    /**
     * Sets the startDate, endDate, tags and description in the editTaskDescriptor
     * Parameters are set to {@code Optional.empty()} if the user does not input them
     */
    private EditTaskDescriptor setArguments(Optional<String> startDate, Optional<String> endDate,
            Set<String> tags, String description, EditTaskDescriptor editTaskDescriptor)
            throws IllegalValueException {
        editTaskDescriptor.setStartDate(ParserUtil.parseDateTime(startDate));
        editTaskDescriptor.setEndDate(ParserUtil.parseDateTime(endDate));

        editTaskDescriptor.setTags(parseTagsForEdit(tags));

        if (description.isEmpty()) {
            editTaskDescriptor.setTaskName(ParserUtil.parseDescription(Optional.empty()));
        } else {
            editTaskDescriptor.setTaskName(ParserUtil.parseDescription(Optional.of(description)));
        }
        return editTaskDescriptor;
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
