package seedu.ezdo.model.util;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Email;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy one cherry"), new Priority("1"), new Email("1alexyeoh@gmail.com"),
                    new StartDate("Blk 130 Geylang Street 29, #06-40"),
                    new DueDate("Blk 130 Geylang Street 29, #06-40"),
                    new UniqueTagList("groceries")),
                new Task(new Name("Study for two midterms"), new Priority("2"), new Email("2berniceyu@gmail.com"),
                    new StartDate("Blk 230 Lorong 3 Serangoon Gardens, #07-18"),
                    new DueDate("Blk 230 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Buy ps three"), new Priority("3"), new Email("3charlotte@yahoo.com"),
                    new StartDate("Blk 311 Ang Mo Kio Street 74, #11-04"),
                    new DueDate("Blk 311 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("personal")),
                new Task(new Name("Visit four neighbours"), new Priority("2"), new Email("4lidavid@google.com"),
                    new StartDate("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new DueDate("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for five finals"), new Priority("3"), new Email("5irfan@outlook.com"),
                    new StartDate("Blk 547 Tampines Street 20, #17-35"),
                    new DueDate("Blk 547 Tampines Street 20, #17-35"),
                    new UniqueTagList("school", "exams")),
                new Task(new Name("Prepare six presentations"), new Priority("3"), new Email("6royb@gmail.com"),
                    new StartDate("Blk 645 Aljunied Street 85, #11-31"),
                    new DueDate("Blk 645 Aljunied Street 85, #11-31"),
                    new UniqueTagList("school", "exams"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyEzDo getSampleEzDo() {
        try {
            EzDo sampleEzDo = new EzDo();
            for (Task sampleTask : getSampleTasks()) {
                sampleEzDo.addTask(sampleTask);
            }
            return sampleEzDo;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
