package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_START_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_END_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_LOCATION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_TAG;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_TIME_MULTIPLE;
import static org.teamstbf.yats.model.item.Event.MESSAGE_TOO_MANY_TIME;
import static org.teamstbf.yats.logic.parser.ParserUtil.SIZE_DEADLINE_TASK;
import static org.teamstbf.yats.logic.parser.ParserUtil.SIZE_EVENT_TASK;
import static org.teamstbf.yats.logic.parser.ParserUtil.INDEX_FIRST_DATE;
import static org.teamstbf.yats.logic.parser.ParserUtil.INDEX_SECOND_DATE;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;

//@@author A0116219L
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

	public static final String MESSAGE_ADD_CONFLICTING_TIME = "Either -s/-e/-by OR -time should be used in add command.";
	public static final String MESSAGE_INCORRECT_TIME_SLOTS = "There can only be 1 or 2 time points after -time flag.";
    /**
	 * Parses the given {@code String} of arguments in the context of the
	 * AddCommand and returns an AddCommand object for execution.
	 */
	public Command parse(String args) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DEADLINE,
				PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_RECURRENCE, PREFIX_TAG, PREFIX_TIME_MULTIPLE);
		argsTokenizer.tokenize(args);
		try {
			HashMap<String, Object> addParam = new HashMap<>();
			addParam.put("name", argsTokenizer.getPreamble().get());
			addParam.put("location", argsTokenizer.getValue(PREFIX_LOCATION).orElse(null));
			addParam.put("start", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_START_TIME)).orElse(null));
			addParam.put("end", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_END_TIME)).orElse(null));
			addParam.put("deadline", ParserUtil.parseDateSingle(argsTokenizer.getValue(PREFIX_DEADLINE)).orElse(null));
			if ((ParserUtil.parseDateList(argsTokenizer.getValue(PREFIX_TIME_MULTIPLE)).isPresent())) {
			    if (hasConflictingTimes(addParam)) {
			        throw new IllegalValueException(MESSAGE_ADD_CONFLICTING_TIME);
			    } else {
			        fillTimeParameters(ParserUtil.parseDateList(argsTokenizer.getValue(PREFIX_TIME_MULTIPLE)).get(), addParam);
			    }
			}
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

    /*
     * Puts date contents inside list into addParam "start", "end", or "deadline"
     */
	private void fillTimeParameters(List<Date> list, HashMap<String, Object> addParam) throws IllegalValueException {
        if (list.size() > SIZE_EVENT_TASK) {
            throw new IllegalValueException(MESSAGE_TOO_MANY_TIME);
        }
        if (list.size() == SIZE_DEADLINE_TASK) {
            addParam.put("deadline", list.get(INDEX_FIRST_DATE));
        } else if (list.size() == SIZE_EVENT_TASK) {
            addParam.put("start", list.get(INDEX_FIRST_DATE));
            addParam.put("end", list.get(INDEX_SECOND_DATE));
        } else {
            throw new IllegalValueException(MESSAGE_INCORRECT_TIME_SLOTS);
        }
    }

    private boolean hasConflictingTimes(HashMap<String, Object> addParam) {
        if (addParam.get("start") != null 
                || addParam.get("end") != null
                || addParam.get("deadline") != null) {
            return true;
        }
        return false;
    }

}
