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
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;

// @@author A0140023E
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(name = "name", required = true)
    private String nameElement;

    @XmlElement(name = "deadline")
    private String deadlineElement;

    @XmlElement(name = "startDate")
    private String startDateTimeElement;

    @XmlElement(name = "endDate")
    private String endDateTimeElement;

    @XmlElement(name = "tag")
    private List<XmlAdaptedTag> tagElements = new ArrayList<>();

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
        setNameElement(source.getName().value);

        setDeadlineElementIfPresent(source.getDeadline());
        // the start and end date elements must be set together because they cannot exist separately
        setStartEndDateElementsIfPresent(source.getStartEndDateTime());

        // TODO do I need to check if Deadline exists then StartEndDateTime cannot exists and vice versa

        setTagElement(source.getTags());
    }

    /**
     * Sets the XmlElement name to the source task's name
     */
    private void setNameElement(String sourceName) {
        nameElement = sourceName;
    }

    /**
     * Sets the XmlElement name to the source task's deadline if it is present
     */
    private void setDeadlineElementIfPresent(Optional<Deadline> sourceDeadline) {
        if (sourceDeadline.isPresent()) {
            Deadline deadline = sourceDeadline.get();
            deadlineElement = deadline.getValue().format(ParserUtil.DATE_TIME_FORMAT);
        }
    }

    /**
     * Sets the XmlElement startDate and endDate to the source task's start and dates if they are present
     */
    private void setStartEndDateElementsIfPresent(Optional<StartEndDateTime> sourceStartEndDateTime) {
        if (sourceStartEndDateTime.isPresent()) {
            StartEndDateTime startEndDateTime = sourceStartEndDateTime.get();
            startDateTimeElement = startEndDateTime.getStartDateTime().format(ParserUtil.DATE_TIME_FORMAT);
            endDateTimeElement = startEndDateTime.getEndDateTime().format(ParserUtil.DATE_TIME_FORMAT);
        }
    }

    /**
     * Sets each tag XmlElement to the source task's tags from its unique tag list
     */
    private void setTagElement(UniqueTagList sourceTagList) {
        tagElements = new ArrayList<>();
        for (Tag tag : sourceTagList) {
            tagElements.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException, PastDateTimeException, InvalidDurationException {
        // TODO get seems to not be a good name, as it is usually used for getters
        final Name name = getNameFromXmlElement();
        final Optional<Deadline> deadline = getDeadlineFromXmlElement();
        final Optional<StartEndDateTime> startEndDateTime = getStartEndDateTimeFromXmlElement();
        final UniqueTagList tagList = getTagListFromXmlElement();

        return new Task(name, deadline, startEndDateTime, tagList);
    }

    /**
     * Returns a Name created from the XmlElement.
     * @throws IllegalValueException if the name does not fulfill the name's constraints
     */
    private Name getNameFromXmlElement() throws IllegalValueException {
        return new Name(nameElement);
    }

    /**
     * Returns an {@link Optional} wrapping the {@link Deadline} created from the XmlElement.
     * If the XmlElement for deadline does not exist, returns an empty {@link Optional}.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     */
    private Optional<Deadline> getDeadlineFromXmlElement() throws PastDateTimeException {
        // return empty if xml element does not exist
        if (deadlineElement == null) {
            return Optional.empty();
        }
        // construct Deadline with allowPastDateTime set to true because this is loaded from storage
        Deadline deadline = new Deadline(ZonedDateTime.parse(deadlineElement, ParserUtil.DATE_TIME_FORMAT), true);
        return Optional.of(deadline);
    }

    /**
     * Returns an {@link Optional} wrapping the {@link StartEndDateTime} created from the two XmlElements.
     * If any of the two XmlElements for StartEndDateTime does not exist, returns an empty {@link Optional}.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     */
    private Optional<StartEndDateTime> getStartEndDateTimeFromXmlElement()
            throws PastDateTimeException, InvalidDurationException {
        // return empty if either xml element does not exist
        if (startDateTimeElement == null || endDateTimeElement == null) {
            return Optional.empty();
        }
        ZonedDateTime startDateTime = ZonedDateTime.parse(startDateTimeElement, ParserUtil.DATE_TIME_FORMAT);
        ZonedDateTime endDateTime = ZonedDateTime.parse(endDateTimeElement, ParserUtil.DATE_TIME_FORMAT);
        // construct StartEndDateTime with allowPastDateTime set to true because this is loaded from storage
        StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
        return Optional.of(startEndDateTime);
    }

    /**
     * Returns a UniqueTagList of the tags created from every tag XmlElement for the Task
     * @throws IllegalValueException if any of the tags from the XML is invalid
     * @throws DuplicateTagException if any of the tags are duplicates
     */
    private UniqueTagList getTagListFromXmlElement() throws IllegalValueException, DuplicateTagException {
        final List<Tag> tags = new ArrayList<>();
        for (XmlAdaptedTag adaptedTag : tagElements) {
            tags.add(adaptedTag.toModelType());
        }
        return new UniqueTagList(tags);
    }
}
