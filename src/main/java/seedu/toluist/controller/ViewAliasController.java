//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.toluist.commons.core.Config;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Alias Controller is responsible for handling viewalias requests
 */
public class ViewAliasController extends Controller {
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*viewalias\\s*";
    private static final String COMMAND_WORD = "viewalias";
    private static final String COMMAND_SEPARATOR_ALIAS = ":";
    private static final String NEW_LINE = "\n";
    public static final String RESULT_MESSAGE_NO_ALIAS = "No aliases found";

    //@@author A0131125Y
    private static final String HELP_DETAILS = "Views aliases in the system.";
    private static final String HELP_FORMAT = "viewalias";
    private static final String[] HELP_COMMENTS = { "Related commands: `alias`, `unalias`",
                                                    "Lists aliases in the format `ALIAS:PHRASE`" };

  //@@author A0131125Y
    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public void execute(Map<String, String> tokens) {
        Map<String, String> aliasMapping = aliasConfig.getAliasMapping();
        uiStore.setCommandResult(new CommandResult(getAliasCommandResult(aliasMapping)));
    }

    public Map<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    private String getAliasCommandResult(Map<String, String> aliasMapping) {
        ArrayList<String> lines = new ArrayList<>();

        SortedSet<String> aliases = new TreeSet<>(aliasMapping.keySet());
        for (String alias : aliases) {
            String line = alias + COMMAND_SEPARATOR_ALIAS + aliasMapping.get(alias);
            lines.add(line);
        }

        String result = String.join(NEW_LINE, lines);
        return result.isEmpty() ? RESULT_MESSAGE_NO_ALIAS : result;
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, null };
    }
}
