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
					new Task(new Title("Vascular Medicine Research"), new Deadline("June 27th, 2039"), new Timing("1800"),
							new Description("Research to be extended and continued to alleviate carcinogenic vascularity"),
							new UniqueTagList("research")),
					new Task(new Title("Vscan Access Mid Term"), new Deadline("September 15th, 2017"), new Timing("1430"),
							new Description("To revise on p value mathematical induction"),
							new UniqueTagList("midterm", "vscan")),
					new Task(new Title("Grocery Shopping"), new Deadline("September 16th, 2017"), new Timing("0945"),
							new Description("To buy carrots, celery, chicken"),
							new UniqueTagList("grocery")),
					new Task(new Title("Project Milestone Documentation"), new Deadline("December 16th, 2017"), new Timing("2359"),
							new Description("Remember to finish up the design aspect of voice synthesiser"),
							new UniqueTagList("project")),
					new Task(new Title("Mechanical restructuring of sustainability revision"), new Deadline("May 30th, 2030"), new Timing("1700"),
							new Description("Mid term test is going to be difficult!"),
							new UniqueTagList("midterm")),
					new Task(new Title("Japanese N1 exam practice"), new Deadline("June 23, 2017"), new Timing("1115"),
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
