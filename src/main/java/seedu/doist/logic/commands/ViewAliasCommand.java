package seedu.doist.logic.commands;

import java.util.Set;

//@@author A0147980U
/**
 * Display all aliases.
 */
public class ViewAliasCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "view_alias";

    @Override
    public CommandResult execute() {
        return new CommandResult(formatAliasesForOutput());
    }

    private String formatAliasesForOutput() {
        Set<String> allCommandWords = aliasModel.getDefaultCommandWordSet();
        StringBuilder sb = new StringBuilder();
        for (String word: allCommandWords) {
            // start with command word
            sb.append(word + ": ");

            // append aliases one after another
            for (String alias : aliasModel.getAliasList(word)) {
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
