package seedu.taskmanager.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String description;
    // @@author A0114269E
    @XmlElement(required = true)
    private String status;
    // @@author

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
        title = source.getTitle().value;
        startDate = source.getStartDate().isPresent() ? source.getStartDate().get().toFullDateString() : null;
        endDate = source.getEndDate().isPresent() ? source.getEndDate().get().toFullDateString() : null;
        description = source.getDescription().isPresent() ? source.getDescription().get().value : null;
        status = source.getStatus().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    // @@author A0140032E
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final StartDate startDate = this.startDate == null ? null : new StartDate(this.startDate);
        final EndDate endDate = this.endDate == null ? null : new EndDate(this.endDate);
        final Description description = this.description == null ? null : new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        // @@author A0114269E
        final Status status = new Status(this.status);
        // @@author
        return new Task(title, Optional.ofNullable(startDate), Optional.ofNullable(endDate),
                Optional.ofNullable(description), status, tags);
    }
    // @@author
}
