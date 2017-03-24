package todolist.logic.parser;

import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static todolist.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static todolist.logic.parser.CliSyntax.PREFIX_TAG;
import static todolist.logic.parser.CliSyntax.PREFIX_URGENCYLEVEL;
import static todolist.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import todolist.commons.exceptions.IllegalValueException;
import todolist.logic.commands.Command;
import todolist.logic.commands.EditCommand;
import todolist.logic.commands.EditCommand.EditTaskDescriptor;
import todolist.logic.commands.IncorrectCommand;
import todolist.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private static Optional<Pair<Character, Integer>> index;

    // @@ A0143648Y
    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_VENUE, PREFIX_STARTTIME, PREFIX_ENDTIME,
                PREFIX_URGENCYLEVEL, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Pair<Character, Integer>> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            index = EditCommandParser.index;
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }
        // @@
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
            editTaskDescriptor.setVenue(ParserUtil.parseVenue(argsTokenizer.getValue(PREFIX_VENUE)));
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
            editTaskDescriptor.setEndTime(ParserUtil.parseEndTime(argsTokenizer.getValue(PREFIX_ENDTIME)));
            editTaskDescriptor
                    .setUrgencyLevel(ParserUtil.parseUrgencyLevel(argsTokenizer.getValue(PREFIX_URGENCYLEVEL)));
            editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
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

    public static void setIndex(Pair<Character, Integer> index) {
        EditCommandParser.index = Optional.of(index);
    }
}
