package seedu.watodo.logic.parser;

import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

//@@author 
/**
 * 
 * 
 *
 */
public class DateTimeParser {

    public enum TaskType { FLOAT, DEADLINE, EVENT };
    private TaskType type;

    private String startDate;
    private String endDate;

    public static final String MESSAGE_INVALID_NUM_DATETIME = "Too many/few dateTime arguments!";

    /** Constructs a DateTimeParser object with both default start and end date-times null */
    public DateTimeParser() {
        startDate = null;
        endDate = null;
    }
    
    /**
     *                 
     * @param args
     * @return
     * @throws IllegalValueException
     */
    public void parse(String args) throws IllegalValueException {
        ArgumentTokenizer datesTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON,
                PREFIX_FROM, PREFIX_TO);
        datesTokenizer.tokenize(args);
        
        type = checkTaskType(datesTokenizer);
        
        if (type.equals(TaskType.DEADLINE) || type.equals(TaskType.EVENT)) {
            extractDates(datesTokenizer);
        }
    }

    /**
     * 
     * @param args
     * @return
     */
    public String trimArgsOfDates(String args) {

        args = args.replaceAll(PREFIX_BY.getPrefix(), "");
        args = args.replaceAll(PREFIX_ON.getPrefix(), ""); 
        args = args.replaceAll(PREFIX_FROM.getPrefix(), ""); 
        args = args.replaceAll(PREFIX_TO.getPrefix(), "");
    }
    
    
    /**
     * Checks the type of task(floating, deadline or event) to be added based on
     * the DATETIME parameters entered by the user.
     * 
     * @throws IllegalValueException if too many or too few dateTime args are
     *             entered
     */
    private TaskType checkTaskType(ArgumentTokenizer datesTokenizer) throws IllegalValueException {
        
        boolean hasBy = datesTokenizer.getValue(PREFIX_BY).isPresent();
        boolean hasOn = datesTokenizer.getValue(PREFIX_ON).isPresent();
        boolean hasFrom = datesTokenizer.getValue(PREFIX_FROM).isPresent();
        boolean hasTo = datesTokenizer.getValue(PREFIX_TO).isPresent();
        
        if (!hasBy && !hasOn && !hasFrom && !hasTo) {
            return TaskType.FLOAT;
        }
        if ((hasBy && !hasOn && !hasFrom && !hasTo) || (!hasBy && hasOn && !hasFrom && !hasTo)) {
            return TaskType.DEADLINE;
        }
        if ((!hasBy && !hasOn && hasFrom && hasTo) || (!hasBy && hasOn && !hasFrom && hasTo)) {
            return TaskType.EVENT;
        }
        throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
    }

    /**
     * 
     * @param datesTokenizer
     * @throws IllegalValueException
     */
    private void extractDates(ArgumentTokenizer datesTokenizer) throws IllegalValueException {
            
        List<String> argsWithDate = new ArrayList<String>();
        Collections.addAll(argsWithDate, datesTokenizer.getValue(PREFIX_BY).orElse(null), 
          datesTokenizer.getValue(PREFIX_ON).orElse(null), 
          datesTokenizer.getValue(PREFIX_FROM).orElse(null),
          datesTokenizer.getValue(PREFIX_TO).orElse(null));

        Parser parser = new Parser();
        List<String> datesInText = new ArrayList<String>();
        
        for (String arg : argsWithDate) {
            if (arg != null) {
                List<DateGroup> dateGroups = parser.parse(arg.trim());
                    throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
                }                
                datesInText.add(dateGroups.get(0).getText().trim());
            }
        }

        if (datesInText.size() == 1) {
            endDate = datesInText.get(0);
        }
        if (datesInText.size() == 2) {
            startDate = datesInText.get(0);
            endDate = datesInText.get(1);
        }
    }


    public TaskType getTaskType() {
        return type;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }


}
