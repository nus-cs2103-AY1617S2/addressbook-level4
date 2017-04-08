package seedu.doist.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.FinishedStatus;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.TaskDate;

//@@author A0140887W
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    private static final String NULL_STRING = "null";

    @XmlElement(required = true)
    private String desc;

    @XmlElement(required = true)
    private String priority;

    @XmlElement(required = true)
    private String finishedStatus;

    @XmlElement(required = true)
    private String startDate;

    @XmlElement(required = true)
    private String endDate;

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
        desc = source.getDescription().desc;
        priority = source.getPriority().getPriorityLevel().toString();
        finishedStatus = Boolean.toString(source.getFinishedStatus().getIsFinished());
        startDate = getDateString(source.getDates().getStartDate());
        endDate = getDateString(source.getDates().getEndDate());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    private String getDateString(Date date) {
        if (date != null) {
            return date.toString();
        } else {
            return NULL_STRING;
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        // instead of throwing illegal value exception,
        // can consider just removing the invalid data
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Description name = new Description(this.desc);
        final Priority priority = new Priority(this.priority);
        final FinishedStatus finishedStatus = new FinishedStatus(Boolean.parseBoolean(this.finishedStatus));
        final Date startDate = getDate(this.startDate);
        final Date endDate = getDate(this.endDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);

        return new Task(name, priority, finishedStatus, new TaskDate(startDate, endDate), tags);
    }

    private Date getDate(String dateString) {
        if (dateString.equals(NULL_STRING)) {
            return null;
        } else {
            return TaskDate.parseDate(dateString);
        }
    }
}
