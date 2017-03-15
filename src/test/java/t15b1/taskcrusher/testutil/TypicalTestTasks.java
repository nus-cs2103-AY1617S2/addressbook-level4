package t15b1.taskcrusher.testutil;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.UserInbox;
import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.task.Deadline;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestCard assignment, payment, shopping, phoneCall, application;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withName("CS2103 assignment")
                    .withDescription(Description.NO_DESCRIPTION).withDeadline("18-6-2020")
                    .withPriority("3")
                    .withTags("school").build();
            payment = new TaskBuilder().withName("pay tuition fee").withDescription("Set up bank account")
                    .withDeadline("tomorrow").withPriority("3")
                    .withTags("owesMoney", "school").build();
            shopping = new TaskBuilder().withName("Buy cheesecake").withPriority("1")
                    .withDeadline("tomorrow").withDescription("wall street").build();
            phoneCall = new TaskBuilder().withName("call Mr Beans").withPriority("1")
                    .withDeadline(Deadline.NO_DEADLINE).withDescription("sometime this year").build();
            application = new TaskBuilder().withName("apply to jobs").withPriority("0")
                    .withDeadline("next Monday").withDescription("McDonald's").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadUserInboxWithSampleData(UserInbox inbox) {
        for (TestCard task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                inbox.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestCard[] getTypicalTasks() {
        return new TestCard[]{assignment, payment, shopping, phoneCall, application};
    }

    public UserInbox getTypicalUserInbox() {
        UserInbox inbox = new UserInbox();
        loadUserInboxWithSampleData(inbox);
        return inbox;
    }
}
