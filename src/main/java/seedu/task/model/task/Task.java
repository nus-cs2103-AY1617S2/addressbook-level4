package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

   // private Name name;
   // private Phone phone;
    //private Email email;
   // private Address address;
    private TaskName taskName;
    private TaskDate taskDate;
    private TaskTime taskStartTime;
    private TaskTime taskEndTime;
    private String taskDescription;
    
    public static final String MESSAGE_INVALID_TIME = "Start time can't be after end time";

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime, String taskDescription, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(taskName, taskDate, taskStartTime, taskEndTime, tags);
        this.taskName = taskName;
    	this.taskDate = taskDate;
    	this.taskStartTime = taskStartTime;
    	this.taskEndTime = taskEndTime;
    	this.taskDescription = taskDescription;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime, String taskDescription) {
        	this(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, new UniqueTagList());
    }
    /*
    public Task(TaskName taskName, TaskDate taskDate, TaskTime taskStartTime, TaskTime taskEndTime, String taskDescription) {
    	this.taskName = taskName;
    	this.taskDate = taskDate;
    	this.taskStartTime = taskStartTime;
    	this.taskEndTime = taskEndTime;
    	this.taskDescription = taskDescription;
    	
    	try {
	    	this.name = new Name("PLACEHOLDER NAME, SHOULD NOT SEE");
	    	this.phone = new Phone("123");
	    	this.email = new Email("asdfads@gmail.com");
	    	this.address = new Address("22 Acacia Avenue");
	    	tags = new UniqueTagList();
    	} catch (IllegalValueException e) {
	    	System.out.println("error11");
	    }
    	
    }
*/
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getTaskDate(), source.getTaskStartTime(), source.getTaskEndTime(), source.getTaskDescription(),source.getTags());
    }

    /*
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPhone(Phone phone) {
        assert phone != null;
        this.phone = phone;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    public void setEmail(Email email) {
        assert email != null;
        this.email = email;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setAddress(Address address) {
        assert address != null;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }
*/
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        
        try {
	        this.setTaskName(replacement.getTaskName());
	        this.setTaskDate(replacement.getTaskDate());
	        this.setTaskStartTime(replacement.getTaskStartTime());
	        this.setTaskEndTime(replacement.getTaskEndTime());
	        this.setTaskDescription(replacement.getTaskDescription());
	        this.setTags(replacement.getTags());
        } catch (IllegalValueException ive) {
        	System.out.println("error resetting data in read only task"); //phrase better for message
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    public TaskName getTaskName() {
    	return taskName;
    }
    public TaskDate getTaskDate() {
    	return taskDate;
    }
    public TaskTime getTaskStartTime() {
    	return taskStartTime;
    }
    public TaskTime getTaskEndTime() {
    	return taskEndTime;
    }
    public String getTaskDescription() {
    	return taskDescription;
    }
    public void setTaskDescription(String description) {
    	this.taskDescription = description;
    }
    public void setTaskName(TaskName taskName) {
    	this.taskName = taskName;
    }
    public void setTaskDate(TaskDate taskDate) {
    	this.taskDate = taskDate;
    }
    public void setTaskStartTime(TaskTime taskStartTime) throws IllegalValueException {
    	if (this.taskEndTime == null || this.taskEndTime.compareTo(taskStartTime) >= 0) {
    		this.taskStartTime = taskStartTime;
    	} else {
    		throw new IllegalValueException(MESSAGE_INVALID_TIME);
    	}
    }
    public void setTaskEndTime(TaskTime taskEndTime) throws IllegalValueException {
    	if (this.taskStartTime == null || this.taskStartTime.compareTo(taskEndTime) <= 0) {
    		this.taskEndTime = taskEndTime;
    	} else {
    		throw new IllegalValueException(MESSAGE_INVALID_TIME);
    	}
    }
    

}
