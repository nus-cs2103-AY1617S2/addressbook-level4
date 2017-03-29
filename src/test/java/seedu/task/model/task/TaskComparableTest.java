package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import seedu.task.model.tag.UniqueTagList;
//@@author A0163559U
public class TaskComparableTest {

    @Test
    public void sortByComplete() {
        // String frequency = "task frequency";
        String priority = "2";
        String timing = "03/03/2017";
        String tag1 = "friendship";
        String tag2 = "love";
        try {
            Task t1 = new Task(new Description("one"), new Priority(priority),
                    new Timing(timing), new Timing(timing), new UniqueTagList(tag1, tag2), false, null);
            Task t2 = new Task(new Description("two"), new Priority(priority),
                    new Timing(timing), new Timing(timing), new UniqueTagList(tag1, tag2), false, null);

            ArrayList<Task> al = new ArrayList<Task>();
            al.add(t1);
            al.add(t2);
            t1.setComplete();
            Collections.sort(al);

            ArrayList<Task> expected = new ArrayList<Task>();
            expected.add(t2);
            expected.add(t1);
            assertEquals(al, expected);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    //    private void printDescriptions(ArrayList<Task> al) {
    //        System.out.println("Starting print...");
    //        for (Task t : al) {
    //            System.out.println(t.getDescription());
    //        }
    //        System.out.println("Ending print.");
    //
    //    }
}
//@@author
