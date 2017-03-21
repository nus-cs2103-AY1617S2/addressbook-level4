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
                TaskParser.parseTask(new Name("Reply Bob's Email"), null, null, new UniqueTagList("work")),
                TaskParser.parseTask(new Name("Buy Groceries"), null, null, new UniqueTagList("home", "errand")),
                TaskParser.parseTask(new Name("Project Meet"), null, null, new UniqueTagList("school")),
                TaskParser.parseTask(new Name("Lunch With David"), null, null, new UniqueTagList("family")),
                TaskParser.parseTask(new Name("Look for Birthday Present"), null, null, new UniqueTagList("errand")),
                TaskParser.parseTask(new Name("Finish Tutorial"), null, null, new UniqueTagList("school"))
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
