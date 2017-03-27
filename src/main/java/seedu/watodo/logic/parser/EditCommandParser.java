package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;

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
        Optional<Integer> index = ParserUtil.parseIndex(args.split(" ", 2)[0]);
        args = args.split(" ", 2)[1];
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        //check if user wants to remove dates of task
        if (args.equals("REMOVEDATES")) {
            hasRemoveDate = true;
            return new EditCommand(index.get(), editTaskDescriptor, hasEditDate, hasRemoveDate);
        }

        try {
            // get any dates arguments entered by the user
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parse(args);
            String argsWithDatesExtracted = dateTimeParser.trimArgsOfDates(args);
            if (dateTimeParser.getStartDate() != null || dateTimeParser.getEndDate() != null) {
                hasEditDate = true;
                if (dateTimeParser.getStartDate() != null) {
                    editTaskDescriptor.setStartDate(Optional.of(new DateTime(dateTimeParser.getStartDate())));
                }
                editTaskDescriptor.setEndDate(Optional.of(new DateTime(dateTimeParser.getEndDate())));
            }

            // get any tags arguments entered by the user
            ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
            argsTokenizer.tokenize(argsWithDatesExtracted);
            if (argsTokenizer.getAllValues(PREFIX_TAG).isPresent()) {
                List<String> tags = argsTokenizer.getAllValues(PREFIX_TAG).get();
                List<String> parsedTags = new ArrayList<String>();
                for (String tag : tags) {
                    parsedTags.add(tag.split("[\\s+]", 2)[0]);  //tag name is only until the first whitespace
                }
                editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(Optional.of(parsedTags))));
            }

            //get any new task description entered by the user
            String tagArgs = String.format(AddCommandParser.EXTRACT_ARGS_REGEX, PREFIX_TAG.getPrefix() + "(\\S+)", "");
            Optional<String> description;
            String argsWithDatesAndTagsExtracted = argsWithDatesExtracted.replaceAll(tagArgs, " ").trim();
            if (argsWithDatesAndTagsExtracted.equals("")) {
                description = Optional.empty();
            } else {
                description = Optional.of(argsWithDatesAndTagsExtracted);
            }
            editTaskDescriptor.setTaskName(ParserUtil.parseDescription(description));

            if (!editTaskDescriptor.isAnyFieldEdited()) {
                return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
            }
            return new EditCommand(index.get(), editTaskDescriptor, hasEditDate, hasRemoveDate);

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
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
