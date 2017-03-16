package seedu.bulletjournal.logic.parser;

/**
 * Provides variations of commands
 * Note: hard-coded for v0.2, will implement nlp for future versions
 * @author Tu An - arishuynhvan
 */
import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FlexibleCommand {

	private String commandFromUser ="";

	private String[] commandGroups = new String[] {
			"add a create new",
			"clear clr c empty",
			"delete d del remove rm",
			"edit e change ",
			"exit close logout",
			"find f search lookup",
			"help h manual instruction instructions",
			"list l ls show display showall",
			"select s choose",
	};

	/**
	 * Constructor must take in a valid string input
	 * @param cfu
	 */
	public FlexibleCommand(String cfu){
		commandFromUser = cfu;
	}

	public String getCommandWord(){
		for (String commandGroup: commandGroups){
			for (String command: commandGroup.split(" ")){
				if (commandFromUser == command)
					return commandGroup.split(" ")[0];
			}
		}
		return commandFromUser;
	}

	public boolean isValidCommand(){
		for (String commandGroup: commandGroups){
			for (String command: commandGroup.split(" ")){
				if (commandFromUser == command)
					return true;
			}
		}
		return false;
	}
}
