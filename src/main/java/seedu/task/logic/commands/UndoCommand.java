//@@author A0139161J
package seedu.task.logic.commands;

import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Last action undone";
    public static final String MESSAGE_FAIL = "Failed to undo";
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getUndoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_UNDO);
            }
            Object toUndo = gStack.getUndoStack().peek(); //needs improvement
            gStack.printStack();
            if (toUndo.getClass() == Task.class) {
                String parserInfo = ((Task) toUndo).getParserInfo();
                System.out.println("Parser Info = " + parserInfo);
                if (parserInfo.equals(COMMAND_WORD_ADD)) {
                    gStack.undoAdd();
                    model.deleteTask((Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } else if (parserInfo.equals(COMMAND_WORD_EDIT)) {
                    Task originalTask = gStack.undoGetOriginalTask();
                    Task editedTask = gStack.undoGetEditedTask();
                    model.deleteTask(editedTask);
                    model.addTask(originalTask);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } else if (parserInfo.equals(COMMAND_WORD_DELETE)) { // it'll be delete command
                    gStack.undoDelete(); // pushes task to redostack
                    model.insertTasktoIndex(((Task) toUndo).getIndex(), (Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                }
            } else {
                TaskManager undo = gStack.undoClear();
                model.resetData(undo);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
            }
        } catch (TaskNotFoundException e) {
            assert false : "not possible";
        } catch (DuplicateTaskException e) {
            assert false : "not possible";
        }
        assert false : "not possible";
        return null;
    }
}
