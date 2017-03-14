package seedu.onetwodo.testutil;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.TaskType;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private EndDate endDate;
    private StartDate startDate;
    private Description description;
    private boolean isDone;
    private TaskType type;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
        this.type = taskToCopy.getTaskType();
        this.isDone = taskToCopy.getDoneStatus();
    }
    
    // Getters
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public boolean getDoneStatus() {
        return isDone;
    }
    
    @Override
    public TaskType getTaskType() {
        return type;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    // Setters
    public void setName(Name name) {
        this.name = name;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
    
    public void setDoneStatus(boolean isDone) {
        this.isDone = isDone;
    }
    
    public void setTaskType(TaskType type) {
        this.type = type;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        
        if(this.hasStartDate()) {
            sb.append("s/" + this.getStartDate().value + " ");
        }
        
        if(this.hasEndDate()) {
            sb.append("e/" + this.getEndDate().value + " ");
        }
        
        if(this.hasDescription()) { 
            sb.append("d/" + this.getDescription().value + " ");
        }
        
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getEditCommand(String prefixIndex) {
        String addCommand = getAddCommand();
        String editCommand = addCommand.replace(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD
                + " " + prefixIndex + " ");
        return editCommand;
    }

}
