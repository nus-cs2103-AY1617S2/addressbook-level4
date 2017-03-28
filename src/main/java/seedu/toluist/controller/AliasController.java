//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
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

    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public void execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String alias = tokens.get(PARAMETER_ALIAS);
        String commandPhrase = tokens.get(PARAMETER_COMMAND);

        if (aliasConfig.isReservedWord(alias)) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_RESERVED_WORD, alias)));
            return;
        }

        if (aliasConfig.setAlias(alias, commandPhrase) && Config.getInstance().save()) {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_SUCCESS, alias, commandPhrase)));
        } else {
            uiStore.setCommandResult(
                    new CommandResult(String.format(RESULT_MESSAGE_FAILURE, alias, commandPhrase)));
        }
    }

    public HashMap<String, String> tokenize(String command) {
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

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
