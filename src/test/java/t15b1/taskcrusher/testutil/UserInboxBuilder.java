package t15b1.taskcrusher.testutil;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.UserInbox;
import t15b1.taskcrusher.model.tag.Tag;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;

/**
 * A utility class to help with building userInbox objects.
 * Example usage: <br>
 *     {@code UserInbox inbox = new UserInboxBuilder().withName("CS2103 assignment")
                    .withDescription(Description.NO_DESCRIPTION).withDeadline("18-6-2016")
                    .withPriority("3");}
 */
public class UserInboxBuilder {

    private UserInbox userInbox;

    public UserInboxBuilder(UserInbox userInbox) {
        this.userInbox = userInbox;
    }

    public UserInboxBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        userInbox.addTask(task);
        return this;
    }

    public UserInboxBuilder withTag(String tagName) throws IllegalValueException {
        userInbox.addTag(new Tag(tagName));
        return this;
    }

    public UserInbox build() {
        return userInbox;
    }
}
