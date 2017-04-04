//@@author A0131125Y
package seedu.toluist.controller;

import java.util.Map;

import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.InvalidCommandException;

/**
 * UnknownCommandController is responsible for rendering the initial UI
 */
public class UnknownCommandController extends Controller {

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        throw new InvalidCommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    public Map<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return true; // matches everything
    }
}
