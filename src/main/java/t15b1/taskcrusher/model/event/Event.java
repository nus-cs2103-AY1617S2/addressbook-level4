package t15b1.taskcrusher.model.event;

import t15b1.taskcrusher.model.shared.Description;
import t15b1.taskcrusher.model.shared.Name;
import t15b1.taskcrusher.model.tag.UniqueTagList;

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
