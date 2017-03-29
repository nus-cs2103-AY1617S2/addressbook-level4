package seedu.address.logic.parser;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
/**
 * Extracts Deadlines and StartEndDateTime if they exist
 *
 */
public class DateTimeExtractor {
    private final Logger logger = LogsCenter.getLogger(DateTimeExtractor.class);

    public static final Pattern HAS_STARTENDATETIME_FORMAT = Pattern.compile(
            // match everything greedily so we can also include any number of [from] and [to]
            ".*"
            // match the final from with spaces to prevent matching words like therefrom and fromage
            + "(?<fromArg>\\sfrom\\s)"
            // now match greedily all the way until the next expression to match
            + "(?<startDateTime>.+)"
            // match a to with spaces to prevent matching words like auto and tomorrow
            + "\\sto\\s"
            // now match LAZILY as the next expression that follows is optional
            + "(?<endDateTime>.+?)"
            // match a white space character and a tag with zero or more times
            + "(?<tagArguments>(\\st/[^/]+)*)");
            // match a white space character with zero or one tag; // TODO change tag format
            // + "(?<tagArguments>(\\stags\\s[^/]+)?)");

    public static final Pattern HAS_DEADLINE_FORMAT = Pattern.compile(
            // match everything greedily so we can also include any number of [by]
            ".*"
            // match the final by with spaces to prevent matching words like baby and bypass
            + "(?<byArg>\\sby\\s)"
            // now match LAZILY as the next expression that follows is optional
            + "(?<deadline>.+?)"
            // match a white space character and a tag with zero or more times
            + "(?<tagArguments>(\\st/[^/]+)*)");
            // match a white space character with zero or one tag;
            // + "(?<tagArguments>(\\stags\\s[^/]+)?)"); // TODO change tag format

    /**
     * Contains the argument string after processing
     */
    private String processedArgs;
    /**
     * Contains the Deadline after processing. Empty if does not exist
     */
    private Optional<Deadline> deadline;
    /**
     * Contains the startEndDateTime after processing. Empty if does not exist
     */
    private Optional<StartEndDateTime> startEndDateTime;

    public DateTimeExtractor(String args) {
        processedArgs = args;
    }

    public void processDeadline() throws PastDateTimeException, IllegalValueException {
        deadline = Optional.empty();
        // only try to look for deadline if there is no start end date time
        if (getProcessedStartEndDateTime() == null || !getProcessedStartEndDateTime().isPresent()) {
            // Pass rose from Uncle to Jane by tmr
            Matcher matcher = HAS_DEADLINE_FORMAT.matcher(processedArgs);

            if (matcher.matches()) {
                ZonedDateTime dateTime = ParserUtil.parseDateTimeString(matcher.group("deadline"));

                deadline = Optional.of(new Deadline(dateTime));
                processedArgs = new StringBuilder(processedArgs)
                        .replace(matcher.start("byArg"), matcher.end("deadline"), "")
                        .toString();
                // there will be extra whitespaces after extracting out the start and end date
                // e.g. project by Friday t/urgent => project t/urgent
                // so we will normalize the whitespace
                processedArgs = StringUtils.normalizeSpace(processedArgs);

            } else {
                logger.info("----------------[PROCESS DEADLINE][No deadline found]");
            }
        }

    }

    public void processStartEndDateTime()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {
        startEndDateTime = Optional.empty();

        // TODO add this
        //if (getProcessedDeadline() == null || !getProcessedDeadline().isPresent()) {
        Matcher matcher = HAS_STARTENDATETIME_FORMAT.matcher(processedArgs);

        if (matcher.matches()) {
            logger.info("----------------[PROCESS STARTENDDATETIME][Start:"
                    + matcher.group("startDateTime") + "]");
            logger.info("----------------[PROCESS STARTENDDATETIME][End: "
                    + matcher.group("endDateTime") + "]");

            ZonedDateTime startDateTime =
                    ParserUtil.parseDateTimeString(matcher.group("startDateTime"));
            ZonedDateTime endDateTime =
                    ParserUtil.parseDateTimeString(matcher.group("endDateTime"));

            startEndDateTime = Optional.of(new StartEndDateTime(startDateTime, endDateTime));
            processedArgs =
                    new StringBuilder(processedArgs).replace(matcher.start("fromArg"),
                            matcher.end("endDateTime"), "").toString();
            // there will be extra whitespaces after extracting out the start and end date
            // e.g. meeting from Wednesday to Thursday t/tag => meeting from t/tag
            // so we will normalize the whitespace
            processedArgs = StringUtils.normalizeSpace(processedArgs);
        } else {
            logger.info("----------------[PROCESS STARTENDDATETIME][No Start and End Date Time found]");
        }
    }

    /**
     * Returns the argument after processing
     */
    public String getProcessedArgs() {
        return processedArgs;
    }

    /**
     * Returns the processed deadline if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<Deadline> getProcessedDeadline() {
        return deadline;
    }

    /**
     * Returns the processed startEndDateTime if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<StartEndDateTime> getProcessedStartEndDateTime() {
        return startEndDateTime;
    }

}
