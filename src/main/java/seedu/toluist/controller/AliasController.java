package seedu.toluist.controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.Config;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.AliasTable;
import seedu.toluist.ui.Ui;

/**
 * Alias Controller is responsible for handling alias requests
 */
public class AliasController extends Controller {
    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s for %s was added";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s for %s could not be added";
    private static final String RESULT_MESSAGE_RESERVED_WORD = "%s is a reserved word";
    private static final String COMMAND_TEMPLATE = "alias\\s+(?<alias>\\S+)\\s+(?<command>.+)";
    private static final String COMMAND_WORD = "alias";

    private static final String ALIAS_TERM = "alias";
    private static final String COMMAND_TERM = "command";

    private final AliasTable aliasConfig = Config.getInstance().getAliasTable();

    public AliasController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        HashMap<String, String> tokens = tokenize(command);
        String alias = tokens.get(ALIAS_TERM);
        String commandPhrase = tokens.get(COMMAND_TERM);

        if (aliasConfig.isReservedWord(alias)) {
            return new CommandResult(String.format(RESULT_MESSAGE_RESERVED_WORD, alias));
        }

        if (aliasConfig.setAlias(alias, commandPhrase) && Config.getInstance().save()) {
            return new CommandResult(String.format(RESULT_MESSAGE_SUCCESS, alias, commandPhrase));
        } else {
            return new CommandResult(String.format(RESULT_MESSAGE_FAILURE, alias, commandPhrase));
        }
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(ALIAS_TERM, matcher.group(ALIAS_TERM));
        tokens.put(COMMAND_TERM, matcher.group(COMMAND_TERM));
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
