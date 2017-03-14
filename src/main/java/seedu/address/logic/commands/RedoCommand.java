package seedu.address.logic.commands;

import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Last action reverted";
    public static final String MESSAGE_FAIL = "Failed to redo";
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getRedoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_REDO);
            }
            Task toRedo = gStack.getRedoStack().peek(); //needs improvement
            String parserInfo = toRedo.getParserInfo();
            if (parserInfo.equals(COMMAND_WORD_ADD)) {
                gStack.redoAdd();
                model.addTask(toRedo);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
            } else if (parserInfo.equals(COMMAND_WORD_EDIT)) {
                gStack.redoEdit();
                model.updateTask(toRedo.getEditTaskIndex(), toRedo);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
            }
            return new CommandResult(String.format(MESSAGE_FAIL, toRedo));
        } catch (DuplicateTaskException e) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }
}
