package project.taskcrusher.model;

import static org.junit.Assert.assertEquals;

//import java.util.ArrayList;
//import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
//import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.task.ReadOnlyTask;
//import project.taskcrusher.model.task.Task;
//import project.taskcrusher.testutil.TypicalTestTasks;

public class UserInboxTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UserInbox inbox = new UserInbox();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inbox.getTaskList());
        assertEquals(Collections.emptyList(), inbox.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        inbox.resetData(null);
    }

//    @Test
//    public void resetData_withValidReadOnlyUserInbox_replacesData() {
//        UserInbox newData = new TypicalTestTasks().getTypicalUserInbox();
//        inbox.resetData(newData);
//        assertEquals(newData, inbox);
//    }
//
//    @Test
//    public void resetData_withDuplicateTasks_throwsAssertionError() {
//        TypicalTestTasks td = new TypicalTestTasks();
//        // Repeat td.assignment twice
//        List<Task> duplicateTasks = Arrays.asList(new Task(td.assignment), new Task(td.assignment));
//        List<Tag> newTags = td.assignment.getTags().asObservableList();
//        UserInboxStub newData = new UserInboxStub(duplicateTasks, newTags);
//
//        thrown.expect(AssertionError.class);
//        inbox.resetData(newData);
//    }
//
//    @Test
//    public void resetData_withDuplicateTags_throwsAssertionError() {
//        UserInbox typicalAddressBook = new TypicalTestTasks().getTypicalUserInbox();
//        List<ReadOnlyTask> newTasks = typicalAddressBook.getTaskList();
//        List<Tag> newTags = new ArrayList<>(typicalAddressBook.getTagList());
//        // Repeat the first tag twice
//        newTags.add(newTags.get(0));
//        UserInboxStub newData = new UserInboxStub(newTasks, newTags);
//
//        thrown.expect(AssertionError.class);
//        inbox.resetData(newData);
//    }

    /**
     * A stub ReadOnlyUserInbox whose tasks and tags lists can violate interface constraints.
     */
    private static class UserInboxStub implements ReadOnlyUserInbox {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        UserInboxStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }


        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
