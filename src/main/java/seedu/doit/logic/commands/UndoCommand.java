package seedu.doit.logic.commands;

import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.logic.commands.exceptions.CommandException;

//@@author A0138909R
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_PARAMETER = "";
    public static final String COMMAND_RESULT = "Undo previous command";
    public static final String COMMAND_EXAMPLE = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task undone.";
    public static final String MESSAGE_FAILURE = "Unable to undo. There is nothing to undo.\n"
            + "You cannot undo a save, load, find, set and list";

    // public static Command toUndo;

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
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
