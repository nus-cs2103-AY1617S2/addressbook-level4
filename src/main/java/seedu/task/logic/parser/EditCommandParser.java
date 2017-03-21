package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.EditCommand.EditTaskDescriptor;

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
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TASK_DATE, PREFIX_START_TIME, PREFIX_END_TIME,
				PREFIX_TASK_DESCRIPTION);
		argsTokenizer.tokenize(args);
		List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

		Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		EditTaskDescriptor editPersonDescriptor = new EditTaskDescriptor();
		try {
			editPersonDescriptor.setTaskName(ParserUtil.parseTaskName(preambleFields.get(1)));
			editPersonDescriptor.setTaskDate(ParserUtil.parseTaskDate(argsTokenizer.getValue(PREFIX_TASK_DATE)));
			editPersonDescriptor.setTaskStartTime(ParserUtil.parseTaskTime(argsTokenizer.getValue(PREFIX_START_TIME)));
			editPersonDescriptor.setTaskEndTime(ParserUtil.parseTaskTime(argsTokenizer.getValue(PREFIX_END_TIME)));
			editPersonDescriptor.setTaskDescription(
					ParserUtil.parseTaskDescription(argsTokenizer.getValue(PREFIX_TASK_DESCRIPTION)));
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}

		if (!editPersonDescriptor.isAnyFieldEdited()) {
			return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
		}

		return new EditCommand(index.get(), editPersonDescriptor);
	}

}
