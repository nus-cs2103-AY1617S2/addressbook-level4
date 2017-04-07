//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Alias Controller is responsible for handling unalias requests
 */
public class UnaliasController extends Controller {
    private static final String MESSAGE_RESULT_SUCCESS = "Alias %s has been removed";
    private static final String MESSAGE_RESULT_FAILURE = "Alias %s cannot be removed";
    public static final String MESSAGE_RESULT_NOT_ALIAS = "%s is not an alias";
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

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        String alias = tokens.get(PARAMETER_ALIAS);

        validateNoAlias(alias);

        unalias(alias);
    }

    private void unalias(String alias) throws InvalidCommandException {
        UiStore uiStore = UiStore.getInstance();
        if (aliasConfig.removeAlias(alias) && Config.getInstance().save()) {
            uiStore.setCommandResult(new CommandResult(String.format(MESSAGE_RESULT_SUCCESS, alias)));
        } else {
            throw new InvalidCommandException(String.format(MESSAGE_RESULT_FAILURE, alias));
        }
    }

    private void validateNoAlias(String alias) throws InvalidCommandException {
        if (!aliasConfig.isAlias(alias)) {
            throw new InvalidCommandException(String.format(MESSAGE_RESULT_NOT_ALIAS, alias));
        }
    }

    public Map<String, String> tokenize(String command) {
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
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
