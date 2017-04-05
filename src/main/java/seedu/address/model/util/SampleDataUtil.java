package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Email;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;

public class SampleDataUtil {
    //@@author A0163848R
    public static Task[] getSamplePersons() {
        /*
        final int n = 50;
        Task[] generated = new Task[n];
        for (int i = 0; i < n; i++) {
            generated[i] = generateRandomTask();
        }
        return generated;
        */
        return new Task[] { randomTaskGenerator() };
    }
    
    /**
     * @return a randomly-generated task
     * @throws IllegalValueException
     */
    private static Task randomTaskGenerator() {
        try {
            return Task.factory(
                    new Name("A"),
                    new StartDate("today"),
                    new EndDate("tomorrow"),
                    new Email("a@e"),
                    new Group("ayy"),
                    UniqueTagList.build("incomplete"));
        } catch (IllegalValueException e) {
            return randomTaskGenerator();
        }
    }
    //@@author
    
    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            YTomorrow sampleAB = new YTomorrow();
            for (Task samplePerson : getSamplePersons()) {
                sampleAB.addPerson(samplePerson);
            }
            return sampleAB;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
