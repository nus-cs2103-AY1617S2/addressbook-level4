package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Buy groceries"), new Deadline("today"), new Priority("1"),
                    new Instruction("eggs x2, bread x1, milk x4"),
                    new UniqueTagList("chores")),
                new Task(new Title("Fill ice tray"), new Deadline("tonight at 9pm"), new Priority("2"),
                    new Instruction("or tomorrow no coffee, no coffee no work, no work no life"),
                    new UniqueTagList("everyday", "trivialButCanKill")),
                new Task(new Title("Finish React Native course"), new Deadline("floating"), new Priority("3"),
                    new Instruction("clear section 7 and final test"),
                    new UniqueTagList("selfLearn")),
                new Task(new Title("Revise CS3243"), new Deadline("sunday"), new Priority("3"),
                    new Instruction("lecture 9"),
                    new UniqueTagList("school")),
                new Task(new Title("Finish CS2103T issues 23"), new Deadline("tomorrow"), new Priority("5"),
                    new Instruction("clear up for Travis"),
                    new UniqueTagList("project", "school")),
                new Task(new Title("Release v01"), new Deadline("last sunday"), new Priority("-2"),
                    new Instruction("done"),
                    new UniqueTagList("project", "school"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAB = new AddressBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
