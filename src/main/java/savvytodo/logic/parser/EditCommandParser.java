package savvytodo.logic.parser;

import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static savvytodo.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static savvytodo.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static savvytodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static savvytodo.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.logic.commands.Command;
import savvytodo.logic.commands.EditCommand;
import savvytodo.logic.commands.EditCommand.EditTaskDescriptor;
import savvytodo.logic.commands.IncorrectCommand;
import savvytodo.model.category.UniqueCategoryList;

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
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_DESCRIPTION, PREFIX_ADDRESS, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setPriority(ParserUtil.parsePhone(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setDescription(ParserUtil.parseEmail(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
            editTaskDescriptor.setAddress(ParserUtil.parseAddress(argsTokenizer.getValue(PREFIX_ADDRESS)));
            editTaskDescriptor.setCategories(
                    parseCategoriesForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into an
     * {@code Optional<UniqueCategoryList>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string,
     * it will be parsed into a {@code Optional<UniqueCategoryList>} containing
     * zero categories.
     */
    private Optional<UniqueCategoryList> parseCategoriesForEdit(Collection<String> categories)
            throws IllegalValueException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1 && categories.contains("") ? Collections.emptySet()
                : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
