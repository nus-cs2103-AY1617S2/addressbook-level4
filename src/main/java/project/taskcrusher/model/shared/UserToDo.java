package project.taskcrusher.model.shared;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Priority;

/** Acts as a parent class of Event and Task. Represents some "thing" that user is going to do
 *  at "some point in time" i.e. no notion of time introduced yet.
 */
public class UserToDo implements ReadOnlyUserToDo {
    protected Name name;
    protected Description description;
    protected Priority priority;
    protected UniqueTagList tags;
    protected boolean isComplete;

    public UserToDo (Name name, Priority priority, Description description,  UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, description, priority, tags);
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.tags = new UniqueTagList(tags);
        this.isComplete = false;
    }

    public Description getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public UniqueTagList getTags() {
        return tags;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public void markComplete() {
        this.isComplete = true;
    }

    public void markIncomplete() {
        this.isComplete = false;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public Name getName() {
        return name;
    }

}
