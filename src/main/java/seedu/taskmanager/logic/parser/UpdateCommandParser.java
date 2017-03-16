package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.taskmanager.model.task.Date.DATE_VALIDATION_REGEX2;

//import java.util.Collection;
//import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;
//import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.UpdateCommand;
import seedu.taskmanager.logic.commands.UpdateCommand.UpdateTaskDescriptor;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UpdateCommandParser {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
	assert args != null;
	ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_DEADLINE, PREFIX_STARTTIME,
		PREFIX_ENDTIME/* , PREFIX_CATEGORY */);
	argsTokenizer.tokenize(args);
	List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

	Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
	}

	UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
	try {
	    Optional<String> taskName = preambleFields.get(1);
	    Optional<String> date = argsTokenizer.getValue(PREFIX_DATE);
	    Optional<String> deadline = argsTokenizer.getValue(PREFIX_DEADLINE);
	    Optional<String> startTime = argsTokenizer.getValue(PREFIX_STARTTIME);
	    Optional<String> endTime = argsTokenizer.getValue(PREFIX_ENDTIME);

	    if (deadline.isPresent()) {
		String stringDeadline = deadline.toString();
		String[] splited = stringDeadline.split("\\s+");
		date = Optional.of(splited[0]);
		startTime = Optional.of(splited[1]);
	    }

	    if (date.isPresent()) {
		if (date.get().matches(DATE_VALIDATION_REGEX2)) {
		    date = Optional.of(CurrentDate.getNewDate(date.get()));
		}
	    }

	    updateTaskDescriptor.setTaskName(ParserUtil.parseTaskName(taskName));
	    updateTaskDescriptor.setDate(ParserUtil.parseDate(date));
	    updateTaskDescriptor.setStartTime(ParserUtil.parseStartTime(startTime));
	    updateTaskDescriptor.setEndTime(ParserUtil.parseEndTime(endTime));

	    // updateTaskDescriptor.setCategories(parseCategoriesForUpdate(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))));
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}

	if (!updateTaskDescriptor.isAnyFieldUpdated()) {
	    return new IncorrectCommand(UpdateCommand.MESSAGE_NOT_UPDATED);
	}

	return new UpdateCommand(index.get(), updateTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    /*
     * private Optional<UniqueCategoryList>
     * parseCategoriesForUpdate(Collection<String> categories) throws
     * IllegalValueException { assert categories != null;
     * 
     * if (categories.isEmpty()) { return Optional.empty(); } Collection<String>
     * categorySet = categories.size() == 1 && categories.contains("") ?
     * Collections.emptySet() : categories; return
     * Optional.of(ParserUtil.parseCategories(categorySet)); }
     */
}
