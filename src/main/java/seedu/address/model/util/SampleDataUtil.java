package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicatePersonException;

//@@author A0163848R
public class SampleDataUtil {
    
    private static final int SAMPLE_SIZE = 50;
    
    Random r;
    
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
    
    public static ReadOnlyTaskManager getSampleAddressBook() {
        YTomorrow sampleAB = new YTomorrow();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            try {
                sampleAB.addPerson(generateRandomTask());
            } catch (DuplicatePersonException e) {
                i--;
                e.printStackTrace();
            }
        }
        return sampleAB;
    }
    
}
