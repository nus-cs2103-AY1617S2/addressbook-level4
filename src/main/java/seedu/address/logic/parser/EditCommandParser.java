package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;

//@@author A0144422R
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser extends SeperableParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args, LogicManager logic) {
        assert args != null;
        this.args = args.trim();
        String[] indexAndArguments = this.args.split("\\s+", 2);
        if (indexAndArguments.length < 2) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        Optional<String> index = ParserUtil.parseIndex(indexAndArguments[0]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        this.args = indexAndArguments[1];
        String tags[] = getTags();
        try {
            editTaskDescriptor.setTags(parseTagsForEdit(Arrays.asList(tags)));
        } catch (IllegalValueException e1) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        // find and remove tags

        // find and remove starting time and deadline if the syntax is "<name>
        // from <starting time> to <deadline>"
        List<Date> startingTimeAndDeadline = getStartingTimeAndDeadline();
        if (startingTimeAndDeadline != null) {
            editTaskDescriptor.setStartingTime(
                    Optional.of(new DateTime(startingTimeAndDeadline.get(0))));
            editTaskDescriptor.setDeadline(
                    Optional.of(new DateTime(startingTimeAndDeadline.get(1))));
        } else {
            // find and remove starting time and deadline if the syntax is
            // "<name> due <deadline>"
            Date deadline = getDeadline();
            if (deadline != null) {
                editTaskDescriptor
                        .setDeadline(Optional.of(new DateTime(deadline)));
            }
        }
        if (!this.args.trim().equals("")) {
            try {
                editTaskDescriptor
                        .setName(Optional.of(new Name(this.args.trim())));
            } catch (IllegalValueException e) {
                return new IncorrectCommand(e.getMessage());
            }
        }
        if (!logic.isValidUIIndex(index.get())) {
            return new IncorrectCommand(
                    Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        return new EditCommand(logic.parseUIIndex(index.get()),
                editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags)
            throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("")
                ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
