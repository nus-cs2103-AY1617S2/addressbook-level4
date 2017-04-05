package seedu.watodo.logic.parser;

import static seedu.watodo.logic.parser.AddCommandParser.EXTRACT_ARGS_REGEX;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TO;

import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.TaskType;

//@@author A0143076J
/**
 * Identifies the task type based on any valid combination of dateTime prefixes,
 * and parses out the startDate and endDate, if any
 */
public class DateTimeParser {

    private Optional<String> startDate;
    private Optional<String> endDate;
    private String unparsedArgs;
    private TaskType type;

    public static final String MESSAGE_INVALID_DATETIME_PREFIX_COMBI = "Too many/few dateTime prefixes!";

    /** Constructs a DateTimeParser object */
    public DateTimeParser() {}

    /**
     * Extracts out the startDate and endDate (if they exist) from the args string
     */
    public void parse(String args) throws IllegalValueException {
        ArgumentTokenizer dateTimeTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON, PREFIX_FROM, PREFIX_TO);

        extractDateTimePrefixes(dateTimeTokenizer, args);
        extractDates(dateTimeTokenizer);
        extractUnparsedArgs(args);
    }

    /**
     * Tokenizes the args for dateTime prefixes and checks that the combination of prefixes is valid
     */
    private void extractDateTimePrefixes(ArgumentTokenizer dateTimeTokenizer, String args) throws IllegalValueException {
        dateTimeTokenizer.tokenize(args);
        if (!isValidPrefixCombi(dateTimeTokenizer)) {
            throw new IllegalValueException(MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        }
    }

    /**
     * Sets the startDate and endDate as {@code}Optional.empty by default and
     * updates them accordingly to the TaskType
     *
     * @throws IllegalValueException if the dates format are invalid
     */
    private void extractDates(ArgumentTokenizer dateTimeTokenizer) throws IllegalValueException {
        
        this.startDate = Optional.empty(); //resets the startDate and endDate
        this.endDate = Optional.empty();  //so DateTimeParser object can be reused for testing

        if (this.type.equals(TaskType.DEADLINE)) {
            extractEndDate(dateTimeTokenizer);
        }
        if (this.type.equals(TaskType.EVENT)) {
            extractStartAndEndDates(dateTimeTokenizer);
        }
    }
   
    /** Gets a valid endDate from a string of text for a deadline task */
    private void extractEndDate(ArgumentTokenizer dateTimeTokenizer) throws IllegalValueException {
        String endDatePostfix = getNonEmptyString(dateTimeTokenizer.getUniqueValue(PREFIX_BY),
                                                  dateTimeTokenizer.getUniqueValue(PREFIX_ON));
        this.endDate = Optional.of(extractDate(endDatePostfix));
    }

    /** Gets a valid startDate and endDate from a string of text for an event task */
    private void extractStartAndEndDates(ArgumentTokenizer dateTimeTokenizer) throws IllegalValueException {
        String startDatePostfix = getNonEmptyString(dateTimeTokenizer.getUniqueValue(PREFIX_FROM),
                                                    dateTimeTokenizer.getUniqueValue(PREFIX_ON));
        String endDatePostfix = dateTimeTokenizer.getUniqueValue(PREFIX_TO).get();

        this.startDate = Optional.of(extractDate(startDatePostfix));
        this.endDate = Optional.of(extractDate(endDatePostfix));
    }
    
    /**
     * Returns the non empty String between two mutually exclusive arguments. Both strings should not coexist.
     * Precondition: Only one of the {@code}Optional.empty strings is present
     */
    private String getNonEmptyString(Optional<String> either, Optional<String> or) {
        assert either.isPresent() || or.isPresent();
        assert !(either.isPresent() && or.isPresent());
        
        if (either.isPresent()) {
            return either.get();
        }
        if(or.isPresent()) {
            return or.get();
        }
        return null;
    }
        
    /**
     * Returns a substring of the postfix argument that is a valid dateTime in text
     */
    private String extractDate(String postfix) throws IllegalValueException {
        assert postfix != null;

        Parser parser = new Parser(); // refers to the Parser class in natty
        List<DateGroup> dateGroups = parser.parse(postfix.trim());
        if (isInvalidDateArg(dateGroups)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        return dateGroups.get(0).getText().trim();
    }

    /** Returns true if a valid dateTime substring does not exist or 
     * its position does not follow right after the dateTime prefix 
     */
    private boolean isInvalidDateArg(List<DateGroup> dateGroups) {
        final int POS_RIGHT_AFTER_PREFIX = 1;
        return dateGroups.size() == 0 || dateGroups.get(0).getPosition() != POS_RIGHT_AFTER_PREFIX;
    }

    /**
     * Sets the unparsedArgs with the dateTime prefixes and dates removed
     */
    private void extractUnparsedArgs(String args) {
        if (startDate.isPresent()) {
            args = args.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_FROM.getPrefix(), startDate.get()), " ");
            args = args.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), startDate.get()), " ");
        }
        if (endDate.isPresent()) {
            args = args.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_BY.getPrefix(), endDate.get()), " ");
            args = args.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_ON.getPrefix(), endDate.get()), " ");
            args = args.replaceAll(String.format(EXTRACT_ARGS_REGEX, PREFIX_TO.getPrefix(), endDate.get()), " ");
        }
        
        this.unparsedArgs = args.trim();
    }

    /**
     * Returns true if the combination of dateTime prefixes entered by the user
     * matches any one of the valid TaskTypes(floating, deadline or event)
     *
     * @throws IllegalValueException if the combination of dateTime prefixes entered is not recognized
     */
    private boolean isValidPrefixCombi(ArgumentTokenizer datesTokenizer) throws IllegalValueException {

        boolean hasBy = datesTokenizer.getUniqueValue(PREFIX_BY).isPresent();
        boolean hasOn = datesTokenizer.getUniqueValue(PREFIX_ON).isPresent();
        boolean hasFrom = datesTokenizer.getUniqueValue(PREFIX_FROM).isPresent();
        boolean hasTo = datesTokenizer.getUniqueValue(PREFIX_TO).isPresent();

        if (isFloatingPrefixCombi(hasBy, hasOn, hasFrom, hasTo)) {
            this.type = TaskType.FLOAT;
            return true;
        }
        if (isDeadlinePrefixCombi(hasBy, hasOn, hasFrom, hasTo)) {
            this.type = TaskType.DEADLINE;
            return true;
        }
        if (isEventPrefixCombi(hasBy, hasOn, hasFrom, hasTo)) {
            this.type = TaskType.EVENT;
            return true;
        }
        return false;
    }

    /**
     * Returns true if there is no dateTime prefixes present
     */
    private boolean isFloatingPrefixCombi(boolean hasBy, boolean hasOn, boolean hasFrom, boolean hasTo) {
        return !hasBy && !hasOn && !hasFrom && !hasTo;
    }

    /**
     * Returns true if and only if either of 'by' or 'on' prefix is present only 
     */
    private boolean isDeadlinePrefixCombi(boolean hasBy, boolean hasOn, boolean hasFrom, boolean hasTo) {
        return (hasBy && !hasOn && !hasFrom && !hasTo) || (!hasBy && hasOn && !hasFrom && !hasTo);
    }

    /**
     * Returns true if and only if either one of 'from' or 'on' prefixes is present
     * and the 'to' prefix is present
     */
    private boolean isEventPrefixCombi(boolean hasBy, boolean hasOn, boolean hasFrom, boolean hasTo) {
        return (!hasBy && !hasOn && hasFrom && hasTo) || (!hasBy && hasOn && !hasFrom && hasTo);
    }

    //@@author generated
    public TaskType getTaskType() {
        return type;
    }

    public Optional<String> getStartDate() {
        return startDate;
    }

    public Optional<String> getEndDate() {
        return endDate;
    }

    public String getUnparsedArgs() {
        return unparsedArgs;
    }

}
