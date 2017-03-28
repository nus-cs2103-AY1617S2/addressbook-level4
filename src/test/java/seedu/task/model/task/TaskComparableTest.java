package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.task.model.tag.UniqueTagList;
//@@author A0163559U
public class TaskComparableTest {

    String description = "task description";
    String priority = "2";
    String timing = "03/03/2017";
    String tag1 = "friendship";
    String tag2 = "love";

    @Test
    public void sortByComplete() {

        try {
            Task t1 = new Task(new Description(description + " a"), new Priority(priority),
                    new Timing(timing), new Timing(timing), new UniqueTagList(tag1, tag2));
            Task t2 = new Task(new Description(description + " b"), new Priority(priority),
                    new Timing(timing), new Timing(timing), new UniqueTagList(tag1, tag2));
            Task t3 = new Task(new Description(description + " c"), new Priority(priority),
                    new Timing(timing), new Timing(timing), new UniqueTagList(tag1, tag2));

            //add two near identical tasks
            ArrayList<Task> al = new ArrayList<Task>(Arrays.asList(t1,t2));

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

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void sort_and_assertEquals(ArrayList<Task> actual, ArrayList<Task> expected) {
        Collections.sort(actual);
        assertEquals(actual,expected);
    }

    @SuppressWarnings("unused")
    private void printDescriptions(ArrayList<Task> al) {
        System.out.println("Starting print...");
        for (Task t : al) {
            System.out.println(t.getDescription());
        }
        System.out.println("Ending print.");

    }
}
//@@author
