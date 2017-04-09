package seedu.task.model.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.YTomorrow;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Group;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0163848R
public class SampleDataUtil {

    private static final int SAMPLE_SIZE = 50;

    Random r;

    /**
     * @param Number of tasks to generate
     * @return Array of randonly-generated tasks
     */
    public static ReadOnlyTask[] getSampleTasks(int n) {
        Set<Task> generated = new HashSet<Task>();
        for (int i = 0; i < n; i++) {
            if (!generated.add(generateRandomTask())) {
                i--;
            }
        }
        return generated.toArray(new Task[n]);
    }

    /**
     * @return a randomly-generated task
     * @throws IllegalValueException
     */
    private static Task generateRandomTask() {
        Random r = new Random();
        boolean addStartDate = r.nextBoolean();
        boolean addEndDate = r.nextBoolean();

        try {

            return Task.factory(
                    new Name(TaskNameGenerator.doAction(r).with().maybe(0.75f).in().maybe(0.75f).toString()),
                    addStartDate && addEndDate ?  TaskDateGenerator.getStartDate(r) : null,
                    addEndDate ? TaskDateGenerator.getEndDate(r) : null,
                    new Group(TaskGroupGenerator.getGroup(r)),
                    UniqueTagList.build(r.nextBoolean() ? Tag.TAG_COMPLETE : Tag.TAG_INCOMPLETE));

        } catch (IllegalValueException e) {
            return generateRandomTask();
        }
    }
    //@@author

    public static ReadOnlyTaskManager getSampleTaskManager() {
        YTomorrow sampleAB = new YTomorrow();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            try {
                sampleAB.addPerson(generateRandomTask());
            } catch (DuplicateTaskException e) {
                i--;
                e.printStackTrace();
            }
        }
        return sampleAB;
    }

}
