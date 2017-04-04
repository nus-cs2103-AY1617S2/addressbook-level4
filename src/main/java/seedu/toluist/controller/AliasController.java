//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Alias Controller is responsible for handling alias requests
 */
public class AliasController extends Controller {
    public static final String PARAMETER_ALIAS = "alias";
    public static final String PARAMETER_COMMAND = "command";
    public static final String COMMAND_WORD = "alias";

    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s for %s was added";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s for %s could not be added";
    public static final String RESULT_MESSAGE_RESERVED_WORD = "%s is a reserved word";
    private static final String COMMAND_TEMPLATE = "(?iu)\\s*alias\\s+(?<alias>\\S+)\\s+(?<command>.+)";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Adds an alias for a phrase. "
                                                   + "The alias can be used to represent that phrase for commands.";
    private static final String HELP_FORMAT = "alias ALIAS PHRASE";
    private static final String[] HELP_COMMENTS = { "Related commands: `unalias`, `viewalias`",
                                                    "Once added, the alias can be used instead "
                                                        + "of the phrase to perform commands.",
                                                    "The phrase can be multiple words long." };
    private static final String[] HELP_EXAMPLES = { "`alias a add`\nAdds `a` as an alias for the word `add`.",
                                                    "`alias 1 mark complete 1`\n"
                                                        + "Adds `1` as an alias for the phrase `mark complete 1`.",
                                                    "`alias a alias`\nUpdates `a` "
                                                        + "to be an alias for the word `alias` instead of `add`." };

    //@@author A0131125Y
    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public void execute(Map<String, String> tokens) {
        String alias = tokens.get(PARAMETER_ALIAS);
        String commandPhrase = tokens.get(PARAMETER_COMMAND);

        if (aliasConfig.isReservedWord(alias)) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_RESERVED_WORD, alias),
                            CommandResult.CommandResultType.FAILURE));
            return;
        }

        if (aliasConfig.setAlias(alias, commandPhrase) && Config.getInstance().save()) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_SUCCESS, alias, commandPhrase)));
        } else {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_FAILURE, alias, commandPhrase),
                            CommandResult.CommandResultType.FAILURE));
        }
    }

    public Map<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_ALIAS, matcher.group(PARAMETER_ALIAS));
        tokens.put(PARAMETER_COMMAND, matcher.group(PARAMETER_COMMAND));
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
