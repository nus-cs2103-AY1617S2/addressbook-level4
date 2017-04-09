//@@author A0163559U
package seedu.task.model.task;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

public class TaskComparableTest {

    private String description = "task description ";
    private String priority = "2";
    private String timing = "03/03/2017";

    private String tag1 = "friendship";
    private String tag2 = "love";

    @Test
    public void sortDescriptions() throws IllegalValueException {
        Description a = new Description(description + "a");
        Description b = new Description(description + "b");
        Description c = new Description(description + "c");

        int ab = a.compareTo(b);
        assertEquals(ab, -1);

        int ac = a.compareTo(c);
        assertEquals(ac, -2);

        int ba = b.compareTo(a);
        assertEquals(ba, 1);

        assertEquals(0, a.compareTo(a));

        ArrayList<Description> actual = new ArrayList<Description>(Arrays.asList(b, c, a));
        Collections.sort(actual);

        ArrayList<Description> expected = new ArrayList<Description>(Arrays.asList(a, b, c));

        assertEquals(actual, expected);

    }

    @Test
    public void sortPriorities() throws IllegalValueException {
        Priority a = new Priority("1");
        Priority b = new Priority("2");
        Priority c = new Priority("3");

        int ab = a.compareTo(b);
        assertEquals(ab, -1);

        int ac = a.compareTo(c);
        assertEquals(ac, -2);

        int ba = b.compareTo(a);
        assertEquals(ba, 1);

        assertEquals(0, a.compareTo(a));

        ArrayList<Priority> actual = new ArrayList<Priority>(Arrays.asList(b, c, a));
        Collections.sort(actual);

        ArrayList<Priority> expected = new ArrayList<Priority>(Arrays.asList(a, b, c));

        assertEquals(actual, expected);

    }

    @Test
    public void sortTimings() throws IllegalValueException {
        Timing a = new Timing("01/01/2017");
        Timing b = new Timing("01/02/2017");
        Timing c = new Timing("01/03/2017");
        Timing floatTime = new Timing(null);

        //compare normal Timings
        int ab = a.compareTo(b);
        assertEquals(ab, -1);

        int ac = a.compareTo(c);
        assertEquals(ac, -1);

        int ba = b.compareTo(a);
        assertEquals(ba, 1);

        assertEquals(0, a.compareTo(a));

        ArrayList<Timing> actual = new ArrayList<Timing>(Arrays.asList(b, c, a));
        Collections.sort(actual);

        ArrayList<Timing> expected = new ArrayList<Timing>(Arrays.asList(a, b, c));

        assertEquals(actual, expected);

        //compare null Timings

        int fa = floatTime.compareTo(a);
        assertEquals(fa, 1);

        int fb = floatTime.compareTo(floatTime);
        assertEquals(fb, 0);

        int fc = c.compareTo(floatTime);
        assertEquals(fc, -1);

        //expect null Timing to sort to end of list
        actual.add(0, floatTime);
        expected.add(floatTime);

        Collections.sort(actual);

        assertEquals(actual, expected);
    }

    @Test
    public void sortTasksByCompleteness() throws DuplicateTagException, IllegalValueException {
        Timing time = new Timing(timing);
        UniqueTagList utl = new UniqueTagList(tag1, tag2);

        Task t1 = new Task(new Description(description + "a"), new Priority(priority),
                time, time, utl, false, null);
        Task t2 = new Task(new Description(description + "b"), new Priority(priority),
                time, time, utl, false, null);
        Task t3 = new Task(new Description(description + "c"), new Priority(priority),
                time, time, utl, false, null);

        //add two near identical tasks
        ArrayList<Task> al = new ArrayList<Task>(Arrays.asList(t1, t2));

        //complete one of them and expect completed task to move to end of list
        t1.setComplete();
        sort_and_assertEquals(al, new ArrayList<Task>(Arrays.asList(t2, t1)));

        //add an uncompleted task to the end of the list
        al.add(t3);
        sort_and_assertEquals(al, new ArrayList<Task>(Arrays.asList(t2, t3, t1)));

        //complete a task in the middle and expect it to move to the end
        t3.setComplete();
        sort_and_assertEquals(al, new ArrayList<Task>(Arrays.asList(t2, t1, t3)));

        //complete a task and expect it to move to the middle
        t2.setComplete();
        sort_and_assertEquals(al, new ArrayList<Task>(Arrays.asList(t1, t2, t3)));
    }

    @Test
    public void sortTasksByCompletenessAndPriority() throws DuplicateTagException, IllegalValueException {
        Timing time = new Timing(null);
        UniqueTagList utl = new UniqueTagList(tag1, tag2);

        Task t1 = new Task(new Description(description + "a t1"), new Priority("1"),
                time, time, utl, false, null);
        Task t2 = new Task(new Description(description + "b t2"), new Priority("1"),
                time, time, utl, false, null);
        Task t3 = new Task(new Description(description + "c t3"), new Priority("1"),
                time, time, utl, false, null);
        Task t4 = new Task(new Description(description + "a t4"), new Priority("2"),
                time, time, utl, false, null);
        Task t5 = new Task(new Description(description + "b t5"), new Priority("2"),
                time, time, utl, false, null);
        Task t6 = new Task(new Description(description + "c t6"), new Priority("2"),
                time, time, utl, false, null);
        Task t7 = new Task(new Description(description + "a t7"), new Priority("3"),
                time, time, utl, false, null);
        Task t8 = new Task(new Description(description + "b t8"), new Priority("3"),
                time, time, utl, false, null);
        Task t9 = new Task(new Description(description + "c t9"), new Priority("3"),
                time, time, utl, false, null);

        //expect tasks to sort by priority, then by description
        ArrayList<Task> actual = new ArrayList<Task>(Arrays.asList(t9, t8, t7, t6, t5, t4, t3, t2, t1));
        ArrayList<Task> expected = new ArrayList<Task>(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        sort_and_assertEquals(actual, expected);

        t1.setComplete();
        t4.setComplete();
        t7.setComplete();

        //expect tasks to sort by complete, then priority, then description
        expected = new ArrayList<Task>(Arrays.asList(t2, t3, t5, t6, t8, t9, t1, t4, t7));
        sort_and_assertEquals(actual, expected);
        Task t2et = new Task(new Description(description + "b t2et"), new Priority("1"),
                time, new Timing("01/01/2017"), utl, false, null);
        Task t5et = new Task(new Description(description + "b t5et"), new Priority("2"),
                time, new Timing("01/02/2017"), utl, false, null);
        Task t8et = new Task(new Description(description + "b t8et"), new Priority("3"),
                time, new Timing("01/03/2017"), utl, false, null);

        actual.add(t2et);
        actual.add(t5et);
        actual.add(t8et);

        //expect tasks to sort by complete, then end timing, priority, description
        expected = new ArrayList<Task>(Arrays.asList(t2et, t2, t3, t5et, t5, t6, t8et,
                t8, t9, t1, t4, t7));
        sort_and_assertEquals(actual, expected);

    }

    private void sort_and_assertEquals(ArrayList<Task> actual, ArrayList<Task> expected) {
        Collections.sort(actual);
        printDescriptions(actual);
        assertEquals(actual, expected);
    }

    private void printDescriptions(ArrayList<Task> al) {
        System.out.println("Starting print...");
        for (Task t : al) {
            System.out.print(t.getPriority() + " " + t.getDescription() +
                    " " + t.getEndTiming());
            if (t.isComplete()) {
                System.out.print(" complete");
            }
            System.out.println();
        }
        System.out.println("Ending print.");

    }
}
//@@author
