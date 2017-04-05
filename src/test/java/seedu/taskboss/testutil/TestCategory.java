package seedu.taskboss.testutil;

import java.util.List;

import com.google.common.collect.Lists;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;

/**
 * A mutable category object. For testing only.
 */
public class TestCategory extends Category {
    private int taskCount;
    private List<TestTask> taskList;

    public TestCategory(String name, int taskCount) throws IllegalValueException {
        super(name);
        this.taskCount = taskCount;
    }

    public TestCategory(String name, TestTask... tasks) throws IllegalValueException {
        super(name);
        taskList = Lists.newArrayList(tasks);
    }

    public void setTaskCount(int count) {
        taskCount = count;
    }
    
    public void setTaskList(TestTask...tasks) {
        taskList = Lists.newArrayList(tasks);
    }

    public int getTaskCount() {
        return taskCount;
    }

    public List<TestTask> getTaskList() {
        return taskList;
    }
}
