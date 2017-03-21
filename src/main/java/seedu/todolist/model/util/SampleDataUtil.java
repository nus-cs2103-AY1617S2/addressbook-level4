package seedu.todolist.model.util;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.todolist.model.task.parser.TaskParser;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    TaskParser.parseTask(new Name("Reply Email"), null, null, new UniqueTagList("friends")),
                    TaskParser.parseTask(new Name("buy groceries"), null, null,
                                         new UniqueTagList("colleagues", "friends")),
                    TaskParser.parseTask(new Name("exercise"), null, null, new UniqueTagList("neighbours")),
                    TaskParser.parseTask(new Name("project meeting"), null, null, new UniqueTagList("family")),
                    TaskParser.parseTask(new Name("buy present"), null, null, new UniqueTagList("classmates")),
                    TaskParser.parseTask(new Name("lunch with david"), null, null, new UniqueTagList("colleagues"))
                };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoList getSampleToDoList() {
        try {
            ToDoList sampleAB = new ToDoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
