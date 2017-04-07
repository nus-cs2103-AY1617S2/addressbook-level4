package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_LOCATION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_OPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.EditCommand;
import project.taskcrusher.logic.commands.EditCommand.EditTaskDescriptor;
import project.taskcrusher.logic.commands.EditEventCommand;
import project.taskcrusher.logic.commands.EditEventCommand.EditEventDescriptor;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Task;

//@@author A0163962X
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {
    private static final Pattern EDIT_COMMAND_FORMAT = Pattern.compile("(?<flag>[te])(?<name>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_DATE, PREFIX_LOCATION,
                PREFIX_DESCRIPTION, PREFIX_TAG, PREFIX_OPTION);
        argsTokenizer.tokenize(args);
        final String option = ParserUtil.setValue(argsTokenizer, PREFIX_OPTION, Parser.NO_OPTION);

        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 3);

        try {
            if (preambleFields.get(0).get().equals(Task.TASK_FLAG)) {

                Optional<Integer> index = preambleFields.get(1).flatMap(ParserUtil::parseIndex);
                if (!index.isPresent()) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }

                EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
                try {
                    editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(2)));
                    editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
                    editTaskDescriptor.setDeadline(ParserUtil.parseDeadline(argsTokenizer.getValue(PREFIX_DATE)));
                    editTaskDescriptor
                            .setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
                    editTaskDescriptor
                            .setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }

                if (!editTaskDescriptor.isAnyFieldEdited()) {
                    return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
                }

                return new EditCommand(index.get(), editTaskDescriptor);

            } else if (preambleFields.get(0).get().equals(Event.EVENT_FLAG)) {

                Optional<Integer> index = preambleFields.get(1).flatMap(ParserUtil::parseIndex);
                if (!index.isPresent()) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }

                EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
                try {
                    editEventDescriptor.setName(ParserUtil.parseName(preambleFields.get(2)));
                    editEventDescriptor.setLocation(ParserUtil.parseLocation(argsTokenizer.getValue(PREFIX_LOCATION)));
                    editEventDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
                    editEventDescriptor.setTimeslots(ParserUtil.parseTimeslots(argsTokenizer.getValue(PREFIX_DATE)));
                    editEventDescriptor
                            .setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
                    editEventDescriptor
                            .setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }

                if (!editEventDescriptor.isAnyFieldEdited()) {
                    return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
                }

                EditEventCommand edited = new EditEventCommand(index.get(), editEventDescriptor);
                if (option.equals(Parser.FORCE_OPTION)) {
                    edited.force = true;
                }
                return edited;

            } else {
                throw new IllegalValueException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    // @@author A0163962X-reused
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
