package seedu.geekeep.model;

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
import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.testutil.TypicalTestTasks;

public class GeeKeepTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GeeKeep geeKeep = new GeeKeep();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), geeKeep.getTaskList());
        assertEquals(Collections.emptyList(), geeKeep.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        geeKeep.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyGeeKeep_replacesData() {
        GeeKeep newData = new TypicalTestTasks().getTypicalGeeKeep();
        geeKeep.resetData(newData);
        assertEquals(newData, geeKeep);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks;
        try {
            newTasks = Arrays.asList(new Task(td.dance), new Task(td.dance));
            List<Tag> newTags = td.dance.getTags().asObservableList();
            GeeKeepStub newData = new GeeKeepStub(newTasks, newTags);

            thrown.expect(AssertionError.class);
            geeKeep.resetData(newData);
        } catch (IllegalValueException ive) {
            assert false : "not possible";
        }
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        GeeKeep typicalGeeKeep = new TypicalTestTasks().getTypicalGeeKeep();
        List<ReadOnlyTask> newTasks = typicalGeeKeep.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalGeeKeep.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        GeeKeepStub newData = new GeeKeepStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        geeKeep.resetData(newData);
    }

    /**
     * A stub ReadOnlyGeeKeep whose tasks and tags lists can violate interface constraints.
     */
    private static class GeeKeepStub implements ReadOnlyGeeKeep {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        GeeKeepStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
