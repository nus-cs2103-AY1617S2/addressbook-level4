package seedu.bulletjournal.logic.commands;

import java.io.IOException;
import java.util.Set;

import seedu.bulletjournal.commons.core.Config;
import seedu.bulletjournal.commons.util.ConfigUtil;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ChangePathCommand extends Command {

    public static final String COMMAND_WORD = "changepath";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change location of saved todo list to "
            + "the specified location.\n"
            + "Parameters:...\n"
            + "Example: " + COMMAND_WORD + " ...";

    private final String path;

    public ChangePathCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() {
        Config config = new Config();
        config.setAddressBookFilePath(path);
        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new CommandResult("changed to ...");
    }

    

}
