package seedu.address.model;

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
import seedu.address.model.person.Event;
import seedu.address.model.person.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTestActivities;

public class WhatsLeftTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final WhatsLeft addressBook = new WhatsLeft();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getEventList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyWhatsLeft_replacesData() {
        WhatsLeft newData = new TypicalTestActivities().getTypicalWhatsLeft();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateActivities_throwsAssertionError() {
        TypicalTestActivities td = new TypicalTestActivities();
        // Repeat td.alice twice
        List<Event> newEvents = Arrays.asList(new Event(td.tutorial), new Event(td.tutorial));
        List<Tag> newTags = td.tutorial.getTags().asObservableList();
        WhatsLeftStub newData = new WhatsLeftStub(newEvents, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        WhatsLeft typicalWhatsLeft = new TypicalTestActivities().getTypicalWhatsLeft();
        List<ReadOnlyEvent> newEvents = typicalWhatsLeft.getEventList();
        List<Tag> newTags = new ArrayList<>(typicalWhatsLeft.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        WhatsLeftStub newData = new WhatsLeftStub(newEvents, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyWhatsLeft whose persons and tags lists can violate interface constraints.
     */
    private static class WhatsLeftStub implements ReadOnlyWhatsLeft {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        WhatsLeftStub(Collection<? extends ReadOnlyEvent> persons, Collection<? extends Tag> tags) {
            this.events.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public List<ReadOnlyTask> getTaskList() {
            return tasks;
        }
    }

}
