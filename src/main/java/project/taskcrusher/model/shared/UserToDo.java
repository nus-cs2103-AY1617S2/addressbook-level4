package project.taskcrusher.model.shared;

import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
/** Acts as a parent class of Event and Task. Represents a ToDo that user is going to complete
 *  at "some point in time". There is no notion of time introduced at this level.
 */
public class UserToDo implements ReadOnlyUserToDo {
    protected Name name;
    protected Description description;
    protected Priority priority;
    protected UniqueTagList tags;
    protected boolean isComplete;

    public UserToDo (Name name, Priority priority, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, description, tags);

        this.name = name;
        this.priority = priority;
        this.description = description;
        this.tags = new UniqueTagList(tags);
        this.isComplete = false;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean isComplete() {
        return this.isComplete;
    }

    public void markComplete() {
        this.isComplete = true;
    }

    public void markIncomplete() {
        this.isComplete = false;
    }
}
