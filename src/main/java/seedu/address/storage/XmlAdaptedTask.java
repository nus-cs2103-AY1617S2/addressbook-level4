package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String startTime;

    @XmlElement
    private List<XmlAdaptedLabel> labeled = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        title = source.getTitle().title;
        startTime = source.getStartTime().toString();
        deadline = source.getDeadline().toString();
        labeled = new ArrayList<>();
        for (Label label : source.getLabels()) {
            labeled.add(new XmlAdaptedLabel(label));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException, IllegalDateTimeValueException {
        final List<Label> taskLabels = new ArrayList<>();
        for (XmlAdaptedLabel label : labeled) {
            taskLabels.add(label.toModelType());
        }
        final Title title = new Title(this.title);
        final Deadline startTime = new Deadline(this.startTime);
        final Deadline deadline = new Deadline(this.deadline);
        final UniqueLabelList labels = new UniqueLabelList(taskLabels);
        return new Task(title, startTime, deadline, labels);
    }
}
