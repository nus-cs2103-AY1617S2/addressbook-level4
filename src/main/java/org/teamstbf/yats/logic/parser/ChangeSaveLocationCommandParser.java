package org.teamstbf.yats.logic.parser;

import java.io.File;

import org.teamstbf.yats.logic.commands.ChangeSaveLocationCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;

// @@author A0139448U
public class ChangeSaveLocationCommandParser {

    public final static String INPUT_DEFAULT = "default";
    public final static String INVALID_SAVE_LOCATION = "Invalid save location.";
    public final static String NO_LOCATION_SPECIFIED = "Please specify a save location";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ChangeSaveLocationCommand and returns an ChangeSaveLocationCommand object
     * for execution.
     */
    public Command parse(String arguments) {
	assert arguments != null;

	arguments = arguments.trim();

	if (arguments.equals("")) {
	    return new IncorrectCommand(NO_LOCATION_SPECIFIED);
	}
	if (arguments.equals(INPUT_DEFAULT)) {
	    return new ChangeSaveLocationCommand(new File("data/YATS.xml"));
	}

	File saveLocation = new File(arguments.trim());

	if (!saveLocation.exists()) {
	    return new IncorrectCommand(INVALID_SAVE_LOCATION);
	} else {
	    if (!arguments.endsWith("/")) {
		arguments += "/";
	    }
	    File newSaveLocation = new File(arguments + "YATS.xml");
	    return new ChangeSaveLocationCommand(newSaveLocation);
	}

    }

}
