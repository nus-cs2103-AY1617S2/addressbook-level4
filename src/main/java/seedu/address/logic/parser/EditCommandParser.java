package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FIELDWORD_TITLE;
import static seedu.address.logic.parser.CliSyntax.FIELDWORD_TAG;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    //public Command parse(String args) {
        //assert args != null;
        //ArgumentTokenizer argsTokenizer =
        //        new ArgumentTokenizer(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_TAG);
        //argsTokenizer.tokenize(args);
        
        //List<Optional<String>> preambleFields = ParserUtil.splitPreamble(prepareArgument(args).orElse(""), 2);

        //Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        //if (!index.isPresent()) {
        //   return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        //}

        //EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        //try {
        //    editTaskDescriptor.setTitle(ParserUtil.parseTitle(preambleFields.get(1)));
        //    editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        //} catch (IllegalValueException ive) {
        //    return new IncorrectCommand(ive.getMessage());
        //}

        //if (!editTaskDescriptor.isAnyFieldEdited()) {
        //   return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        //}

        //return new EditCommand(index.get(), editTaskDescriptor);
    //}
    
    public Command parse(String args) {
        assert args != null;
        List<Optional<String>> editInformation = ParserUtil.splitArgument(args.trim(),3);
        
        Optional<Integer> index = editInformation.get(0).flatMap(ParserUtil::parseIndex);
        Optional<String> fieldWord = editInformation.get(1);
        Optional<String> updateInformation = editInformation.get(2);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            String string = fieldWord.get();
            if (FIELDWORD_TITLE.equals(string)) {
                editTaskDescriptor.setTitle(ParserUtil.parseTitle(updateInformation));
            }
            if (FIELDWORD_TAG.equals(string)) {
                editTaskDescriptor.setTags(parseTagsForEdit(separateTags(updateInformation)));
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
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
