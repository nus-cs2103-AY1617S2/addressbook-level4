package seedu.doit.logic.commands;

import java.util.logging.Logger;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.storage.SetCommandEvent;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;

//@@author A0138909R
public class SetCommand extends Command {
    public static final String COMMAND_ALREADY_EXISTS = "Cannot change into a command that already exists!";
    public static final String NO_SUCH_COMMAND_TO_CHANGE = "There exists no such command to change!";
    public static final String COMMAND_WORD = "set";
    public static final String COMMAND_PARAMETER = "OLD_COMMAND NEW_COMMAND";
    public static final String COMMAND_RESULT = "Customizes command words";
    public static final String COMMAND_EXAMPLE = "set delete del\n" + "set del -";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the command identified by the default command word to a new command word\n"
            + "Parameters: OLD/DEFAULT_COMMAND_WORD NEW_COMMAND_WORD\n" + "Example: " + COMMAND_WORD + " add adding";
    public static final String MESSAGE_SET_TASK_SUCCESS = "Set command: %1$s";
    public final String newCommand;
    public final String oldCommand;
    private static final Logger logger = LogsCenter.getLogger(SetCommand.class);

    public SetCommand(String oldCommand, String newCommand) {
        this.oldCommand = oldCommand;
        this.newCommand = newCommand;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            this.model.commandSet(this.oldCommand, this.newCommand);
            logger.info(this.oldCommand + " command in command settings became: " + this.newCommand);
        } catch (NoSuchCommandException nsce) {
            throw new CommandException(NO_SUCH_COMMAND_TO_CHANGE);
        } catch (CommandExistedException cee) {
            throw new CommandException(COMMAND_ALREADY_EXISTS);
        }
        EventsCenter.getInstance().post(new SetCommandEvent(this.oldCommand, this.newCommand));
        return new CommandResult(String.format(MESSAGE_SET_TASK_SUCCESS, this.oldCommand + " into " + this.newCommand));

    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }
}
