package seedu.bulletjournal.model.util;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.BeginTime;
import seedu.bulletjournal.model.task.Deadline;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.model.task.UniqueTaskList.DuplicatePersonException;

public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        try {
            return new Task[] {
                new Task(new TaskName("Alex Yeoh"), new Deadline("87438807"), new Status("undone"),
                    new BeginTime("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new TaskName("Bernice Yu"), new Deadline("99272758"), new Status("undone"),
                    new BeginTime("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new TaskName("Charlotte Oliveiro"), new Deadline("93210283"),
                    new Status("undone"),
                    new BeginTime("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new TaskName("David Li"), new Deadline("91031282"), new Status("undone"),
                    new BeginTime("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new TaskName("Irfan Ibrahim"), new Deadline("92492021"), new Status("undone"),
                    new BeginTime("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new TaskName("Roy Balakrishnan"), new Deadline("92624417"), new Status("undone"),
                    new BeginTime("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTodoList getSampleAddressBook() {
        try {
            TodoList sampleAB = new TodoList();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
