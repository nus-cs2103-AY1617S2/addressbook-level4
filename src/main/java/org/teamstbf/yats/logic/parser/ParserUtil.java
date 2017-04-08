package org.teamstbf.yats.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.StringUtil;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.Recurrence;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

import com.joestelmach.natty.DateGroup;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final int NUMBER_SINGLE = 1;
    private static final String MESSAGE_WRONG_DATE_NUMBER = "One field should contain one and only one date/time.";

    /**
     * Returns the specified index in the {@code command} if it is a positive
     * unsigned integer Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
	final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
	if (!matcher.matches()) {
	    return Optional.empty();
	}

	String index = matcher.group("targetIndex");
	if (!StringUtil.isUnsignedInteger(index)) {
	    return Optional.empty();
	}
	return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty, or if the
     * list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
	List<String> elements = list.orElse(Collections.emptyList());
	return new HashSet<>(elements);
    }

    /**
     * Splits a preamble string into ordered fields.
     *
     * @return A list of size {@code numFields} where the ith element is the ith
     *         field value if specified in the input, {@code Optional.empty()}
     *         otherwise.
     */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
	return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields)).map(Optional::ofNullable)
		.collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if
     * {@code name} is present.
     */
    public static Optional<Title> parseName(Optional<String> name) throws IllegalValueException {
	assert name != null;
	return name.isPresent() ? Optional.of(new Title(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> location} into an
     * {@code Optional<Location>} if {@code location} is present.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
	assert location != null;
	return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> periodic} into an
     * {@code Optional<Periodic>} if {@code periodic} is present.
     */
    public static Optional<Periodic> parsePeriodic(Optional<String> periodic) throws IllegalValueException {
	assert periodic != null;
	return periodic.isPresent() ? Optional.of(new Periodic(periodic.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> description} into an
     * {@code Optional<Description>} if {@code description} is present.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
	assert description != null;
	return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    public static Optional<String> parseRecurrence(Optional<String> recurrence) throws IllegalValueException {
	assert recurrence != null;
	return recurrence.isPresent() ? recurrence : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> schedule} into an
     * {@code Optional<List<Date>>} if {@code schedule} is present.
     */
    public static Optional<List<Date>> parseDateList(Optional<String> schedule) throws IllegalValueException {
	// String -> DateList -> Optional
	assert schedule != null;
	if (schedule.isPresent()) {
	    return Optional.of(parseDateTime(schedule.get()));
	} else {
	    return Optional.empty();
	}
    }
    
    /**
     * Parses a {@code Optional<String> dateString} into an
     * {@code Optional<Date>} if {@code dateString} is present
     */
    public static Optional<Date> parseDateSingle(Optional<String> dateString) throws IllegalValueException {
        assert dateString != null;
        if (dateString.isPresent()) {
            return Optional.of(parseSingleDate(dateString.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
	assert tags != null;
	final Set<Tag> tagSet = new HashSet<>();
	for (String tagName : tags) {
	    tagSet.add(new Tag(tagName));
	}
	return new UniqueTagList(tagSet);
    }

    /**
     * Parses {@code String words} to a {@code List<Date>}, using natty library
     *
     * @param a
     *            string containing date and time information
     * @return a list of Date objects
     * @throws IllegalValueException
     */
    public static List<Date> parseDateTime(String words) throws IllegalValueException {
	// Date referenceDate = new Date();
	if (words == null) {
	    return null;
	}
	com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
	List<DateGroup> dateGroup = dateParser.parse(words);
	List<Date> dateList = dateGroup.isEmpty() ? new ArrayList<Date>() : dateGroup.get(0).getDates();
	return dateList;
    }

    public static Date parseSingleDate(String words) throws IllegalValueException {
	List<Date> dates = parseDateTime(words);
	// Assert.assertEquals(1, dates.size());
	if (dates.size() != NUMBER_SINGLE) {
	    throw new IllegalValueException(MESSAGE_WRONG_DATE_NUMBER);
	}
	return dates.get(0);
    }

    /**
     * Parses a {@code String} and splits it to its tokens
     *
     * @param commandText
     * @return
     */
    public static String[] stringTokenizer(String commandText) {
	String[] splitText = commandText.trim().split(" ");
	for (String element : splitText) {
	    element.trim();
	}
	return splitText;
    }

    /**
     * Sorts a string array into its natural order, increasing String array
     * should only contain integers only
     *
     * @param stringArray
     * @return
     */
    public static String[] sortIndexArr(String[] stringArray) {
	int[] intArray = new int[stringArray.length];
	for (int i = 0; i < stringArray.length; i++) {
	    intArray[i] = Integer.parseInt(stringArray[i]);
	}
	Arrays.sort(intArray);
	for (int i = 0; i < intArray.length; i++) {
	    stringArray[i] = Integer.toString(intArray[i]);
	}
	return stringArray;
    }

    /**
     * Checks the string array if all the elements are integers
     *
     * @param stringArray
     * @return
     */
    public static boolean isAllIntegers(String[] stringArray) {
	for (int i = 0; i < stringArray.length; i++) {
	    if (!isInteger(stringArray[i])) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Utility method to be used in conjuction with {@code isAllIntegers}
     *
     * @param toCheck
     * @return
     */
    public static boolean isInteger(String toCheck) {
	try {
	    Integer.parseInt(toCheck);
	} catch (NumberFormatException e) {
	    return false;
	}
	return true;
    }

}
