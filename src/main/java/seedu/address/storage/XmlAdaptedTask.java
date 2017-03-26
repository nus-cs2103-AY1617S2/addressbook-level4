package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private String startingTime;

    @XmlElement(required = false)
    private String deadline;

    @XmlElement(required = true)
    private String taskType;

    @XmlElement(required = true)
    private boolean done;

    @XmlElement(required = true)
    private boolean manualToday;

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
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().toString();
        done = source.isDone();
        manualToday = source.isManualToday();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        if (source.getStartingTime().isPresent()) {
            startingTime = Long.toString(source.getStartingTime().get().getDate().getTime());
        }
        if (source.getDeadline().isPresent()) {
            deadline = Long.toString(source.getDeadline().get().getDate().getTime());
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

        final Name name = new Name(this.name);
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final UniqueTagList tags = new UniqueTagList(taskTags);

        Optional<DateTime> deadline = Optional.empty();
        Optional<DateTime> startingTime = Optional.empty();
        if (this.deadline != null) {
            deadline = Optional.of(new DateTime(new Date(Long.parseLong(this.deadline))));
        }
        if (this.startingTime != null) {
            startingTime = Optional.of(new DateTime(new Date(Long.parseLong(this.startingTime))));
        }

        return Task.createTask(name, tags, deadline, startingTime, done, manualToday);
    }
}
