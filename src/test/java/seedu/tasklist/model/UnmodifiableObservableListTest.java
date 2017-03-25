package seedu.tasklist.model;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static seedu.tasklist.testutil.TestUtil.assertThrows;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.tag.UniqueTagList.DuplicateTagException;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.testutil.TypicalTestTasks;

public class UnmodifiableObservableListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Integer> backing;
    private UnmodifiableObservableList<Integer> list;

    @Before
    public void setUp() {
        backing = new ArrayList<>();
        backing.add(10);
        list = new UnmodifiableObservableList<>(FXCollections.observableList(backing));
    }

    @Test
    public void transformationListGenerators_correctBackingList() {
        assertSame(list.sorted().getSource(), list);
        assertSame(list.filtered(i -> true).getSource(), list);
    }

    @Test
    public void mutatingMethods_disabled() {

        final Class<UnsupportedOperationException> ex = UnsupportedOperationException.class;

        assertThrows(ex, () -> list.add(0, 2));
        assertThrows(ex, () -> list.add(3));

        assertThrows(ex, () -> list.addAll(2, 1));
        assertThrows(ex, () -> list.addAll(backing));
        assertThrows(ex, () -> list.addAll(0, backing));

        assertThrows(ex, () -> list.set(0, 2));

        assertThrows(ex, () -> list.setAll(new ArrayList<Number>()));
        assertThrows(ex, () -> list.setAll(1, 2));

        assertThrows(ex, () -> list.remove(0, 1));
        assertThrows(ex, () -> list.remove(null));
        assertThrows(ex, () -> list.remove(0));

        assertThrows(ex, () -> list.removeAll(backing));
        assertThrows(ex, () -> list.removeAll(1, 2));

        assertThrows(ex, () -> list.retainAll(backing));
        assertThrows(ex, () -> list.retainAll(1, 2));

        assertThrows(ex, () -> list.replaceAll(i -> 1));

        assertThrows(ex, () -> list.sort(Comparator.naturalOrder()));

        assertThrows(ex, () -> list.clear());

        final Iterator<Integer> iter = list.iterator();
        iter.next();
        assertThrows(ex, iter::remove);

        final ListIterator<Integer> liter = list.listIterator();
        liter.next();
        assertThrows(ex, liter::remove);
        assertThrows(ex, () -> liter.add(5));
        assertThrows(ex, () -> liter.set(3));
        assertThrows(ex, () -> list.removeIf(i -> true));
    }

    @Test
    public void toStringTest() throws DuplicateTagException, IllegalValueException, ParseException {
        //test for tags
        String expected = "test test1";
        UnmodifiableObservableList<Tag> tagTest = new UniqueTagList("test", "test1").asObservableList();
        assertTrue(expected.equals(tagTest.toString()));

        //test for tasks
        expected = "CS2103T tutorial StartDate: 15/03/2017 15:00:10 EndDate: 15/03/2017 18:00:10 "
                    + "Comment: prepare V0.2 presentation Priority: high Tags: [2103][class] "
                    + "CS3245 homework 3 Comment: discuss with classmates Priority: low Tags: [class] "
                    + "Buy groceries Comment: go NTUC Priority: low Tags:  "
                    + "Update Java for CS2103T Comment: Find out why jdk is not displaying the correct ver "
                    + "Priority: high Tags: [2103] "
                    + "Implement undo for this Deadline: 15/03/2017 18:00:10 Comment: By today Priority: high Tags:  "
                    + "Drink water Comment: To improve brain function Priority: high Tags:";
        UnmodifiableObservableList<ReadOnlyTask> taskTest = (UnmodifiableObservableList<ReadOnlyTask>)
                                                            new TypicalTestTasks().getTypicalTaskList().getTaskList();
        assertTrue(expected.equals(taskTest.toString()));
    }
}
