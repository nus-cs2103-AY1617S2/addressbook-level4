package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Set;

/**
 * Display all aliases.
 */
public class ViewAliasCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "view_alias";

    @Override
    public CommandResult execute() {
        return new CommandResult(formatAliasesForOutput());
    }

    public static CommandInfo info() {
        return new CommandInfo(new ArrayList<String>(), DEFAULT_COMMAND_WORD);
    }

    private String formatAliasesForOutput() {
        Set<String> allCommandWords = model.getDefaultCommandWordSet();
        StringBuilder sb = new StringBuilder();
        for (String word: allCommandWords) {
            sb.append(word + ": ");
            for (String alias : model.getAliasList(word)) {
                sb.append(alias + ", ");
            }
            // remove the comma after the last alias
            if (sb.charAt(sb.length() - 2) == ',') {
                sb.deleteCharAt(sb.length() - 2);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
