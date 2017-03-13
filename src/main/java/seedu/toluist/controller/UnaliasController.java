package seedu.toluist.controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.Ui;

/**
 * Alias Controller is responsible for handling unalias requests
 */
public class UnaliasController extends Controller {
    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s has been removed";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s cannot be removed";
    private static final String RESULT_MESSAGE_NOT_ALIAS = "%s is not an alias";
    private static final String COMMAND_TEMPLATE = "unalias\\s+(?<alias>\\S+)\\s*";
    private static final String COMMAND_WORD = "unalias";

    private static final String ALIAS_TERM = "alias";

    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public UnaliasController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String alias = tokens.get(ALIAS_TERM);

        if (!aliasConfig.isAlias(alias)) {
            return new CommandResult(String.format(RESULT_MESSAGE_NOT_ALIAS, alias));
        }

        if (aliasConfig.removeAlias(alias) && Config.getInstance().save()) {
            return new CommandResult(String.format(RESULT_MESSAGE_SUCCESS, alias));
        } else {
            return new CommandResult(String.format(RESULT_MESSAGE_FAILURE, alias));
        }
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(ALIAS_TERM, matcher.group(ALIAS_TERM));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
