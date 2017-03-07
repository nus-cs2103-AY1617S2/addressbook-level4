package seedu.task.model.util;

<<<<<<< HEAD:src/main/java/seedu/address/model/util/SampleDataUtil.java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.person.Location;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.person.Task;
import seedu.address.model.person.Date;
import seedu.address.model.person.UniquePersonList.DuplicatePersonException;
import seedu.address.model.tag.UniqueTagList;
=======
import seedu.task.model.TaskManager;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/task/model/util/SampleDataUtil.java

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new Date("87438807"), new Remark("alexyeoh@gmail.com"),
                    new Location("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Name("Bernice Yu"), new Date("99272758"), new Remark("berniceyu@gmail.com"),
                    new Location("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new Date("93210283"), new Remark("charlotte@yahoo.com"),
                    new Location("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Name("David Li"), new Date("91031282"), new Remark("lidavid@google.com"),
                    new Location("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Name("Irfan Ibrahim"), new Date("92492021"), new Remark("irfan@outlook.com"),
                    new Location("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Name("Roy Balakrishnan"), new Date("92624417"), new Remark("royb@gmail.com"),
                    new Location("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

<<<<<<< HEAD:src/main/java/seedu/address/model/util/SampleDataUtil.java
    public static ReadOnlyTaskManager getSampleAddressBook() {
=======
    public static ReadOnlyTaskManager getSampleTaskManager() {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/task/model/util/SampleDataUtil.java
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
