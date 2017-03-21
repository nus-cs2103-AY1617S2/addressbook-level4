package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    //public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;
	public TestTask apples, cereals, yam, zoo;
	
    public TypicalTestTasks() {
        try {
        	
        	apples = new TaskBuilder().withTaskName("Deliver apples").withTaskDate("120217").withTaskStartTime("1000")
					.withTaskEndTime("1200").withTaskDescription("Deliver to Crescent Road.").build();
			cereals = new TaskBuilder().withTaskName("Buy cereals").withTaskDate("020217").withTaskStartTime("0800")
					.withTaskEndTime("1000").withTaskDescription("Look for promo cereals.").build();

			// Manually added
			yam = new TaskBuilder().withTaskName("Grow yam").withTaskDate("100217").withTaskStartTime("0700")
					.withTaskEndTime("1700").withTaskDescription("Buy fertilizers.").build();
			zoo = new TaskBuilder().withTaskName("Visit zoo").withTaskDate("030217").withTaskStartTime("0800")
					.withTaskEndTime("1700").withTaskDescription("Bring Jesse along.").build();
        	/*
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").withAddress("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").withAddress("chicago ave").build();
                    */
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTaskPerson(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
    	return new TestTask[]{apples, cereals, yam, zoo};
       // return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
