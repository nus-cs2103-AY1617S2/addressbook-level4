package seedu.task.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    //    @XmlElement(required = true)
    //    private String startDate;
    //    @XmlElement(required = true)
    //    private String endDate;
    @XmlElement(required = true)
    private boolean recurring;
    @XmlElement(required = true)
    private String frequency;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private ArrayList<RecurringTaskOccurrence> occurrences = new ArrayList<>();


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
        description = source.getDescription().description;
        priority = source.getPriority().value;
        //        startDate = source.getStartTiming().value;
        //        endDate = source.getEndTiming().value;
        recurring = source.isRecurring();
        frequency = source.getFrequency().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        occurrences = source.getOccurrences();
        //        for (RecurringTaskOccurrence occurrence : source.getOccurrences()) {
        //            occurrences.add(new XmlAdaptedOccurrence(occurrence));
        //        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        //        final ArrayList<RecurringTaskOccurrence> taskOccurrences = new ArrayList<>();
        //        for (XmlAdaptedOccurrence occurrence : occurrences) {
        //            taskOccurrences.add(occurrence.toModelTypeOccurrence());
        //        }
        final Description description = new Description(this.description);
        final Priority priority = new Priority(this.priority);
        //        final Timing startDate = new Timing(this.startDate);
        //        final Timing endDate = new Timing(this.endDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final RecurringFrequency frequency = new RecurringFrequency(this.frequency);
        return new Task(description, priority, occurrences, tags, recurring, frequency);
    }
}
