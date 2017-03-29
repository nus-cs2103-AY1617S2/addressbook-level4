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
     * Contains the Deadline as raw String after processing. Empty if does not exist
     */
    private Optional<String> rawDeadline;

    /**
     * Contains the startEndDateTime after processing. Empty if does not exist
     */
    private Optional<StartEndDateTime> startEndDateTime;
    /**
     * Contains the raw startDateTime after processing. Empty if does not exist
     */
    private Optional<String> rawStartDateTime;
    /**
     * Contains the raw endDateTime after processing. Empty if does not exist
     */
    private Optional<String> rawEndDateTime;

    public DateTimeExtractor(String args) {
        processedArgs = args;
    }

    public void processDeadline() throws PastDateTimeException, IllegalValueException {
        // perhaps include the raw as well
        deadline = Optional.empty();
        // only try to look for deadline if there is no start end date time
        if (getProcessedStartEndDateTime() == null || !getProcessedStartEndDateTime().isPresent()) {
            // Pass rose from Uncle to Jane by tmr
            Matcher matcher = HAS_DEADLINE_FORMAT.matcher(processedArgs);

            if (matcher.matches()) {
                final String matchedRawDeadline = matcher.group("deadline");
                ZonedDateTime dateTime = ParserUtil.parseDateTimeString(matchedRawDeadline);

                deadline = Optional.of(new Deadline(dateTime));
                processedArgs = new StringBuilder(processedArgs)
                        .replace(matcher.start("byArg"), matcher.end("deadline"), "")
                        .toString();
                // there will be extra whitespaces after extracting out the start and end date
                // e.g. project by Friday t/urgent => project  t/urgent
                // so we will normalize the whitespace
                processedArgs = StringUtils.normalizeSpace(processedArgs);

            } else {
                logger.info("----------------[PROCESS DEADLINE][No deadline found]");
            }
        }
    }

    /**
     * TODO Does not throw PastDateTimeException
     * @throws IllegalValueException
     */
    public void processRawDeadline() throws IllegalValueException {
        // TODO reduce duplication
        rawDeadline = Optional.empty();
        // deadline = Optional.empty(); deadline not to be initialized
        // only try to look for deadline if there is no start end date time
        // TODO temporarily ignore checks
        //if (getProcessedStartEndDateTime() == null || !getProcessedStartEndDateTime().isPresent()) {
        // Pass rose from Uncle to Jane by tmr
        Matcher matcher = HAS_DEADLINE_FORMAT.matcher(processedArgs);

        if (matcher.matches()) {
            final String matchedRawDeadline = matcher.group("deadline");
            if (!ParserUtil.isDateTimeString(matchedRawDeadline)) {
                return;
            }

            rawDeadline = Optional.of(matchedRawDeadline);
            // TODO whether the comment about past date time still need to be kept
            // since we don't actually know the date or time we don't know if it is past date yet
            // so no need to construct Deadline or check if it is past date
            // NO new Deadline();

            // assuming that rawDeadline will result into not a past date time
            // but past date times don't pass through anyway later on even with this "wrong" processedArgs
            // because exceptions will be thrown later
            processedArgs = new StringBuilder(processedArgs)
                    .replace(matcher.start("byArg"), matcher.end("deadline"), "")
                    .toString();
            // there will be extra whitespaces after extracting out the start and end date
            // e.g. project by Friday t/urgent => project  t/urgent
            // so we will normalize the whitespace
            processedArgs = StringUtils.normalizeSpace(processedArgs);

        } else {
            logger.info("----------------[PROCESS DEADLINE][No deadline found]");
        }
        //}

    }

    public void processStartEndDateTime()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {
        // perhaps include the raw as well
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
     * TODO Does not throw PastDateTimeException and InvalidDurationException
     * @throws IllegalValueException
     */
    public void processRawStartEndDateTime() throws IllegalValueException {
        rawStartDateTime = Optional.empty();
        rawEndDateTime = Optional.empty();

        // TODO add this
        //if (getProcessedDeadline() == null || !getProcessedDeadline().isPresent()) {
        Matcher matcher = HAS_STARTENDATETIME_FORMAT.matcher(processedArgs);

        if (matcher.matches()) {
            final String matchedStartDateTime = matcher.group("startDateTime");
            final String matchedEndDateTime = matcher.group("endDateTime");
            logger.info("----------------[PROCESS RAWSTARTENDDATETIME][Start:"
                    + matchedStartDateTime + "]");
            logger.info("----------------[PROCESS RAWSTARTENDDATETIME][End: "
                    + matchedEndDateTime + "]");
            if (!ParserUtil.isDateTimeString(matchedStartDateTime)
                    || !ParserUtil.isDateTimeString(matchedEndDateTime)) {
                return;
            }
            rawStartDateTime = Optional.of(matchedStartDateTime);
            rawEndDateTime = Optional.of(matchedEndDateTime);
            // TODO whether the comment about past date time still need to be kept
            // since we don't actually know the date or time we don't know if it is past date yet
            // so no need to construct StartEndDateTime or check if it is past date
            // no InvalidDurationException as well
            // NO new StartEndDateTime();

            // assuming that rawStart and end will result into not a past date time and invaliddurationexception
            // but past date times don't pass through anyway later on even with this "wrong" processedArgs
            // because exceptions will be thrown later
            processedArgs =
                    new StringBuilder(processedArgs).replace(matcher.start("fromArg"),
                            matcher.end("endDateTime"), "").toString();
            // there will be extra whitespaces after extracting out the start and end date
            // e.g. meeting from Wednesday to Thursday t/tag => meeting from t/tag
            // so we will normalize the whitespace
            processedArgs = StringUtils.normalizeSpace(processedArgs);
        } else {
            logger.info("----------------[PROCESS RAWSTARTENDDATETIME][No Start and End Date Time found]");
        }
    }
    // TODO processStartDateTime and processEndDateTime only for special case for EditCommandParser

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
     * Returns the raw deadline if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<String> getProcessedRawDeadline() {
        return rawDeadline;
    }

    /**
     * Returns the processed startEndDateTime if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<StartEndDateTime> getProcessedStartEndDateTime() {
        return startEndDateTime;
    }

    /**
     * Returns the processed startDateTime if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<String> getProcessedStartDateTime() {
        return rawStartDateTime;
    }

    /**
     * Returns the processed endDateTime if it exists, otherwise returns empty. Returns null if not processed.
     */
    public Optional<String> getProcessedEndDateTime() {
        return rawEndDateTime;
    }

}
