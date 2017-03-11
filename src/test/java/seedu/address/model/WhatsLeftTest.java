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
import seedu.address.model.person.Activity;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTestActivities;

public class WhatsLeftTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final WhatsLeft addressBook = new WhatsLeft();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getActivityList());
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
        List<Activity> newActivities = Arrays.asList(new Activity(td.alice), new Activity(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        WhatsLeftStub newData = new WhatsLeftStub(newActivities, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        WhatsLeft typicalWhatsLeft = new TypicalTestActivities().getTypicalWhatsLeft();
        List<ReadOnlyActivity> newActivities = typicalWhatsLeft.getActivityList();
        List<Tag> newTags = new ArrayList<>(typicalWhatsLeft.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        WhatsLeftStub newData = new WhatsLeftStub(newActivities, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyWhatsLeft whose persons and tags lists can violate interface constraints.
     */
    private static class WhatsLeftStub implements ReadOnlyWhatsLeft {
        private final ObservableList<ReadOnlyActivity> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        WhatsLeftStub(Collection<? extends ReadOnlyActivity> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyActivity> getActivityList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
