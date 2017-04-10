package seedu.ezdo.model;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.tag.UniqueTagList.DuplicateTagException;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.TaskDate;
import seedu.ezdo.testutil.TypicalTestTasks;

@RunWith(JMockit.class)
public class EzDoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EzDo ezDo = new EzDo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ezDo.getTaskList());
        assertEquals(Collections.emptyList(), ezDo.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        ezDo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEzDo_replacesData() {
        EzDo newData = new TypicalTestTasks().getTypicalEzDo();
        ezDo.resetData(newData);
        assertEquals(newData, ezDo);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        EzDoStub newData = new EzDoStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        List<ReadOnlyTask> newTasks = typicalEzDo.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalEzDo.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        EzDoStub newData = new EzDoStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        ezDo.resetData(newData);
    }

    @Test
    public void hashCode_works() {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        EzDo typicalEzDo2 = new TypicalTestTasks().getTypicalEzDo();
        assertEquals(typicalEzDo.hashCode(), typicalEzDo2.hashCode());
    }

    @Test
    public void moveCurrentTaskToDone_ive() throws IllegalValueException {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        ArrayList<Task> tasksToToggle = new ArrayList<Task>();
        Task task = new Task(new Name("test"), new Priority(""), new StartDate(""), new DueDate(""), new Recur("") ,
                new UniqueTagList("test"));
        tasksToToggle.add(task);
        IllegalValueException e = new IllegalValueException("illegal value");
        new Expectations(Recur.class) {
            {
                new Recur(""); result = e;
            }
        };
        new Verifications() {
            {
                e.printStackTrace();
            }
        };
        typicalEzDo.toggleTasksDone(tasksToToggle);
    }

    @Test
    public void updateRecurringTasks_ive() throws IllegalValueException {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        ArrayList<Task> tasksToToggle = new ArrayList<Task>();
        Task task = new Task(new Name("test"), new Priority(""), new StartDate(""),
                new DueDate("05/04/2016"), new Recur("daily"), new UniqueTagList("test"));
        tasksToToggle.add(task);
        IllegalValueException e = new IllegalValueException("illegal value");
        new MockUp<Task>() {
            @Mock
            TaskDate getStartDate() throws IllegalValueException {
                throw e;
            }
        };
        new Verifications() {
            {
                e.printStackTrace();
            }
        };
        typicalEzDo.toggleTasksDone(tasksToToggle);
    }

    @Test
    public void updateDate_parseException() throws DuplicateTagException, IllegalValueException {
        EzDo typicalEzDo = new TypicalTestTasks().getTypicalEzDo();
        ArrayList<Task> tasksToToggle = new ArrayList<Task>();
        Task task = new Task(new Name("test"), new Priority(""), new StartDate(""),
                new DueDate("05/04/2016"), new Recur("daily"), new UniqueTagList("test"));
        tasksToToggle.add(task);
        ParseException pe = new ParseException("parse exception", 1);
        new MockUp<Calendar>() {
            @Mock
            Calendar getInstance() throws ParseException {
                throw pe;
            }
        };
        new Verifications() {
            {
                pe.printStackTrace();
            }
        };
        typicalEzDo.toggleTasksDone(tasksToToggle);
    }
    /**
     * A stub ReadOnlyEzDo whose tasks and tags lists can violate interface constraints.
     */

    private static class EzDoStub implements ReadOnlyEzDo {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        EzDoStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
