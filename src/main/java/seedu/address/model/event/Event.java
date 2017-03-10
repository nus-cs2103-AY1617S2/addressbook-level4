package seedu.address.model.event;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.Description;
import seedu.address.model.person.Name;

public class Event implements ReadOnlyEvent{
    public Event(ReadOnlyEvent event) {
    }

    public UniqueTagList getTags(){
        return null;
    }
    
    public Name getEventName(){
        return null;
    }
    
    public Description getDescription(){
        return null;
    }

    public EventDate getEventDate(){
        return null;
    }

    public void resetData(ReadOnlyEvent editedTask) {
        // TODO Auto-generated method stub
        
    }
     
}
