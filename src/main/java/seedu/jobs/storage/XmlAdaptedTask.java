package seedu.jobs.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String period;
    @XmlElement(required = true)
    private String isCompleted;
    @XmlElement(required = true)
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        description = source.getDescription().value;
        period = Integer.toString(source.getPeriod().value);
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        isCompleted = String.valueOf(source.isCompleted());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     * @throws IllegalTimeException
     */
    public Task toModelType() throws IllegalValueException, IllegalTimeException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(Optional.of(this.name));
        final Time startTime = new Time(Optional.of(this.startTime));
        final Time endTime = new Time(Optional.of(this.endTime));
        final Description description = new Description(Optional.of(this.description));
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Period period = new Period(Optional.of(this.period));
        final boolean isCompleted = (this.isCompleted.equals("true")) ? true: false;
        return new Task(name, startTime, endTime, description, tags, period,isCompleted);
    }
}
