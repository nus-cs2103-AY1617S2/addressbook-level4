package seedu.address.storage;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String deadline;

    @XmlElement
    private String eventStartDate;

    @XmlElement
    private String eventEndDate;

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
        name = source.getName().value;

        if (source.getDeadline().isPresent()) {
            deadline = source.getDeadline().get().getValue().format(ParserUtil.DATE_TIME_FORMAT);
        }

        if (source.getStartEndDateTime().isPresent()) {
            StartEndDateTime startEndDateTime = source.getStartEndDateTime().get();
            eventStartDate = startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT);
            eventEndDate = startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT);
        }

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

        Optional<Deadline> deadline = Optional.empty();
        Optional<StartEndDateTime> startEndDateTime = Optional.empty();

        if (this.deadline != null) {
            deadline = Optional.of(new Deadline(ZonedDateTime.parse(this.deadline, ParserUtil.DATE_TIME_FORMAT)));
        }

        if (this.eventStartDate != null && this.eventEndDate != null) {
            // to make further checks because file may be corrupted ane field is missing
            ZonedDateTime startDateTime = ZonedDateTime.parse(eventStartDate, ParserUtil.DATE_TIME_FORMAT);
            ZonedDateTime endDateTime = ZonedDateTime.parse(eventEndDate, ParserUtil.DATE_TIME_FORMAT);
            startEndDateTime = Optional.of(new StartEndDateTime(startDateTime, endDateTime));
        }


        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, deadline, startEndDateTime, tags);
    }
}
