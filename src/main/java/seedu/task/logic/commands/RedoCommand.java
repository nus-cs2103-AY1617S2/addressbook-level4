//@@author A0139161J
package seedu.task.logic.commands;

import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

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
            Object toRedo = gStack.getRedoStack().peek(); //needs improvement
            if (toRedo.getClass() == Task.class) { //add or edit command
                String parserInfo = ((Task) toRedo).getParserInfo();
                if (parserInfo.equals(COMMAND_WORD_ADD)) {
                    gStack.redoAdd();
                    model.addTask((Task) toRedo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
                } else if (parserInfo.equals(COMMAND_WORD_EDIT)) {
                    Task editedTask = gStack.redoGetEditedTask();
                    Task originalTask = gStack.redoGetOriginalTask();
                    model.deleteTask(originalTask);
                    model.addTask(editedTask);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
                } else { //delete command
                    ReadOnlyTask unmutableTask = gStack.redoDelete();
                    model.deleteTask(unmutableTask);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
                }
            } else {
                gStack.redoClear();
                model.resetData(new TaskManager());
                return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
            }
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        } catch (TaskNotFoundException e) {
            assert false : "not possible";
        }
        assert false : "not possible";
        return null;
    }
}
