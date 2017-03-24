package seedu.bulletjournal.logic.parser;

/**
 * Provides variations of commands Note: hard-coded for v0.2, will implement nlp
 * for future versions
 * @author A0127826Y
 */

public class FlexibleCommand {
    private String commandFromUser = "";
    private String[] commandGroups = new String[] { "add a adds create creates new", "clear clr c clears empty empties",
        "delete d deletes del remove removes rm", "edit edits e change changes",
        "exit exits close closes logout logouts quit q", "find finds f search searches lookup", "show showall",
        "help helps h manual instruction instructions", "list lists l ls display displays",
        "select selects s choose chooses" };

    /**
     * Constructor must take in a valid string input
     * @param cfu
     */
    public FlexibleCommand(String cfu) {
        commandFromUser = cfu;
    }

    /**
     * @return the correct command string that matches COMMAND_WORD of command
     *         classes
     */
    public String getCommandWord() {
        for (String commandGroup : commandGroups) {
            for (String command : commandGroup.split(" ")) {
                if (commandFromUser.equals(command)) {
                    return commandGroup.split(" ")[0];
                }
            }
        }
        return commandFromUser;
    }

    public boolean isValidCommand() {
        for (String commandGroup : commandGroups) {
            for (String command : commandGroup.split(" ")) {
                if (commandFromUser.equals(command)) {
                    return true;
                }
            }
        }
        return false;
    }
}
