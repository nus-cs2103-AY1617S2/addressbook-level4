//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Alias Controller is responsible for handling unalias requests
 */
public class UnaliasController extends Controller {
    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s has been removed";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s cannot be removed";
    public static final String RESULT_MESSAGE_NOT_ALIAS = "%s is not an alias";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*unalias\\s+(?<alias>\\S+)\\s*";
    private static final String COMMAND_WORD = "unalias";

    private static final String PARAMETER_ALIAS = "alias";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Removes an alias for a command.";
    private static final String HELP_FORMAT = "unalias ALIAS";
    private static final String[] HELP_COMMENTS = { "Related commands: `alias`, `viewalias`" };
    private static final String[] HELP_EXAMPLES = { "`unalias abc`\nRemoves the alias `abc` from the program." };

  //@@author A0131125Y
    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public void execute(HashMap<String, String> tokens) {
        String alias = tokens.get(PARAMETER_ALIAS);

        if (!aliasConfig.isAlias(alias)) {
            uiStore.setCommandResult(new CommandResult(
                    String.format(RESULT_MESSAGE_NOT_ALIAS, alias), CommandResult.CommandResultType.FAILURE));
            return;
        }

        if (aliasConfig.removeAlias(alias) && Config.getInstance().save()) {
            uiStore.setCommandResult(new CommandResult(String.format(RESULT_MESSAGE_SUCCESS, alias)));
        } else {
            uiStore.setCommandResult(new CommandResult(
                    String.format(RESULT_MESSAGE_FAILURE, alias), CommandResult.CommandResultType.FAILURE));
        }
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_ALIAS, matcher.group(PARAMETER_ALIAS));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
