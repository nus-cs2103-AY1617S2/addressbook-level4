package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_START_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_END_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_LOCATION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * AddCommand and returns an AddCommand object for execution.
	 */
	public Command parse(String args) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DEADLINE,
				PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_RECURRENCE, PREFIX_TAG);
		argsTokenizer.tokenize(args);
		try {
			HashMap<String, Object> addParam = new HashMap<>();
			addParam.put("name", argsTokenizer.getPreamble().get());
			addParam.put("location", argsTokenizer.getValue(PREFIX_LOCATION).orElse(null));
			addParam.put("start", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_START_TIME)).orElse(null));
			addParam.put("end", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_END_TIME)).orElse(null));
			addParam.put("deadline", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_DEADLINE)).orElse(null));
			addParam.put("description", argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse(null));
			addParam.put("tag", ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
			addParam.put("recurrence", argsTokenizer.getValue(PREFIX_RECURRENCE).orElse(null));
			return new AddCommand(addParam);
		} catch (NoSuchElementException nsee) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}

}
