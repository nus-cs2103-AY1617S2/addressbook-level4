package seedu.todolist.model;

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
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;
import seedu.todolist.testutil.TypicalTestTasks;

public class ToDoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ToDoList toDoList = new ToDoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), toDoList.getTaskList());
        assertEquals(Collections.emptyList(), toDoList.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        toDoList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyToDoList_replacesData() {
        ToDoList newData = new TypicalTestTasks().getTypicalToDoList();
        toDoList.resetData(newData);
        assertEquals(newData, toDoList);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(TaskParser.parseTask(td.alice), TaskParser.parseTask(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        ToDoListStub newData = new ToDoListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        toDoList.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        ToDoList typicalToDoList = new TypicalTestTasks().getTypicalToDoList();
        List<Task> newTasks = typicalToDoList.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalToDoList.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        ToDoListStub newData = new ToDoListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        toDoList.resetData(newData);
    }

    /**
     * A stub ReadOnlyToDoList whose tasks and tags lists can violate interface constraints.
     */
    private static class ToDoListStub implements ReadOnlyToDoList {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ToDoListStub(Collection<? extends Task> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
