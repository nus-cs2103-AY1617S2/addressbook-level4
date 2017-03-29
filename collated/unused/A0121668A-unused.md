# A0121668A-unused
###### \TaskStatus.java
``` java
//Unused because it was decided not to wrap taskStatus inside a class, 
//but implement with a simple boolean attribute instead
/**
 * Represents an Activity's TaskStatus in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskStatus {
    
    public static final String COMPLETED_TASK_STATUS = "[Done]";
    public static final String UNCOMPLETED_TASK_STATUS = "[Pending]";
    public static final boolean COMPLETED = true;
    public static final boolean UNCOMPLETED = false;
    
    public final String status;


    public TaskStatus(boolean status) throws IllegalValueException {
        if(status) {
            this.status = COMPLETED_TASK_STATUS;
        }
        else {
            this.status = UNCOMPLETED_TASK_STATUS;
        }
    }

    @Override
    public String toString() {
        return status;
    }
    
```
