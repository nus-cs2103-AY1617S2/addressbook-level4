package project.taskcrusher.model.shared;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.commons.util.CollectionUtil;
import project.taskcrusher.model.tag.UniqueTagList;

//@@author A0127737X
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
        //assert !CollectionUtil.isAnyNull(name, description, priority, tags);
        assert !CollectionUtil.isAnyNull(name, description, tags);
        if (priority == null) {
            try {
                this.priority = new Priority(Priority.NO_PRIORITY); //CHANGED THIS FOR CONSISTENCY
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block. Used for events in the future that supports priority
                e.printStackTrace();
            }
        } else {
            this.priority = priority;
        }

        this.name = name;
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

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public UniqueTagList getTags() {
        return tags;
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


}
