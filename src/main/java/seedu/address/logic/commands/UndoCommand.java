package seedu.address.logic.commands;

import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

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
                    gStack.undoEdit();
                    model.updateTask(((Task) toUndo).getEditTaskIndex(), (Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } else if (parserInfo.equals(COMMAND_WORD_DELETE)) { // it'll be delete command
                    gStack.undoDelete(); // pushes task to redostack
                    System.out.println("To be restored : " + toUndo.toString());
                    System.out.println("Index to be restored" + ((Task) toUndo).getEditTaskIndex());
                    model.undoState(((Task) toUndo).getEditTaskIndex(), (Task) toUndo);
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
                } /*else if (toUndo.getClass() == ObservableList.class) { // undo clear command
                ObservableList<ReadOnlyTask> undo = gStack.undoClear();
                model.undoState(0, null, undo);
                }*/
            }
        } catch (TaskNotFoundException e) {
            throw new CommandException("Task not found");
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_FAIL);
    }
}
