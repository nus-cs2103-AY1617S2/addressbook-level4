package seedu.onetwodo.model.history;

import java.util.Stack;

import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.ReadOnlyTask;

//@@author A0135739W
/**
 * Represents the saved history of ToDoLists.
 */
public class ToDoListHistoryManager implements ToDoListHistory {

    public static final String MESSAGE_EMPTYUNDOHISTORY = "There is nothing to undo.";
    public static final String MESSAGE_EMPTYREDOHISTORY = "There is nothing to redo.";

    private Stack<ToDoList> previousToDoLists;
    private Stack<ToDoList> nextToDoLists;
    private Stack<CommandHistoryEntry> previousCommandHistory;
    private Stack<CommandHistoryEntry> nextCommandHistory;

    public ToDoListHistoryManager () {
        this.previousToDoLists = new Stack<ToDoList>();
        this.nextToDoLists = new Stack<ToDoList>();
        this.previousCommandHistory = new Stack<CommandHistoryEntry>();
        this.nextCommandHistory = new Stack<CommandHistoryEntry>();
    }

    
    public void saveUndoInformationAndClearRedoHistory(String commandWord, ReadOnlyTask taskBeforeEdit, 
            ReadOnlyTask taskAfterEdit, ToDoList toDoList) {
        previousCommandHistory.push(new CommandHistoryEntry(commandWord, taskBeforeEdit, taskAfterEdit));
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
        nextCommandHistory.clear();
    }
    
    
    @Override
    public void saveUndoInformationAndClearRedoHistory(String commandWord, ReadOnlyTask task,
            ToDoList toDoList) {
        previousCommandHistory.push(new CommandHistoryEntry(commandWord, task));
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
        nextCommandHistory.clear();
    }

    @Override
    public void saveUndoInformationAndClearRedoHistory(String commandWord, ToDoList toDoList) {
        previousCommandHistory.push(new CommandHistoryEntry(commandWord));
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
        nextCommandHistory.clear();
    }

    @Override
    public void saveUndoInformation(ToDoList toDoList) {
        ToDoList copiedCurrentToDoList = new ToDoList(toDoList);
        previousToDoLists.push(copiedCurrentToDoList);
    }

    @Override
    public void saveRedoInformation(ToDoList toDoList) {
        ToDoList copiedCurrentToDoList = new ToDoList(toDoList);
        nextToDoLists.push(copiedCurrentToDoList);
    }

    @Override
    public ToDoList getPreviousToDoList() {
        assert (hasUndoHistory());
        return previousToDoLists.pop();
    }

    @Override
    public ToDoList getNextToDoList() {
        assert (hasRedoHistory());
        return nextToDoLists.pop();
    }

    @Override
    public String getUndoFeedbackMessageAndTransferToRedo() {
        assert (!previousCommandHistory.empty());
        CommandHistoryEntry previousCommand = previousCommandHistory.pop();
        String feedbackMessage = previousCommand.getFeedbackMessageInReverseCommand();
        nextCommandHistory.push(previousCommand);
        return feedbackMessage;
    }

    @Override
    public String getRedoFeedbackMessageAndTransferToUndo() {
        assert (!nextCommandHistory.empty());
        CommandHistoryEntry nextCommand = nextCommandHistory.pop();
        String feedbackMessage = nextCommand.getFeedbackMessage();
        previousCommandHistory.push(nextCommand);
        return feedbackMessage;
    }

    @Override
    public boolean hasUndoHistory() {
        return !previousToDoLists.empty() && !previousCommandHistory.empty();
    }

    @Override
    public boolean hasRedoHistory()  {
        return !nextToDoLists.empty() && !nextCommandHistory.empty();
    }
}
