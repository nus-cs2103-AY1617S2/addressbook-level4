package org.teamstbf.yats.model.util;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Recurrence;
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
			    new UniqueTagList("research"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Grocery Shopping"), new Location("FoodCourt"),
			    new Schedule("8:05PM 07/4/2017"), new Schedule("9:05PM 07/4/2017"), new Schedule(""),
			    new Description("Buy some food"), new UniqueTagList("necessitires"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Vscan Access Mid Term"), new Location("School"), new Schedule(""),
			    new Schedule(""), new Schedule(""),
			    new Description("To revise on p value mathematical induction"),
			    new UniqueTagList("school", "learn"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Project Milestone Documentation"), new Location("School"), new Schedule(""),
			    new Schedule(""), new Schedule("11:59PM 08/04/2017"),
			    new Description("prepare project doucments"), new UniqueTagList("school", "learn"),
			    new IsDone(), false, new Recurrence()),
		    new Event(new Title("Benzoic Aptitude Test"), new Location("School"),
			    new Schedule("3:45PM 12/12/2017"), new Schedule("7:00PM 12/12/2017"), new Schedule(""),
			    new Description(
				    "Benzoic acid is a very acidic compound that requires intense alcoholic exchange. "
					    + "Revision has to be based on ethanol and focus on malloc."),
			    new UniqueTagList("Organic", "revision"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Inductive Reasoning Based Phillosophy"), new Location("Home"),
			    new Schedule(""), new Schedule(""), new Schedule("10:20AM 10/1/2019"),
			    new Description(
				    "Have to finish reading this book by 10th January in preparation for Armaggedon."),
			    new UniqueTagList("home", "books", "reading", "armaggedon"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Minecraft Lets Play 100"), new Location("RoosterTeeth"), new Schedule(""),
			    new Schedule(""), new Schedule(""),
			    new Description("Catch up on Minecraft Let's Plays by Rooster Teeth! Comedy as its best!"),
			    new UniqueTagList("RT", "comedy"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Red vs Blue"), new Location("Dutch"), new Schedule("8:05PM 12/3/2020"),
			    new Schedule("9:00PM 30/3/2025"), new Schedule(""),
			    new Description("The great war between the 2 colours have begun. The crusade is upon us!"),
			    new UniqueTagList("holy", "crusade"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Oxygen not Included"), new Location("KleiEntertainment"), new Schedule(""),
			    new Schedule(""), new Schedule(""), new Description("New game released!"),
			    new UniqueTagList("school", "learn"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Suicide watch"), new Location("r/SuicideWatch"), new Schedule(""),
			    new Schedule(""), new Schedule(""),
			    new Description("Why cut yourself when you can have access to this subreddit!"),
			    new UniqueTagList("reddit"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Virtual Youtuber"), new Location("WhiteSpace"), new Schedule(""),
			    new Schedule(""), new Schedule(""), new Description("Straight man, funny man."),
			    new UniqueTagList("Kizuna", "Ai"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Machine Vision Algorithm on Raspberry Pi"), new Location("Home"),
			    new Schedule(""), new Schedule(""), new Schedule("6:00PM 30/8/2017"),
			    new Description("Because placing black masking tape on the ground is too old school, "
				    + "vision processing via camera controls would make a better and more efficient choice."),
			    new UniqueTagList("Machine", "vision", "raspberry", "pi", "tasty"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Learning Python"), new Location("Home"), new Schedule(""), new Schedule(""),
			    new Schedule("3:33AM 21/11/2018"),
			    new Description("Raspberry pi makes entry to learning real time operating system easy."),
			    new UniqueTagList("raspberry", "pi"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Dont starve together"), new Location("KleiEntertainment"),
			    new Schedule("3:00PM 15/6/2018"), new Schedule("5:00PM 17/6/2018"), new Schedule(""),
			    new Description(
				    "Obtaining food for one person is hard already, why do you have to make things harder than usual?"),
			    new UniqueTagList("dont", "starve", "DST"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Uninstall DOTA2"), new Location("Steam"), new Schedule("3:33PM 28/2/2019"),
			    new Schedule("5:55PM 29/2/2019"), new Schedule(""),
			    new Description(
				    "DOTA2 is getting too hard and too toxic, it's time to uninstall, see you at the next game."),
			    new UniqueTagList("DOTA2"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Steel beams cannot melt dank memes"), new Location("4chan"), new Schedule(""),
			    new Schedule(""), new Schedule(""), new Description("4chan amirite"), new UniqueTagList(),
			    new IsDone(), false, new Recurrence()),
		    new Event(new Title("Title not found"), new Location(""), new Schedule(""), new Schedule(""),
			    new Schedule(""), new Description("Locating title within application's database."),
			    new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Replication based on computer dynamic memory"),
			    new Location("RyzenArchitecture"), new Schedule(""), new Schedule(""), new Schedule(""),
			    new Description(""), new UniqueTagList("Ryzen", "Vega"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Seeing is believeing"), new Location("NUS"), new Schedule(""),
			    new Schedule(""), new Schedule(""),
			    new Description("NUS Wifi is kind of a finicky subject, you don't want to touch it, "
				    + "but you have to use it to survive. Do what you will with it, but I'm leaving."),
			    new UniqueTagList("gone", "wild"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Secrets of the hidden temple"), new Location("MadMoon"), new Schedule(""),
			    new Schedule(""), new Schedule("9:52PM 23/9/2019"),
			    new Description(
				    "The mad moon seems to be a legend or a myth, something that should not be uncovered.."),
			    new UniqueTagList("mad", "moon"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("The spirit moves me"), new Location(""), new Schedule("1:00AM 15/5/2017"),
			    new Schedule("5:30AM 15/5/2017"), new Schedule(""), new Description("I am a storm chaser."),
			    new UniqueTagList("storm", "spirit"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("The first of many a blooded enemy I will draw thine blade"),
			    new Location("KentRidge"), new Schedule("2:45PM 17/4/2017"),
			    new Schedule("2:00AM 19/4/2017"), new Schedule(""),
			    new Description("Now you walk between the planes."), new UniqueTagList("spirit", "breaker"),
			    new IsDone(), false, new Recurrence()),
		    new Event(new Title("To move swiftly as the wolves of IceWrack"), new Location("IceWrack"),
			    new Schedule(""), new Schedule(""), new Schedule("11:59PM 16/7/2025"),
			    new Description(
				    "Moving in the cold, dark, icy region is like taking a bullet train to Greenland."),
			    new UniqueTagList("Bullet", "train"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Returning the Arsenal Magus"), new Location(""), new Schedule(""),
			    new Schedule(""), new Schedule(""),
			    new Description("Invoking the many spells in your arsenal is too much to handle."),
			    new UniqueTagList("Wex", "Quas", "Exort"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("To move out of the detonation region"), new Location("SnipersShrapnel"),
			    new Schedule(""), new Schedule(""), new Schedule("6:00PM 30/3/2017"),
			    new Description("The slow is too strong! We must hurry."), new UniqueTagList("HO", "HA"),
			    new IsDone(), false, new Recurrence()),
		    new Event(new Title("Setting up proximity sensors"), new Location("Radiant"),
			    new Schedule("12:00AM 31/4/2017"), new Schedule("11:59PM 3/4/2017"), new Schedule(""),
			    new Description("The mine is set."), new UniqueTagList("planted", "set"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Remove mine documentation"), new Location("NUS"),
			    new Schedule("8:05AM 2/2/2017"), new Schedule("9:05PM 10/2/2017"), new Schedule(""),
			    new Description("Did we put the right fuse on that one?"),
			    new UniqueTagList("techies", "WOAW"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Attend the Sirens funeral"), new Location("SlithreenGuardsPalace"),
			    new Schedule("5:15AM 15/3/2017"), new Schedule("12:00PM 15/3/2017"), new Schedule(""),
			    new Description("No Slithreen may fail, but are put to eternal rest."),
			    new UniqueTagList("song", "rhasta"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Venturing into the dark abyss"), new Location("DarkAbyss"), new Schedule(""),
			    new Schedule(""), new Schedule("7:00PM 27/3/2017"),
			    new Description("I have to return before he finds me.. Only darkness await.."),
			    new UniqueTagList("under", "lord"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Document on the reversing polarity of Magnus"), new Location("Magnus"),
			    new Schedule(""), new Schedule(""), new Schedule("4:00PM 5/5/2017"),
			    new Description("The master-smiths of Mt. Joerlak agree on only a single point: "
				    + "that the horn of a magnoceros is more precious than any alloy. And of all such horns, "
				    + "the largest and sharpest belongs to the beast they call Magnus. For half a generation, Magnus took "
				    + "easy sport goring hunters come to claim the treasures of his kin."),
			    new UniqueTagList("bradwarden", "enchantress"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Submit a report on the Nothl Realm"), new Location("Huskar"), new Schedule(""),
			    new Schedule(""), new Schedule("12:00PM 11/4/2017"),
			    new Description("The harrowing experience increases the wrath of the dazzling one."),
			    new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("The milk shall not be forgotten"), new Location("Internet"),
			    new Schedule("12:00PM 31/3/2017"), new Schedule("12:00PM 19/4/2017"), new Schedule(""),
			    new Description("Never forget."), new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Wings of Piano"), new Location("Deemo"), new Schedule(""), new Schedule(""),
			    new Schedule("6:00PM 1/4/2017"), new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Evolution Era"), new Location("Deemo"), new Schedule(""), new Schedule(""),
			    new Schedule("6:00PM 2/4/2017"), new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Reflection Mirror Night"), new Location("Deemo"), new Schedule(""),
			    new Schedule(""), new Schedule("6:00PM 3/4/2017"),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Saika"), new Location("Deemo"), new Schedule(""), new Schedule(""),
			    new Schedule("9:00AM 4/4/2017"), new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Magnolia"), new Location("Deemo"), new Schedule(""), new Schedule(""),
			    new Schedule("10:15AM 5/4/2017"), new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Yubikiri Genman"), new Location("Deemo"), new Schedule("11:00AM 6/4/2017"),
			    new Schedule("12:00PM 7/4/2017"), new Schedule(""),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Nine Point Eight"), new Location("Deemo"), new Schedule("1:00PM 8/4/2017"),
			    new Schedule("3:00PM 8/4/2017"), new Schedule(""),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Sallen pointer"), new Location("NUS"), new Schedule("2:11PM 22/4/2017"),
			    new Schedule("2:45PM 22/4/2017"), new Schedule(""),
			    new Description("Sallen key low pass filtering seems to have failed."),
			    new UniqueTagList("never", "give", "you", "up"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Friction"), new Location("Deemo"), new Schedule("3:00PM 16/4/2017"),
			    new Schedule("4:00PM 16/4/2017"), new Schedule(""),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Final Destination"), new Location("Death"), new Schedule(""), new Schedule(""),
			    new Schedule(""), new Description("In death will you do us part."),
			    new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Resurrection"), new Location("Life"), new Schedule(""), new Schedule(""),
			    new Schedule(""), new Description("Til life squeeze you dry."),
			    new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Reverse Parallel Universe"), new Location("Deemo"), new Schedule(""),
			    new Schedule(""), new Schedule("4:30PM 15/5/2017"),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Anima"), new Location("Deemo"), new Schedule("3:54PM 13/2/2017"),
			    new Schedule("4:00PM 13/2/2017"), new Schedule(""),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Test configuration file of reverberating system"), new Location("NUS"),
			    new Schedule(""), new Schedule(""), new Schedule("11:59PM 15/3/2017"),
			    new Description("System harmonics project failing test cases."),
			    new UniqueTagList("recovering", "system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Dream"), new Location("Deemo"), new Schedule(""), new Schedule(""),
			    new Schedule("4:15PM 16/3/2017"), new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Entrance"), new Location("Deemo"), new Schedule("8:23PM 15/5/2017"),
			    new Schedule("9:00PM 16/5/2017"), new Schedule(""),
			    new Description("Never left without saying Goodbye"),
			    new UniqueTagList("deemo", "little", "girl"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Test event 1"), new Location("TestDrive"), new Schedule("3:23PM 23/3/2017"),
			    new Schedule("4:23PM 23/3/2017"), new Schedule(""),
			    new Description("Test drive initiating.."), new UniqueTagList("system"), new IsDone(), false, new Recurrence()),
		    new Event(new Title("Test event 2"), new Location("TestDrive"), new Schedule(""), new Schedule(""),
			    new Schedule(""), new Description("Locating title within application's database."),
			    new UniqueTagList("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7"), new IsDone(), false, new Recurrence()) };
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
