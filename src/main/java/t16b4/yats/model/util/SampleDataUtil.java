package t16b4.yats.model.util;

import t16b4.yats.commons.exceptions.IllegalValueException;
import t16b4.yats.model.TaskManager;
import t16b4.yats.model.item.Description;
import t16b4.yats.model.item.Timing;
import t16b4.yats.model.item.Title;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.Deadline;
import t16b4.yats.model.item.UniqueItemList.DuplicatePersonException;
import t16b4.yats.model.ReadOnlyTaskManager;
import t16b4.yats.model.tag.UniqueTagList;

public class SampleDataUtil {
	public static Task[] getSampleTasks() {
		try {
			return new Task[] {
					new Task(new Title("Vascular Medicine Research"), new Deadline("87438807"), new Timing("alexyeoh@gmail.com"),
							new Description("Research to be extended and continued to alleviate carcinogenic vascularity"),
							new UniqueTagList("research")),
					new Task(new Title("Vscan Access Mid Term"), new Deadline("99272758"), new Timing("berniceyu@gmail.com"),
							new Description("To revise on p value mathematical induction"),
							new UniqueTagList("midterm", "vscan")),
					new Task(new Title("Grocery Shopping"), new Deadline("93210283"), new Timing("charlotte@yahoo.com"),
							new Description("To buy carrots, celery, chicken"),
							new UniqueTagList("grocery")),
					new Task(new Title("Project Milestone Documentation"), new Deadline("91031282"), new Timing("lidavid@google.com"),
							new Description("Remember to finish up the design aspect of voice synthesiser"),
							new UniqueTagList("project")),
					new Task(new Title("Mechanical restructuring of sustainability revision"), new Deadline("92492021"), new Timing("irfan@outlook.com"),
							new Description("Mid term test is going to be difficult!"),
							new UniqueTagList("midterm")),
					new Task(new Title("Japanese particle practice"), new Deadline("92624417"), new Timing("royb@gmail.com"),
							new Description("To brush up on the usage of particles in certain situations"),
							new UniqueTagList("japanese"))
			};
		} catch (IllegalValueException e) {
			throw new AssertionError("sample data cannot be invalid", e);
		}
	}

	public static ReadOnlyTaskManager getSampleTaskManager() {
		try {
			TaskManager sampleTM = new TaskManager();
			for (Task sampleTask : getSampleTasks()) {
				sampleTM.addTask(sampleTask);
			}
			return sampleTM;
		} catch (DuplicatePersonException e) {
			throw new AssertionError("sample data cannot contain duplicate persons", e);
		}
	}
}
