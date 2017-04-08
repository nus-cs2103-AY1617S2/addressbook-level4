package onlythree.imanager.model.util;

import java.time.ZonedDateTime;
import java.util.Optional;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.model.ReadOnlyTaskList;
import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.UniqueTaskList.DuplicateTaskException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

public class SampleDataUtil {
    //@@author A0140023E
    public static Task[] getSampleTasks() {
        try {
            // TODO improve variety of test cases
            return new Task[] {
                new Task(new Name("EE2021"), Optional.of(new Deadline(ZonedDateTime.now().plusDays(3))),
                         Optional.empty(), new UniqueTagList("homework")),
                new Task(new Name("MA1506"), Optional.empty(), Optional.empty(),
                         new UniqueTagList("killer", "revision")),
                new Task(new Name("CS2103 Project"), Optional.empty(), Optional.empty(), new UniqueTagList("Help")),
                new Task(new Name("PC1222 Lecture"), Optional.empty(), Optional.empty(), new UniqueTagList("lecture")),
                new Task(new Name("GEH1027 Lecture"), Optional.empty(), Optional.empty(), new UniqueTagList("lecture")),
                new Task(new Name("HOHO Time"), Optional.empty(), Optional.empty(), new UniqueTagList("LMAO"))
            };
        } catch (PastDateTimeException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    //@@author
    public static ReadOnlyTaskList getSampleTaskList() {
        try {
            TaskList sampleTaskList = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskList.addTask(sampleTask);
            }
            return sampleTaskList;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
