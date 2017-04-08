package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.TaskTime;

//@@author A0146757R
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String taskDate;
    @XmlElement(required = true)
    private String taskStartTime;
    @XmlElement(required = true)
    private String taskEndTime;
    @XmlElement(required = true)
    private String taskDescription;
    @XmlElement(required = true)
    private String taskStatus;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    // @@author
    /**
     * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
     * required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {

        if (source.getTaskName() != null) {
            taskName = source.getTaskName().fullTaskName;
        }
        if (source.getTaskDate() != null) {
            taskDate = source.getTaskDate().value;
        }
        if (source.getTaskStartTime() != null) {
            taskStartTime = source.getTaskStartTime().value;
        }
        if (source.getTaskEndTime() != null) {
            taskEndTime = source.getTaskEndTime().value;
        }
        if (source.getTaskDescription() != null) {
            taskDescription = source.getTaskDescription();
        }
        if (source.getTaskStatus() != null) {
            taskStatus = source.getTaskStatus().toString();
        }
        tagged = new ArrayList<>();

        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's task
     * object.
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final TaskName taskName = new TaskName(this.taskName);
        final Optional<TaskDate> taskDateOpt;
        final Optional<TaskTime> taskStartTimeOpt;
        final Optional<TaskTime> taskEndTimeOpt;
        final Optional<String> taskDescriptionOpt;
        final Optional<TaskStatus> taskStatusOpt;
        if (this.taskDate == null) {
            taskDateOpt = Optional.ofNullable(null);
        } else {
            taskDateOpt = Optional.ofNullable(new TaskDate(this.taskDate));
        }

        if (this.taskStartTime == null) {
            taskStartTimeOpt = Optional.ofNullable(null);
        } else {
            taskStartTimeOpt = Optional.ofNullable(new TaskTime(this.taskStartTime));
        }

        if (this.taskEndTime == null) {
            taskEndTimeOpt = Optional.ofNullable(null);
        } else {
            taskEndTimeOpt = Optional.ofNullable(new TaskTime(this.taskEndTime));
        }

        if (this.taskDescription == null) {
            taskDescriptionOpt = Optional.ofNullable(null);
        } else {
            taskDescriptionOpt = Optional.ofNullable(this.taskDescription);
        }

        if (this.taskStatus == null) {
            taskStatusOpt = Optional.ofNullable(null);
        } else {
            taskStatusOpt = Optional.ofNullable(new TaskStatus(this.taskStatus));
        }
        final UniqueTagList listOfTags = new UniqueTagList(taskTags);

        return new Task(taskName, taskDateOpt, taskStartTimeOpt, taskEndTimeOpt, taskDescriptionOpt, taskStatusOpt,
                listOfTags);

    }
}
