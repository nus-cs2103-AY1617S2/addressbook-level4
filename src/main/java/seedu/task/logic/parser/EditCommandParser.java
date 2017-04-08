package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;
/*
import static seedu.task.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.task.logic.parser.CliSyntax.PREFIX_ADDRESS;
 */

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.task.logic.parser.ParserUtil;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.task.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
	assert args != null;
	ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME,
		PREFIX_DESCRIPTION, PREFIX_TAG);
	argsTokenizer.tokenize(args);
	List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

	Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
	}

	EditTaskDescriptor editPersonDescriptor = new EditTaskDescriptor();
	try {
	    editPersonDescriptor.setTaskName(ParserUtil.parseTaskName(preambleFields.get(1)));
	    if (argsTokenizer.getValue(PREFIX_DATE).isPresent()) {
		    if (argsTokenizer.getValue(PREFIX_DATE).get().trim().equals("")) {
		    	editPersonDescriptor.setTaskDate(null);
		    } else {
		    	editPersonDescriptor.setTaskDate(ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_DATE)));
		    }
		}
	    if (argsTokenizer.getValue(PREFIX_START_TIME).isPresent()) {
		    if (argsTokenizer.getValue(PREFIX_START_TIME).get().trim().equals("")) {
		    	editPersonDescriptor.setTaskStartTime(null);
		    } else {
		    	editPersonDescriptor.setTaskStartTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_START_TIME)));
		    }
	    }
	    if (argsTokenizer.getValue(PREFIX_END_TIME).isPresent()) {
		    if (argsTokenizer.getValue(PREFIX_END_TIME).get().trim().equals("")) {
		    	editPersonDescriptor.setTaskEndTime(null);
		    } else {
		    	editPersonDescriptor.setTaskEndTime(ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_END_TIME)));
		    }
	    }
	    editPersonDescriptor.setTaskDescription(ParserUtil.parseString(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
	    editPersonDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}

	if (!editPersonDescriptor.isAnyFieldEdited()) {
	    return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
	}

	return new EditCommand(index.get(), editPersonDescriptor);
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
