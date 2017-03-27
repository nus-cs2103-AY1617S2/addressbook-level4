package seedu.taskboss.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priorityLevel;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String information;
    @XmlElement (required = true)
    private String recurrence;

    @XmlElement
    private List<XmlAdaptedCategory> addedToCategory = new ArrayList<>();

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
        //@@author A0144904H
        priorityLevel = source.getPriorityLevel().input;

        //@@author
        startDateTime = source.getStartDateTime().value;
        endDateTime = source.getEndDateTime().value;
        information = source.getInformation().value;
        recurrence = source.getRecurrence().toString();
        addedToCategory = new ArrayList<>();
        for (Category category : source.getCategories()) {
            addedToCategory.add(new XmlAdaptedCategory(category));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Category> taskCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : addedToCategory) {
            taskCategories.add(category.toModelType());
        }
        final Name name = new Name(this.name);
        final PriorityLevel priorityLevel = new PriorityLevel(this.priorityLevel);
        final DateTime startDateTime = new DateTime(this.startDateTime);
        final DateTime endDateTime = new DateTime(this.endDateTime);
        final Information information = new Information(this.information);
        final Recurrence recurrence = new Recurrence(Frequency.valueOf(this.recurrence));
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        return new Task(name, priorityLevel, startDateTime, endDateTime, information,
                recurrence, categories);
    }
}
