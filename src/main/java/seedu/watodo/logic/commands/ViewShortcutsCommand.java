package seedu.watodo.logic.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

import seedu.watodo.logic.commands.exceptions.CommandException;

//@@author A01403076J
/**
 * Shows user a list of all the command words for each of the standard commands.
 */
public class ViewShortcutsCommand extends Command {

    public static final String COMMAND_WORD = "viewshortcuts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all shortcut commands.\n"
            + "Example: " + COMMAND_WORD;

    public static final String VIEW_SHORTCUT_MESSAGE = "%1$s\nAll shortcuts shown.";


    @Override
    public CommandResult execute() throws CommandException {

        LinkedHashMap<String, ArrayList<String>> commandShortcuts = collateShortcutkeys();

        StringBuilder sb = formatForView(commandShortcuts);

        return new CommandResult(String.format(VIEW_SHORTCUT_MESSAGE, sb.toString()));
    }

    /** Collects all the shortcut keys and alternative command words for each of the standard command words */
    private LinkedHashMap<String, ArrayList<String>> collateShortcutkeys() {
        AlternativeCommandsLibrary.altCommands.values().removeIf(Objects::isNull);
        LinkedHashMap<String, ArrayList<String>> commandShortcuts = new LinkedHashMap<String, ArrayList<String>>();
        for (String cmdWord : AlternativeCommandsLibrary.COMMANDS_WORDS) {
            commandShortcuts.put(cmdWord, new ArrayList<String>());
        }
        return commandShortcuts;
    }

    /** Formats the list of shortcuts and alternative command words for displaying to the user  */
    private StringBuilder formatForView(LinkedHashMap<String, ArrayList<String>> commandShortcuts) {
        StringBuilder sb = new StringBuilder();
        AlternativeCommandsLibrary.altCommands.forEach((shortcut, cmdWord)
            -> commandShortcuts.get(cmdWord).add(shortcut));
        commandShortcuts.forEach((cmdWord, shortcuts) -> sb.append(cmdWord + ": " + shortcuts + "\n"));
        return sb;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
