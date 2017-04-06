package seedu.jobs.model.calendar;

import seedu.jobs.model.task.Name;

public class IDCalendar {
	private Name toConvert;
	
	public IDCalendar (Name name) {
		toConvert = name;
	}
	
	public String manageID (String id) {
		String alteredID = id.replaceAll("[^a-z0-9]","");
		alteredID.toLowerCase();
		
		//to satisfy length requirement
		if (alteredID.length()<5) {
			for (int i = alteredID.length(); i < 5; i++) {
				alteredID = alteredID + "x";
			}
		}
		return alteredID;
	}
	
	public String toString() {
		return manageID(toConvert.toString());
	}
	
}
