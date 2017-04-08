package seedu.task.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.task.model.Model;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;

/**
 * A utility class to generate test data.
 */

public class TestDataHelper {

    public Task allocate() throws Exception {
        Name name = new Name("Allocate time for exercise");
        Date startDate = new Date("9-03-2017 2300");
        Date endDate = new Date("10-03-2017 2300");
        Remark remark = new Remark("Just do it");
        Location privateLocation = new Location("111, alpha street");
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("longertag2");
        UniqueTagList tags = new UniqueTagList(tag1, tag2);
        return new Task(name, startDate, endDate, remark, privateLocation, tags, false, "");
    }

    /**
     * Generates a valid task using the given seed. Running this function with
     * the same parameter values guarantees the returned task will have the same
     * state. Each unique seed will generate a unique Task object.
     *
     * @param seed
     *            used to generate the task data field values
     */
    public Task generateTask(int seed) throws Exception {
        return new Task(new Name("Task " + seed), new Date(seed + "-05-15 2000"), new Date(seed + "-05-15 2001"),
                new Remark(seed + "@email"), new Location("House of " + seed),
                new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))), false, "");
    }

    /** Generates the correct add command based on the task given */
    public String generateAddCommand(Task p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");
        cmd.append(p.getName().toString());
        cmd.append(" s/").append(p.getStartDate());
        cmd.append(" e/").append(p.getEndDate());
        cmd.append(" r/").append(p.getRemark());
        cmd.append(" l/").append(p.getLocation());

        UniqueTagList tags = p.getTags();
        for (Tag t : tags) {
            cmd.append(" t/").append(t.tagName);
        }

        return cmd.toString();
    }

    /**
     * Generates an TaskManager with auto-generated tasks.
     */
    public TaskManager generateTaskManager(int numGenerated) throws Exception {
        TaskManager taskManager = new TaskManager();
        addToTaskManager(taskManager, numGenerated);
        taskManager.sortTaskList();
        return taskManager;
    }

    /**
     * Generates an TaskManager based on the list of Tasks given.
     */
    public TaskManager generateTaskManager(List<Task> tasks) throws Exception {
        TaskManager taskManager = new TaskManager();
        addToTaskManager(taskManager, tasks);
        return taskManager;
    }

    /**
     * Adds auto-generated Task objects to the given TaskManager
     *
     * @param taskManager
     *            The TaskManager to which the Tasks will be added
     */
    public void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
        addToTaskManager(taskManager, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given TaskManager
     */
    public void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
        for (Task p : tasksToAdd) {
            taskManager.addTaskToFront(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     *
     * @param model
     *            The model to which the Tasks will be added
     */
    public void addToModel(Model model, int numGenerated) throws Exception {
        addToModel(model, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    public void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
        for (Task p : tasksToAdd) {
            model.addTask(p);
        }
    }

    /**
     * Generates a list of Tasks based on the flags.
     */
    public List<Task> generateTaskList(int numGenerated) throws Exception {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= numGenerated; i++) {
            tasks.add(generateTask(i));
        }
        return tasks;
    }

    public List<Task> generateTaskList(Task... tasks) {
        List<Task> toReturn = Arrays.asList(tasks);
        Collections.sort(toReturn);
        return toReturn;
    }

    /**
     * Generates a Task object with given name. Other fields will have some
     * dummy values.
     */
    public Task generateTaskWithName(String name) throws Exception {
        return new Task(new Name(name), new Date("4-05-2015"), new Date("05-05-2015"), new Remark("1@email"),
                new Location("House of 1"), new UniqueTagList(new Tag("tag")), false, "");
    }

}
