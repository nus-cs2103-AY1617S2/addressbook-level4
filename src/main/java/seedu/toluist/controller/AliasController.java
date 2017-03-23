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
    public static final String ALIAS_TERM = "alias";
    public static final String COMMAND_TERM = "command";
    public static final String COMMAND_WORD = "alias";

    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s for %s was added";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s for %s could not be added";
    public static final String RESULT_MESSAGE_RESERVED_WORD = "%s is a reserved word";
    private static final String COMMAND_TEMPLATE = "alias\\s+(?<alias>\\S+)\\s+(?<command>.+)";

    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public void execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String alias = tokens.get(ALIAS_TERM);
        String commandPhrase = tokens.get(COMMAND_TERM);

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
        tokens.put(ALIAS_TERM, matcher.group(ALIAS_TERM));
        tokens.put(COMMAND_TERM, matcher.group(COMMAND_TERM));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
