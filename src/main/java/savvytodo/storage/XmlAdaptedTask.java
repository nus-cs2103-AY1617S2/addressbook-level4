package savvytodo.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.category.Category;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;
import savvytodo.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String priority;
    @XmlElement
    private String description;
    @XmlElement
    private String location;
    @XmlElement
    private DateTime dateTime;
    @XmlElement
    private Recurrence recurrence;
    @XmlElement
    private boolean status;

    @XmlElement
    private List<XmlAdaptedCategory> categorized = new ArrayList<>();

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
        priority = source.getPriority().value;
        description = source.getDescription().value;
        location = source.getLocation().value;
        dateTime = source.getDateTime();
        recurrence = source.getRecurrence();
        status = source.isCompleted().value;
        categorized = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categorized.add(new XmlAdaptedCategory(category));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Category> taskCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : categorized) {
            taskCategories.add(category.toModelType());
        }
        final Name name = new Name(this.name);
        final Priority priority = new Priority(this.priority);
        final Description description = new Description(this.description);
        final Location location = new Location(this.location);
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        final DateTime dateTime = new DateTime(this.dateTime.startValue, this.dateTime.endValue);
        final Recurrence recurrence = new Recurrence(this.recurrence.type.toString(), this.recurrence.occurences);
        final Status status = new Status(this.status);

        return new Task(name, priority, description, location, categories, dateTime, recurrence, status);
    }
}
