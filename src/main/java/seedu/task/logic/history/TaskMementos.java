//@@author A0163744B
package seedu.task.logic.history;

import java.util.Optional;
import java.util.Stack;

/*
 * Stores a history of tasks. Can be used to implement undo/redo histories.
 */
public class TaskMementos {
    private Stack<TaskMemento> undoMementoStack;
    private Stack<TaskMemento> redoMementoStack;

    public TaskMementos() {
        this.undoMementoStack = new Stack<TaskMemento>();
        this.redoMementoStack = new Stack<TaskMemento>();
    }

    /**
     * Adds a TaskMemento to the undo list and clears the list of "redo" mementos.
     * The clearing of "redo" mementos occurs so that one cannot perform a command and then still
     * call redo to perform the action of a previous undo.
     * @param taskMemento the task memento to add to the undo history
     */
    public void addUndoMementoAndClearRedo(TaskMemento taskMemento) {
        this.undoMementoStack.push(taskMemento);
        this.redoMementoStack.clear();
    }

    /**
     * Moves backward up the undo history by one memento
     * @return the TaskMemento of the next command to undo or Optional.empty() if there is no command to undo
     */
    public Optional<TaskMemento> getUndoMemento() {
        if (this.undoMementoStack.isEmpty()) {
            return Optional.empty();
        }
        TaskMemento memento = undoMementoStack.pop();
        redoMementoStack.push(memento);
        return Optional.of(memento);
    }

    /**
     * Moves forward through the redo history by one memento
     * @return the TaskMemento of the next command to redo or Optional.empty() if there is no command to redo
     */
    public Optional<TaskMemento> getRedoMemento() {
        if (this.redoMementoStack.isEmpty()) {
            return Optional.empty();
        }
        TaskMemento memento = redoMementoStack.pop();
        undoMementoStack.push(memento);
        return Optional.of(memento);
    }
}
