package seedu.toluist.controller;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.CommandAliasConfig;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Alias Controller is responsible for handling alias requests
 */
public class AliasController extends Controller {
    private static final String RESULT_MESSAGE_SUCCESS = "Alias %s for %s added";
    private static final String RESULT_MESSAGE_FAILURE = "Alias %s for %s cannot be added";
    private static final String COMMAND_TEMPLATE = "alias\\s+(?<alias>\\S+)\\s+(?<command>.+)";
    private static final String COMMAND_WORD = "alias";

    private static final String ALIAS_TERM = "alias";
    private static final String COMMAND_TERM = "command";

    private final CommandAliasConfig aliasConfig;

    public AliasController(Ui renderer, CommandAliasConfig aliasConfig) {
        super(renderer);
        this.aliasConfig = aliasConfig;
    }

    public CommandResult execute(String command) {
         HashMap<String, String> tokens = tokenize(command);
         String alias = tokens.get(ALIAS_TERM);
         String commandPhrase = tokens.get(COMMAND_TERM);

         if (aliasConfig.setAlias(alias, commandPhrase)) {
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

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
