package project.taskcrusher.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.ReadOnlyTask;
import project.taskcrusher.model.task.Task;

//@@author A0127737X
/**
 * JAXB-friendly version of Task.
 */
public class XmlAdaptedTask {

    /* Attributes inherited from UserToDo*/
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private boolean isComplete;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /* Task-specific attributes */
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private boolean isOverdue;

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
        name = source.getName().name;
        priority = source.getPriority().priority;
        description = source.getDescription().description;
        isComplete = source.isComplete();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        deadline = source.getDeadline().deadline;
        isOverdue = source.isOverdue();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Description description = new Description(this.description);
        final UniqueTagList tags = new UniqueTagList(taskTags);

        final Deadline deadline = new Deadline(this.deadline, Deadline.IS_LOADING_FROM_STORAGE);
        return new Task(name, deadline, priority, description, tags, isComplete, isOverdue);
    }
}
