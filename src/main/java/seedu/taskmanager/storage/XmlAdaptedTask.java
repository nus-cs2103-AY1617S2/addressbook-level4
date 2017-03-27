package seedu.taskmanager.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartTime;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.TaskName;
//import seedu.taskmanager.model.category.Category;
//import seedu.taskmanager.model.category.UniqueCategoryList;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskname;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String endTime;

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
        startDate = source.getStartDate().value;
        startTime = source.getStartTime().value;
        endDate = source.getEndDate().value;
        endTime = source.getEndTime().value;
        // categorised = new ArrayList<>();
        // for (Category category : source.getCategories()) {
        // categorised.add(new XmlAdaptedCategory(category));
        // }
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
        // final List<Category> taskCategories = new ArrayList<>();
        // for (XmlAdaptedCategory category : categorised) {
        // taskCategories.add(category.toModelType());
        // }
        final TaskName taskname = new TaskName(this.taskname);
        final StartDate startdate = new StartDate(this.startDate);
        final StartTime starttime = new StartTime(this.startTime);
        final EndDate enddate = new EndDate(this.endDate);
        final EndTime endtime = new EndTime(this.endTime);
        // final UniqueCategoryList categories = new
        // UniqueCategoryList(taskCategories);
        return new Task(taskname, startdate, starttime, enddate,
                endtime/* , categories */);
    }
}
