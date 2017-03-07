package seedu.taskmanager.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
//import seedu.taskmanager.model.person.Address;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Time;
import seedu.taskmanager.model.task.ReadOnlyTask;
//import seedu.taskmanager.model.tag.Tag;
//import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskname;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String date;
//    @XmlElement(required = true)
//    private String address;

//    @XmlElement
//    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        taskname = source.getTaskName().fullTaskName;
        time = source.getTime().value;
        date = source.getDate().value;
//        address = source.getAddress().value;
//        tagged = new ArrayList<>();
//        for (Tag tag : source.getTags()) {
//            tagged.add(new XmlAdaptedTag(tag));
//        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
//        final List<Tag> personTags = new ArrayList<>();
//        for (XmlAdaptedTag tag : tagged) {
//            personTags.add(tag.toModelType());
//        }
        final TaskName taskname = new TaskName(this.taskname);
        final Time time = new Time(this.time);
        final Date date = new Date(this.date);
//        final Address address = new Address(this.address);
//        final UniqueTagList tags = new UniqueTagList(personTags);
        return new Task(taskname, time, date);
    }
}
