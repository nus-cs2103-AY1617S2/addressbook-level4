package seedu.taskcrusher.model.event;

import seedu.taskcrusher.model.shared.Description;
import seedu.taskcrusher.model.shared.Name;
import seedu.taskcrusher.model.tag.UniqueTagList;

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
