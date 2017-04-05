package seedu.whatsleft.model;

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
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.testutil.TypicalTestEvents;
import seedu.whatsleft.testutil.TypicalTestTasks;

public class WhatsLeftTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final WhatsLeft whatsLeft = new WhatsLeft();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), whatsLeft.getEventList());
        assertEquals(Collections.emptyList(), whatsLeft.getTaskList());
        assertEquals(Collections.emptyList(), whatsLeft.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        whatsLeft.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyWhatsLeft_replacesData() {
        TypicalTestEvents te = new TypicalTestEvents();
        WhatsLeft newData = te.getTypicalWhatsLeft();
        whatsLeft.resetData(newData);
        assertEquals(newData, whatsLeft);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        TypicalTestEvents te = new TypicalTestEvents();
        // Repeat te.tutorial twice
        List<Event> newEvents = Arrays.asList(new Event(te.tutorial), new Event(te.tutorial));
        List<Task> newTasks = Arrays.asList();
        List<Tag> newTags = te.tutorial.getTags().asObservableList();
        WhatsLeftStub newData = new WhatsLeftStub(newEvents, newTasks, newTags);

        thrown.expect(AssertionError.class);
        whatsLeft.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks tt = new TypicalTestTasks();
        List<Event> newEvents = Arrays.asList();
        // Repeat tt.homework twice
        List<Task> newTasks = Arrays.asList(new Task(tt.homework), new Task(tt.homework));
        List<Tag> newTags = tt.homework.getTags().asObservableList();
        WhatsLeftStub newData = new WhatsLeftStub(newEvents, newTasks, newTags);

        thrown.expect(AssertionError.class);
        whatsLeft.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        WhatsLeft typicalWhatsLeft = new TypicalTestEvents().getTypicalWhatsLeft();
        List<ReadOnlyEvent> newEvents = typicalWhatsLeft.getEventList();
        List<ReadOnlyTask> newTasks = typicalWhatsLeft.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalWhatsLeft.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        WhatsLeftStub newData = new WhatsLeftStub(newEvents, newTasks, newTags);

        thrown.expect(AssertionError.class);
        whatsLeft.resetData(newData);
    }

    /**
     * A stub ReadOnlyWhatsLeft whose events, tasks and tags lists can violate interface constraints.
     */
    private static class WhatsLeftStub implements ReadOnlyWhatsLeft {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        WhatsLeftStub(Collection<? extends ReadOnlyEvent> events,
            Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.events.setAll(events);
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
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
