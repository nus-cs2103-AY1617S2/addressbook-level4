package org.teamstbf.yats.model.util;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueEventList.DuplicateEventException;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class SampleDataUtil {

	public static Event[] getSampleEvents() {
		try {
			return new Event[] {
					new Event(new Title("Vascular Medicine Research"), new Location("Hospital"),
							new Schedule("4:05PM 07/4/2017"), new Schedule("5:05PM 07/4/2017"), new Schedule(""),
							new Description(
									"Research to be extended and continued to " + "alleviate carcinogenic vascularity"),
							new UniqueTagList("research"), new IsDone()),
					new Event(new Title("Grocery Shopping"), new Location("FoodCourt"),
							new Schedule("8:05PM 07/4/2017"), new Schedule("9:05PM 07/4/2017"), new Schedule(""),
							new Description("Buy some food"), new UniqueTagList("necessitires"), new IsDone()),
					new Event(new Title("Vscan Access Mid Term"), new Location("School"), new Schedule(""),
							new Schedule(""), new Schedule(""),
							new Description("To revise on p value mathematical induction"),
							new UniqueTagList("school", "learn"), new IsDone()),
					new Event(new Title("Project Milestone Documentation"), new Location("School"), new Schedule(""),
							new Schedule(""), new Schedule("11:59PM 08/04/2017"),
							new Description("prepare project doucments"), new UniqueTagList("school", "learn"),
							new IsDone()),
					new Event(new Title("Benzoic Aptitude Test"), new Location("School"),
							new Schedule("3:45PM 12/12/2017"), new Schedule("7:00PM 12/12/2017"), new Schedule(""),
							new Description(
									"Benzoic acid is a very acidic compound that requires intense alcoholic exchange. "
											+ "Revision has to be based on ethanol and focus on malloc."),
							new UniqueTagList("Organic", "revision"), new IsDone()),
					new Event(new Title("Inductive Reasoning Based Phillosophy"), new Location("Home"),
							new Schedule(""), new Schedule(""), new Schedule("10:20AM 10/1/2019"),
							new Description(
									"Have to finish reading this book by 10th January in preparation for Armaggedon."),
							new UniqueTagList("home", "books", "reading", "armaggedon"), new IsDone()),
					new Event(new Title("Minecraft Lets Play 100"), new Location("RoosterTeeth"), new Schedule(""),
							new Schedule(""), new Schedule(""),
							new Description("Catch up on Minecraft Let's Plays by Rooster Teeth! Comedy as its best!"),
							new UniqueTagList("RT", "comedy"), new IsDone()),
					new Event(new Title("Red vs Blue"), new Location("Dutch"), new Schedule("8:05PM 12/3/2020"),
							new Schedule("9:00PM 30/3/2025"), new Schedule(""),
							new Description("The great war between the 2 colours have begun. The crusade is upon us!"),
							new UniqueTagList("holy", "crusade"), new IsDone()),
					new Event(new Title("Oxygen not Included"), new Location("KleiEntertainment"), new Schedule(""),
							new Schedule(""), new Schedule(""), new Description("New game released!"),
							new UniqueTagList("school", "learn"), new IsDone()),
					new Event(new Title("Suicide watch"), new Location("r/SuicideWatch"), new Schedule(""),
							new Schedule(""), new Schedule(""),
							new Description("Why cut yourself when you can have access to this subreddit!"),
							new UniqueTagList("reddit"), new IsDone()),
					new Event(new Title("Virtual Youtuber"), new Location("WhiteSpace"), new Schedule(""),
							new Schedule(""), new Schedule(""), new Description("Straight man, funny man."),
							new UniqueTagList("Kizuna", "Ai"), new IsDone()),
					new Event(new Title("Machine Vision Algorithm on Raspberry Pi"), new Location("Home"),
							new Schedule(""), new Schedule(""), new Schedule("6:00PM 30/8/2017"),
							new Description("Because placing black masking tape on the ground is too old school, "
									+ "vision processing via camera controls would make a better and more efficient choice."),
							new UniqueTagList("Machine", "vision", "raspberry", "pi", "tasty"), new IsDone()),
					new Event(new Title("Learning Python"), new Location("Home"), new Schedule(""), new Schedule(""),
							new Schedule("3:33AM 21/11/2018"),
							new Description("Raspberry pi makes entry to learning real time operating system easy."),
							new UniqueTagList("raspberry", "pi"), new IsDone()),
					new Event(new Title("Dont starve together"), new Location("KleiEntertainment"),
							new Schedule("3:00PM 15/6/2018"), new Schedule("5:00PM 17/6/2018"), new Schedule(""),
							new Description(
									"Obtaining food for one person is hard already, why do you have to make things harder than usual?"),
							new UniqueTagList("dont", "starve", "DST"), new IsDone()),
					new Event(new Title("Uninstall DOTA2"), new Location("Steam"), new Schedule("3:33PM 28/2/2019"),
							new Schedule("5:55PM 29/2/2019"), new Schedule(""),
							new Description(
									"DOTA2 is getting too hard and too toxic, it's time to uninstall, see you at the next game."),
							new UniqueTagList("DOTA2"), new IsDone()),
					new Event(new Title("Steel beams cannot melt dank memes"), new Location("4chan"), new Schedule(""),
							new Schedule(""), new Schedule(""), new Description("4chan amirite"), new UniqueTagList(),
							new IsDone()),
					new Event(new Title("Title not found"), new Location(""), new Schedule(""), new Schedule(""),
							new Schedule(""), new Description("Locating title within application's database."),
							new UniqueTagList("recovering", "system"), new IsDone()),
					new Event(new Title("Replication based on computer dynamic memory"),
							new Location("RyzenArchitecture"), new Schedule(""), new Schedule(""), new Schedule(""),
							new Description(""), new UniqueTagList("Ryzen", "Vega"), new IsDone()) };
		} catch (IllegalValueException e) {
			throw new AssertionError("sample data cannot be invalid", e);
		}
	}

	public static ReadOnlyTaskManager getSampleTaskManager() {
		try {
			TaskManager sampleTM = new TaskManager();
			for (Event sampleTask : getSampleEvents()) {
				sampleTM.addEvent(sampleTask);
			}
			return sampleTM;
		} catch (DuplicateEventException e) {
			throw new AssertionError("sample data cannot contain duplicate persons", e);
		}
	}
	/*
	 * new Event(new Title("Restructuring of sustainability revision"), new
	 * Deadline("30/05/2030"), new Timing("5:00pm"), new
	 * Description("Mid term test is going to be difficult!"), new
	 * UniqueTagList("midterm")), new Event(new
	 * Title("Japanese N1 exam practice"), new Deadline("23/06/2017"), new
	 * Timing("11:15am"), new
	 * Description("To brush up on the usage of particles in certain situations"
	 * ), new UniqueTagList("japanese"))
	 */
}
