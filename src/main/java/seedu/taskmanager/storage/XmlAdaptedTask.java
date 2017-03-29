package seedu.taskmanager.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.model.category.UniqueCategoryList;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskname;
    @XmlElement(required = true)
    private String startdate;
    @XmlElement(required = true)
    private String starttime;
    @XmlElement(required = true)
    private String enddate;
    @XmlElement(required = true)
    private String endtime;
    @XmlElement(required = true)
    private String markedCompleted;
    @XmlElement
    private List<XmlAdaptedCategory> categorised = new ArrayList<>();

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
        taskname = source.getTaskName().fullTaskName;
        startdate = source.getStartDate().value;
        starttime = source.getStartTime().value;
        enddate = source.getEndDate().value;
        endtime = source.getEndTime().value;
        markedCompleted = source.getIsMarkedAsComplete().toString();
        categorised = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categorised.add(new XmlAdaptedCategory(category));
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
        final List<Category> taskCategories = new ArrayList<>();
        for (XmlAdaptedCategory category : categorised) {
            taskCategories.add(category.toModelType());
        }

        final TaskName taskName = new TaskName(this.taskname);
        final StartDate startDate = new StartDate(this.startdate);
        final StartTime startTime = new StartTime(this.starttime);
        final EndDate endDate = new EndDate(this.enddate);
        final EndTime endTime = new EndTime(this.endtime);
        final Boolean markedCompleted = new Boolean(this.markedCompleted);
        final UniqueCategoryList categories = new UniqueCategoryList(taskCategories);
        return new Task(taskName, startDate, startTime, endDate, endTime, markedCompleted, categories);
    }
}
