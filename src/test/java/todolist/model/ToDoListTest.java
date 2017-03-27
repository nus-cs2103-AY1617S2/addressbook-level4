package todolist.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import todolist.model.tag.Tag;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.Task;
import todolist.testutil.TypicalTestTasks;

public class ToDoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ToDoList todooList = new ToDoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), todooList.getTaskList());
        assertEquals(Collections.emptyList(), todooList.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        todooList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyToDoList_replacesData() {
        ToDoList newData = new TypicalTestTasks().getTypicalToDoList();
        todooList.resetData(newData);
        assertEquals(newData, todooList);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.cs2103Tutorial), new Task(td.cs2103Tutorial));
        List<Tag> newTags = td.cs2103Tutorial.getTags().asObservableList();
        ToDoListStub newData = new ToDoListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        todooList.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        ToDoList typicalToDoList = new TypicalTestTasks().getTypicalToDoList();
        List<ReadOnlyTask> newTasks = typicalToDoList.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalToDoList.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        ToDoListStub newData = new ToDoListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        todooList.resetData(newData);
    }

    /**
     * A stub ReadOnlyToDoList whose tasks and tags lists can violate interface constraints.
     */
    private static class ToDoListStub implements ReadOnlyToDoList {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ToDoListStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
