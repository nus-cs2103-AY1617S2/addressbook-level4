package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Title;

/**
 *
 */
public class TaskBuilder {

    private TestTask person;

    public TaskBuilder() {
        this.person = new TestTask();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(TestTask personToCopy) {
        this.person = new TestTask(personToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Title(name));
        return this;
    }

    public TaskBuilder withLabels(String ... labels) throws IllegalValueException {
        person.setLabels(new UniqueLabelList());
        for (String label: labels) {
            person.getLabels().add(new Label(label));
        }
        return this;
    }

    public TaskBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Deadline(address));
        return this;
    }

    public TestTask build() {
        return this.person;
    }

}
