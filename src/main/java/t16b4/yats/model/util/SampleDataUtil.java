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
					new Task(new Title("Vascular Medicine Research"), new Deadline("27/06/2039"), new Timing("6:00pm"),
							new Description("Research to be extended and continued to alleviate carcinogenic vascularity"),
							new UniqueTagList("research")),
					new Task(new Title("Vscan Access Mid Term"), new Deadline("15/09/2017"), new Timing("2:30pm"),
							new Description("To revise on p value mathematical induction"),
							new UniqueTagList("midterm", "vscan")),
					new Task(new Title("Grocery Shopping"), new Deadline("16/09/2017"), new Timing("9:45am"),
							new Description("To buy carrots, celery, chicken"),
							new UniqueTagList("grocery")),
					new Task(new Title("Project Milestone Documentation"), new Deadline("16/12/2017"), new Timing("11:59pm"),
							new Description("Remember to finish up the design aspect of voice synthesiser"),
							new UniqueTagList("project")),
					new Task(new Title("Mechanical restructuring of sustainability revision"), new Deadline("30/05/2030"), new Timing("5:00pm"),
							new Description("Mid term test is going to be difficult!"),
							new UniqueTagList("midterm")),
					new Task(new Title("Japanese N1 exam practice"), new Deadline("23/06/2017"), new Timing("11:15am"),
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
