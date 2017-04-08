package project.taskcrusher.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

//import java.util.ArrayList;
//import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
//import java.util.List;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.testutil.TypicalTestUserInbox;

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

    @Test
    public void resetData_withValidReadOnlyUserInbox_replacesData() {
        UserInbox newData = new TypicalTestUserInbox().getTypicalUserInbox();
        inbox.resetData(newData);
        assertEquals(newData, inbox);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestUserInbox td = new TypicalTestUserInbox();

        // Repeat td.assignment twice
        List<Task> duplicateTasks = Arrays.asList(new Task(td.assignment1), new Task(td.assignment1));
        List<Tag> newTags = td.assignment1.getTags().asObservableList();
        List<Event> emptyEvents = Arrays.asList();
        UserInboxStub newData = new UserInboxStub(duplicateTasks, emptyEvents, newTags);

        thrown.expect(AssertionError.class);
        inbox.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        TypicalTestUserInbox td = new TypicalTestUserInbox();

        // Repeat td.islandTrip twice
        List<Event> duplicateEvents = Arrays.asList(new Event(td.islandTrip), new Event(td.islandTrip));
        List<Tag> newTags = td.assignment1.getTags().asObservableList();
        List<Task> emptyTasks = Arrays.asList();
        UserInboxStub newData = new UserInboxStub(emptyTasks, duplicateEvents, newTags);

        thrown.expect(AssertionError.class);
        inbox.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        UserInbox typicalUserInbox = new TypicalTestUserInbox().getTypicalUserInbox();
        List<ReadOnlyTask> newTasks = typicalUserInbox.getTaskList();
        List<ReadOnlyEvent> newEvents = typicalUserInbox.getEventList();
        List<Tag> newTags = new ArrayList<>(typicalUserInbox.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        UserInboxStub newData = new UserInboxStub(newTasks, newEvents, newTags);

        thrown.expect(AssertionError.class);
        inbox.resetData(newData);
    }

    /**
     * A stub ReadOnlyUserInbox whose tasks and tags lists can violate interface constraints.
     */
    private static class UserInboxStub implements ReadOnlyUserInbox {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        UserInboxStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends ReadOnlyEvent> events,
                Collection<? extends Tag> tags) {

            this.tasks.setAll(tasks);
            this.events.setAll(events);
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
