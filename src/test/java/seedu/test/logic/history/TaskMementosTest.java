//@@evanyeung A0163744B
package seedu.test.logic.history;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.logic.history.TaskMementos;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;

public class TaskMementosTest {
    private TaskMemento memento1;
    private TaskMemento memento2;
    private TaskMemento memento3;

    @Before
    public void setup() throws DuplicateTagException, IllegalValueException {
        memento1 = new TaskMemento(
                new Task(
                    new Description("Task 1"),
                    new DueDate("2017/01/01 0100"),
                    new Duration("2017/01/01 0000", "2017/01/01 0100"),
                    new UniqueTagList("Task1"),
                    new Complete(false),
                    new TaskId(101)
                ),
                new Task(
                    new Description("Task"),
                    new DueDate("2017/01/01 0100"),
                    new Duration("2017/01/01 0000", "2017/01/01 0100"),
                    new UniqueTagList("Task"),
                    new Complete(false),
                    new TaskId(101)
                )
        );
        memento2 = new TaskMemento(
                new Task(
                    new Description("Task 2"),
                    new DueDate("2017/01/01 0100"),
                    new Duration("2015/01/01 1200", "2016/02/01 0100"),
                    new UniqueTagList("Task2"),
                    new Complete(false),
                    new TaskId(102)
                ),
                new Task(
                    new Description("Task"),
                    new DueDate("2017/01/01 0100"),
                    new Duration("2017/01/01 0000", "2017/01/01 0100"),
                    new UniqueTagList("Task"),
                    new Complete(false),
                    new TaskId(102)
                )
        );
        memento3 = new TaskMemento(
                new Task(
                    new Description("Task 3"),
                    new DueDate("2017/02/22 2300"),
                    new Duration("2017/02/19 1243", "2017/05/25 1245"),
                    new UniqueTagList("Task3"),
                    new Complete(false),
                    new TaskId(103)
                ),
                new Task(
                    new Description("Task"),
                    new DueDate("2017/01/01 0100"),
                    new Duration("2017/01/01 0000", "2017/01/01 0100"),
                    new UniqueTagList("Task"),
                    new Complete(false),
                    new TaskId(103)
                )
        );
    }

    @Test
    public void undoTest() {
        TaskMementos mementos = new TaskMementos();
        assertEquals(Optional.empty(), mementos.getUndoMemento());
        mementos.addUndoMementoAndClearRedo(memento1);
        assertEquals(Optional.of(memento1), mementos.getUndoMemento());
        assertEquals(Optional.empty(), mementos.getUndoMemento());
    }

    @Test
    public void redoTest() {
        TaskMementos mementos = new TaskMementos();
        assertEquals(Optional.empty(), mementos.getRedoMemento());
        mementos.addUndoMementoAndClearRedo(memento1);
        assertEquals(Optional.empty(), mementos.getRedoMemento());
        mementos.getUndoMemento();
        assertEquals(Optional.of(memento1), mementos.getRedoMemento());
        mementos.addUndoMementoAndClearRedo(memento2);
        assertEquals(Optional.empty(), mementos.getRedoMemento());
    }

    @Test
    public void multipleUndoRedoTest() {
        TaskMementos mementos = new TaskMementos();
        assertEquals(Optional.empty(), mementos.getUndoMemento());
        assertEquals(Optional.empty(), mementos.getRedoMemento());
        mementos.addUndoMementoAndClearRedo(memento1);
        mementos.addUndoMementoAndClearRedo(memento2);
        assertEquals(Optional.empty(), mementos.getRedoMemento());

        assertEquals(Optional.of(memento2), mementos.getUndoMemento());
        assertEquals(Optional.of(memento2), mementos.getRedoMemento());
        assertEquals(Optional.of(memento2), mementos.getUndoMemento());
        assertEquals(Optional.of(memento1), mementos.getUndoMemento());
        assertEquals(Optional.empty(), mementos.getUndoMemento());

        assertEquals(Optional.of(memento1), mementos.getRedoMemento());
        assertEquals(Optional.of(memento2), mementos.getRedoMemento());
        assertEquals(Optional.empty(), mementos.getRedoMemento());

        assertEquals(Optional.of(memento2), mementos.getUndoMemento());
        mementos.addUndoMementoAndClearRedo(memento3);
        assertEquals(Optional.empty(), mementos.getRedoMemento());

        assertEquals(Optional.of(memento3), mementos.getUndoMemento());
        assertEquals(Optional.of(memento1), mementos.getUndoMemento());
    }
}
