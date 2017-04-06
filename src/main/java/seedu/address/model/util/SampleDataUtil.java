package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

//@@author A0163848R

public class SampleDataUtil {
    
    private static final int SAMPLE_SIZE = 50;
    
    Random r;
    
    public static Iterable<Task> getSampleTasks(int n) {
        List<Task> generated = new ArrayList<Task>(n);
        for (int i = 0; i < n; i++) {
            Task t = generateRandomTask();
            if (!generated.contains(t)) {
                generated.add(t);
            } else {
                i--;
            }
        }
        return generated;
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
                    addStartDate && addEndDate ?  new StartDate(TaskDateGenerator.getStartDate(r)) : null,
                    addEndDate ? new EndDate(TaskDateGenerator.getEndDate(r)) : null,
                    new Group(TaskGroupGenerator.getGroup(r)),
                    UniqueTagList.build(r.nextBoolean() ? Tag.TAG_COMPLETE : Tag.TAG_INCOMPLETE));
          
        } catch (IllegalValueException e) {
            return generateRandomTask();
        }
    }
    //@@author
    
    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            YTomorrow sampleAB = new YTomorrow();
            for (Task samplePerson : getSampleTasks(SAMPLE_SIZE)) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
    
}
