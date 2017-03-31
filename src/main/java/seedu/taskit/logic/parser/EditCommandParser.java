package seedu.taskit.logic.parser;

//@author A0141872E
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskit.logic.parser.CliSyntax.FIELDWORD_END;
import static seedu.taskit.logic.parser.CliSyntax.FIELDWORD_PRIORITY;
import static seedu.taskit.logic.parser.CliSyntax.FIELDWORD_START;
import static seedu.taskit.logic.parser.CliSyntax.FIELDWORD_TAG;
import static seedu.taskit.logic.parser.CliSyntax.FIELDWORD_TITLE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.logic.commands.IncorrectCommand;
import seedu.taskit.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Title;

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
        List<Optional<String>> editInformation = ParserUtil.splitArgument(args.trim(),3);

        if (!(editInformation.size()==3)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,EditCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = editInformation.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_NOT_EDITED));
        }

        Optional<String> fieldWord = editInformation.get(1);
        Optional<String> updateInformation = editInformation.get(2);

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            String fieldWordName = fieldWord.get();
            if (FIELDWORD_TITLE.equals(fieldWordName)) {
                if(!Title.isValidTitle(updateInformation.get())) {
                    return new IncorrectCommand(Title.MESSAGE_TITLE_CONSTRAINTS);
                }
                editTaskDescriptor.setTitle(ParserUtil.parseTitle(updateInformation));
            }
            if (FIELDWORD_TAG.equals(fieldWordName)) {
                if(!Tag.isValidTagName(updateInformation.get())) {
                    return new IncorrectCommand(Tag.MESSAGE_TAG_CONSTRAINTS);
                }
                editTaskDescriptor.setTags(parseTagsForEdit(separateTags(updateInformation)));
            }
            if (FIELDWORD_START.equals(fieldWordName)) {
                editTaskDescriptor.setStart(ParserUtil.parseDate(updateInformation));
            }
            if (FIELDWORD_END.equals(fieldWordName)) {
                editTaskDescriptor.setEnd(ParserUtil.parseDate(updateInformation));
            }
            if (FIELDWORD_PRIORITY.equals(fieldWordName)) {
                editTaskDescriptor.setPriority(ParserUtil.parsePriority(updateInformation));
            }
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
               return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
            }
        return new EditCommand(index.get(), editTaskDescriptor);
    }

    private Collection<String> separateTags(Optional<String> updateInformation) {
        List<String> separatedTags = Arrays.asList(updateInformation.get().split("s//+"));
        return separatedTags;
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

        Collection<String> tagSet = tags.size() == 1 && tags.contains("null") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
