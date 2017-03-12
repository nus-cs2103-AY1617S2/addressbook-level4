package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.toluist.commons.core.Config;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.CommandAliasConfig;
import seedu.toluist.ui.Ui;

/**
 * Alias Controller is responsible for handling viewalias requests
 */
public class ViewAliasController extends Controller {
    private static final String COMMAND_TEMPLATE = "viewalias\\s*";
    private static final String COMMAND_WORD = "viewalias";
    private static final String ALIAS_COMMAND_SEPARATOR = ":";
    private static final String NEW_LINE = "\n";
    public static final String NO_ALIAS_MESSAGE = "No aliases found";

    private final CommandAliasConfig aliasConfig = Config.getInstance().getCommandAliasConfig();

    public ViewAliasController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        Map<String, String> aliasMapping = aliasConfig.getAliasMapping();
        return new CommandResult(getAliasCommandResult(aliasMapping));
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    private String getAliasCommandResult(Map<String, String> aliasMapping) {
        ArrayList<String> lines = new ArrayList<>();

        SortedSet<String> aliases = new TreeSet<>(aliasMapping.keySet());
        for (String alias : aliases) {
            String line = alias + ALIAS_COMMAND_SEPARATOR + aliasMapping.get(alias);
            lines.add(line);
        }

        String result = String.join(NEW_LINE, lines);
        return result.isEmpty() ? NO_ALIAS_MESSAGE : result;
    }
}
