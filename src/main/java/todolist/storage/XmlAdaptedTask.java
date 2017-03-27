package todolist.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import todolist.commons.exceptions.IllegalValueException;
import todolist.model.tag.Tag;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String urgencyLevel;
    @XmlElement(required = true)
    private String description;

    @XmlElement
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
        title = source.getTitle().title;
        venue = source.getVenue().isPresent() ? source.getVenue().get().toString() : "";
        startTime = source.getStartTime().isPresent() ? source.getStartTime().get().toString() : "";
        endTime = source.getEndTime().isPresent() ? source.getEndTime().get().toString() : "";
        urgencyLevel = source.getUrgencyLevel().isPresent() ? source.getUrgencyLevel().get().toString() : "";
        description = source.getDescription().isPresent() ? source.getDescription().get().toString() : "";
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted Task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Venue venue = this.venue == "" ? null : new Venue(this.venue);
        final StartTime startTime = (this.startTime.length()>0) ? new StartTime(this.startTime) : null;
        final EndTime endTime = this.endTime.length()>0 ? new EndTime(this.endTime) : null;
        final UrgencyLevel urgencyLevel = this.urgencyLevel != "" ? new UrgencyLevel(this.urgencyLevel) : null;
        final Description description = this.description != "" ? new Description(this.description) : null;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(title, venue, startTime, endTime, urgencyLevel, description, tags);
    }
}
