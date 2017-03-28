package seedu.todolist.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.task.EndTime;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.StartTime;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String startTime;

    @XmlElement
    private String endTime;

    @XmlElement(required = true)
    private boolean completed;

    @XmlElement
    private String description;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask. This is the no-arg constructor that is
     * required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        name = source.getName().fullName;
        if (source.getStartTime() != null) {
            startTime = (source.getStartTime() != null ? source.getStartTime().toString() : "");
        } else {
            startTime = "";
        }
        if (source.getEndTime() != null) {
            endTime = (source.getEndTime() != null ? source.getEndTime().toString() : "");
        } else {
            endTime = "";
        }

        completed = source.isComplete();
        description = (source.getDescription() == null ? "" : source.getDescription());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task
     * object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated in the adapted
     *             task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        final Name name = new Name(this.name);
        StartTime startTime;
        EndTime endTime;
        boolean isComplete = this.completed;
        String description;

        if (this.startTime.equals("")) {
            startTime = null;
        } else {
            startTime = new StartTime(this.startTime);
        }

        if (this.endTime.equals("")) {
            endTime = null;
        } else {
            endTime = new EndTime(this.endTime);
        }

        if (this.description.equals("")) {
            description = null;
        } else {
            description = this.description;
            System.out.println(description);
        }

        final UniqueTagList tags = new UniqueTagList(taskTags);
        return TaskParser.parseTask(name, startTime, endTime, tags, isComplete, description);
    }
}
