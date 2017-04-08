package seedu.tache.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.recurstate.RecurState;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private String startDateTime;

    @XmlElement(required = false)
    private String endDateTime;

    @XmlElement(required = false)
    private boolean isActive;

    @XmlElement(required = false)
    private boolean isTimed;

    @XmlElement(required = true)
    private RecurState recurState;

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
        name = source.getName().fullName;
        if (source.getStartDateTime().isPresent()) {
            startDateTime = source.getStartDateTime().get().getAmericanDateTime();
        }
        if (source.getEndDateTime().isPresent()) {
            endDateTime = source.getEndDateTime().get().getAmericanDateTime();
        }
        isActive = source.getActiveStatus();
        isTimed = source.getTimedStatus();
        recurState = source.getRecurState();
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
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Optional<DateTime> startDateTime;
        if (this.startDateTime != null) {
            startDateTime = Optional.of(new DateTime(this.startDateTime));
        } else {
            startDateTime = Optional.empty();
        }
        final Optional<DateTime> endDateTime;
        if (this.endDateTime != null) {
            endDateTime = Optional.of(new DateTime(this.endDateTime));
        } else {
            endDateTime = Optional.empty();
        }
        final boolean isActive = this.isActive;
        final boolean isTimed = this.isTimed;
        final RecurState recurState = this.recurState;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, startDateTime, endDateTime, tags, isTimed, isActive, recurState.isRecurring(),
                            recurState.getRecurInterval(), recurState.getRecurCompletedList());
    }
}
