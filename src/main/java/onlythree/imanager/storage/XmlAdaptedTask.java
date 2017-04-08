package onlythree.imanager.storage;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.parser.DateTimeUtil;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.tag.UniqueTagList.DuplicateTagException;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

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

    @XmlElement(name = "completed")
    private boolean completeElement;

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

        setTagElement(source.getTags());

        setCompleteElement(source.isComplete());
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
            deadlineElement = deadline.getDateTime().format(DateTimeUtil.DATE_TIME_FORMAT);
        }
    }

    /**
     * Sets the XmlElement startDate and endDate to the source task's start and dates if they are present
     */
    private void setStartEndDateElementsIfPresent(Optional<StartEndDateTime> sourceStartEndDateTime) {
        if (sourceStartEndDateTime.isPresent()) {
            StartEndDateTime startEndDateTime = sourceStartEndDateTime.get();
            startDateTimeElement = startEndDateTime.getStartDateTime().format(DateTimeUtil.DATE_TIME_FORMAT);
            endDateTimeElement = startEndDateTime.getEndDateTime().format(DateTimeUtil.DATE_TIME_FORMAT);
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
     * Sets the XmlElement completed to true if the source task's is complete
     */
    private void setCompleteElement(boolean isComplete) {
        completeElement = isComplete;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException, InvalidDurationException {
        final Name name = buildNameFromXmlElement();
        final Optional<Deadline> deadline = buildDeadlineFromXmlElement();
        final Optional<StartEndDateTime> startEndDateTime = buildStartEndDateTimeFromXmlElement();
        final UniqueTagList tagList = buildTagListFromXmlElement();
        final boolean isComplete = buildIsCompleteFromXmlElement();

        return new Task(name, deadline, startEndDateTime, tagList, isComplete);
    }

    /**
     * Returns a Name created from the XmlElement.
     * @throws IllegalValueException if the name does not fulfill the name's constraints
     */
    private Name buildNameFromXmlElement() throws IllegalValueException {
        return new Name(nameElement);
    }

    /**
     * Returns an {@link Optional} wrapping the {@link Deadline} created from the XmlElement.
     * If the XmlElement for deadline does not exist, returns an empty {@link Optional}.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     */
    private Optional<Deadline> buildDeadlineFromXmlElement() {
        // return empty if xml element does not exist
        if (deadlineElement == null) {
            return Optional.empty();
        }

        try {
            // construct Deadline with allowPastDateTime set to true because this is loaded from storage
            Deadline deadline =
                    new Deadline(ZonedDateTime.parse(deadlineElement, DateTimeUtil.DATE_TIME_FORMAT), true);
            return Optional.of(deadline);
        } catch (PastDateTimeException e) {
            throw new AssertionError("Deadline constructed from storage should never throw a PastDateTimeException");
        }
    }

    /**
     * Returns an {@link Optional} wrapping the {@link StartEndDateTime} created from the two XmlElements.
     * If any of the two XmlElements for StartEndDateTime does not exist, returns an empty {@link Optional}.
     * @throws PastDateTimeException should never be thrown because dates in the past are allowed
     * @throws InvalidDurationException if the end DateTime is before or same as the start DateTime
     */
    private Optional<StartEndDateTime> buildStartEndDateTimeFromXmlElement()
            throws InvalidDurationException {
        // a StartEndDateTime cannot be constructed if either XML element do not exist
        if (startDateTimeElement == null || endDateTimeElement == null) {
            return Optional.empty();
        }
        ZonedDateTime startDateTime = ZonedDateTime.parse(startDateTimeElement, DateTimeUtil.DATE_TIME_FORMAT);
        ZonedDateTime endDateTime = ZonedDateTime.parse(endDateTimeElement, DateTimeUtil.DATE_TIME_FORMAT);

        try {
            // construct StartEndDateTime with allowPastDateTime set to true because this is loaded from storage
            StartEndDateTime startEndDateTime = new StartEndDateTime(startDateTime, endDateTime, true);
            return Optional.of(startEndDateTime);
        } catch (PastDateTimeException e) {
            throw new AssertionError("StartEndDateTime constructed from storage should never throw"
                                   + "a PastDateTimeException");
        }
    }

    /**
     * Returns a UniqueTagList of the tags created from every tag XmlElement for the Task
     * @throws IllegalValueException if any of the tags from the XML is invalid
     * @throws DuplicateTagException if any of the tags are duplicates
     */
    private UniqueTagList buildTagListFromXmlElement() throws IllegalValueException, DuplicateTagException {
        final List<Tag> tags = new ArrayList<>();
        for (XmlAdaptedTag adaptedTag : tagElements) {
            tags.add(adaptedTag.toModelType());
        }
        return new UniqueTagList(tags);
    }

    /**
     * Returns a Boolean representing whether the task is complete from the XmlElement.
     */
    private boolean buildIsCompleteFromXmlElement() {
        return completeElement;
    }
}
