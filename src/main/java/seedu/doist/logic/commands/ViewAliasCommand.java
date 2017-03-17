package seedu.doist.logic.commands;

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
        return new CommandInfo(Command.getAliasList(DEFAULT_COMMAND_WORD), DEFAULT_COMMAND_WORD);
    }

    private static String formatAliasesForOutput() {
        Set<String> allCommandWords = Command.getDefaultCommandWordSet();
        StringBuilder sb = new StringBuilder();
        for (String word: allCommandWords) {
            sb.append(word + ": ");
            for (String alias : Command.getAliasList(word)) {
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
