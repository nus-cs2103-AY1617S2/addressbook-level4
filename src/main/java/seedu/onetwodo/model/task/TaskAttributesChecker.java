package seedu.onetwodo.model.task;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.AddCommand;

/**
 * A utility class that checks if the given Task has valid attributes.
 */
public class TaskAttributesChecker {
    
    public static final String MESSAGE_MISSING_DATE = "A task with a start date needs an end date or duration.";
    public static final String MESSAGE_INVALID_START = "Start time needs to be later than current time.";
    public static final String MESSAGE_INVALID_END = "End time needs to be later than current time.";
    public static final String MESSAGE_INVALID_EVENT= "End time needs to be later than start time.";
    
    
    public static void checkValidAttributes(Task taskUnderTest, LocalDateTime dateCreated) throws IllegalValueException{
        checkIsValidTodo(taskUnderTest);
        checkIsValidStartDate(taskUnderTest,dateCreated);
        checkIsValidEndDate(taskUnderTest,dateCreated);
        checkIsValidEvent(taskUnderTest);
    }
    
    
    private static void checkIsValidTodo(Task taskUnderTest) throws IllegalValueException{
        if (taskUnderTest.getStartDate().hasDate() && !taskUnderTest.getEndDate().hasDate()){
            //has start date but no end date
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                    MESSAGE_MISSING_DATE) + AddCommand.MESSAGE_USAGE);
        }
    }
    
    private static void checkIsValidStartDate(Task taskUnderTest, LocalDateTime dateCreated) throws IllegalValueException{
        if (taskUnderTest.getStartDate().hasDate()){
            if(taskUnderTest.getStartDate().getLocalDateTime().compareTo(dateCreated)<0){
                //startDate of Task is before the date the task is created
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        MESSAGE_INVALID_START) + AddCommand.MESSAGE_USAGE);
            }
        }
    }
    
    private static void checkIsValidEndDate(Task taskUnderTest, LocalDateTime dateCreated) throws IllegalValueException{
        if (taskUnderTest.getEndDate().hasDate()){
            if(taskUnderTest.getEndDate().getLocalDateTime().compareTo(dateCreated)<0){
                //endDate of Task is before the date the task is created
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        MESSAGE_INVALID_END) + AddCommand.MESSAGE_USAGE);
            }
        }
    }
    
    private static void checkIsValidEvent(Task taskUnderTest) throws IllegalValueException{
        if (taskUnderTest.getStartDate().hasDate() && taskUnderTest.getEndDate().hasDate()){
            if(taskUnderTest.getEndDate().getLocalDateTime().compareTo(taskUnderTest.getStartDate().getLocalDateTime())<0){
                //endDate of Task is before startDate
                throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                        MESSAGE_INVALID_EVENT) + AddCommand.MESSAGE_USAGE);
            }
        }
    }
}
