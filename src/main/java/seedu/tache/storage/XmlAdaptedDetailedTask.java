package seedu.tache.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.Time;

/**
 * JAXB-friendly version of the DetailedTask.
 */
public class XmlAdaptedDetailedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String startDate;

    @XmlElement
    private String endDate;

    @XmlElement
    private String startTime;

    @XmlElement
    private String endTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedDetailedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDetailedTask() {}


    /**
     * Converts a given DetailedTask into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDetailedTask
     */

    public XmlAdaptedDetailedTask(ReadOnlyDetailedTask source) {
        name = source.getName().fullName;
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted detailed task object into the model's DetailedTask object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted detailed task
     */
    public DetailedTask toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Date startDate = new Date(this.startDate);
        final Date endDate = new Date(this.endDate);
        final Time startTime = new Time(this.startTime);
        final Time endTime = new Time(this.endTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new DetailedTask(name, startDate, endDate, startTime, endTime, tags);
    }
}
