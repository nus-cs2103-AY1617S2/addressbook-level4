package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_NLP_DESCRIPTION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_NLP_LOCATION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_NLP_TAG;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_NLP_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_NLP_RECURRENCE;

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
	ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_NLP_TIME, PREFIX_NLP_LOCATION, PREFIX_NLP_TAG,
		PREFIX_NLP_DESCRIPTION, PREFIX_NLP_RECURRENCE);
	argsTokenizer.tokenize(args);
	try {
	    HashMap<String, Object> addParam = new HashMap<>();
	    addParam.put("name", argsTokenizer.getPreamble().get());
	    addParam.put("location", argsTokenizer.getValue(PREFIX_NLP_LOCATION).orElse(null));
	    addParam.put("time", ParserUtil.parseDateTime(argsTokenizer.getValue(PREFIX_NLP_TIME).orElse(null)));
	    addParam.put("description", argsTokenizer.getValue(PREFIX_NLP_DESCRIPTION).orElse(null));
	    addParam.put("tag", ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_NLP_TAG)));
	    addParam.put("recurrence", argsTokenizer.getValue(PREFIX_NLP_RECURRENCE).orElse(null));
	    return new AddCommand(addParam);
	} catch (NoSuchElementException nsee) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}
    }

}
