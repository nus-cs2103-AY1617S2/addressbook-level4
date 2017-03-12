package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Add tutor to github"), new Date("6/03/07"), new Date("07-03-2007"),
                    new Remark("For CS2103T V0.1"),
                    new Location("Blk 30 Geylang Street 29, #06-40"), new UniqueTagList("project"), false),
                new Task(new Name("Bridge competition"), new Date("10/12/07"), new Date("12-12-2017"),
                    new Remark("Match 3, table A"),
                    new Location("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new UniqueTagList("CCA", "friends"), false),
                new Task(new Name("Change USD to SGD"), new Date("1-08-15"), new Date("07-08-2015"),
                    new Remark("Too much change from trip"),
                    new Location("Blk 11 Ang Mo Kio Street 74, #11-04"), new UniqueTagList("money"), false),
                new Task(new Name("Download lecture notes"), new Date("6/03/17"), new Date("23-08-2017"),
                    new Remark("For open-book finals"),
                    new Location("Blk 436 Serangoon Gardens Street 26, #16-43"), new UniqueTagList("finals"), false),
                new Task(new Name("Invite friends over for housewarming"), new Date("6/03/17 2300"),
                    new Date("09-03-2017"),
                    new Remark("prepare food as well"),
                    new Location("Blk 47 Tampines Street 20, #17-35"), new UniqueTagList("friends", "house"), false),
                new Task(new Name("Rewatch lecture 4 webcast"), new Date("6/03/17 10am"),
                    new Date("17-03-2017"),
                    new Remark("on algebraic query"),
                    new Location("Blk 45 Aljunied Street 85, #11-31"), new UniqueTagList("finals"), false)
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
