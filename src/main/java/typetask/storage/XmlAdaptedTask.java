package typetask.storage;

import javax.xml.bind.annotation.XmlElement;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Priority;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
  //@@author A0139926R
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String endDate;
  //@@author A0144902L
    @XmlElement(required = true)
    private Boolean isCompleted;
    @XmlElement(required = true)
    private String priority;
  //@@author

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

  //@@author A0139926R
    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        if (source.getEndDate() != null) {
            endDate = source.getEndDate().value;
        } else {
            endDate = "";
        }
        //@@author A0144902L
        priority = source.getPriority().value;
        if (source.getPriority() != null) {
            priority = source.getPriority().value;
        } else {
            priority = "Low";
        }
        isCompleted = source.getIsCompleted();
    }
  //@@author A0139926R
    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Name name = new Name(this.name);
        final DueDate date = new DueDate(this.date);
        final DueDate endDate = new DueDate(this.endDate);
        //@@author A0144902L
        final Priority priority = new Priority(this.priority);
        boolean isCompleted = false;
        if (this.isCompleted != null) {
            isCompleted = this.isCompleted;
        }
        return new Task(name, date, endDate, isCompleted, priority);
    }
}
