package werkbook.task.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Tokenizes arguments string of the form:
 * {@code preamble <prefix>value <prefix>value ...}<br>
 * e.g. {@code some preamble text /t 11.00/dToday /t 12.00 /k /m July} where
 * prefixes are {@code /t /d /k /m}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code /k} in
 * the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be
 * discarded.<br>
 * 3. A prefix need not have leading and trailing spaces e.g. the
 * {@code /d in 11.00/dToday} in the above example<br>
 * 4. An argument may be repeated and all its values will be accumulated e.g.
 * the value of {@code /t} in the above example.<br>
 */
public class ArgumentTokenizer {

    /** Given prefixes **/
    private final List<Prefix> prefixes;

    /** Arguments found after tokenizing **/
    private final Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();

    /**
     * Creates an ArgumentTokenizer that can tokenize arguments string as
     * described by prefixes
     */
    public ArgumentTokenizer(Prefix... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

    /**
     * @param argsString arguments string of the form: preamble <prefix>value
     *            <prefix>value ...
     * @throws IllegalValueException
     */
    public void tokenize(String argsString) throws IllegalValueException {
        resetTokenizerState();
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        List<PrefixPosition> filteredList = filterPositionsForDate(argsString, positions);
        extractArguments(argsString, filteredList);
    }

    /**
     * Returns last value of given prefix.
     */
    public Optional<String> getValue(Prefix prefix) {
        return getAllValues(prefix).flatMap((values) -> Optional.of(values.get(values.size() - 1)));
    }

    /**
     * Returns all values of given prefix.
     */
    public Optional<List<String>> getAllValues(Prefix prefix) {
        if (!this.tokenizedArguments.containsKey(prefix)) {
            return Optional.empty();
        }
        List<String> values = new ArrayList<>(this.tokenizedArguments.get(prefix));
        return Optional.of(values);
    }

    // @@author A0139903B
    /**
     * Returns the full preamble (text before and after the first valid prefix),
     * if any. Leading/trailing spaces will be trimmed. If the string before the
     * first prefix is empty, Optional.empty() will be returned.
     */
    public Optional<String> getFullPreamble() {
        Optional<List<String>> storedPreamble = getAllValues(new Prefix(""));

        if (!storedPreamble.isPresent()) {
            return Optional.empty();
        }

        String fullPreamble = String.join(" ", storedPreamble.get());

        /* A empty full preamble is when the concat string is empty */
        if (!fullPreamble.isEmpty()) {
            return Optional.of(fullPreamble.trim());
        } else {
            return Optional.empty();
        }
    }
    // @@author

    private void resetTokenizerState() {
        this.tokenizedArguments.clear();
    }

    /**
     * Finds all positions in an arguments string at which any prefix appears
     *
     * @throws IllegalValueException
     */
    private List<PrefixPosition> findAllPrefixPositions(String argsString) throws IllegalValueException {
        List<PrefixPosition> positions = new ArrayList<>();

        for (Prefix prefix : this.prefixes) {
            positions.addAll(findPrefixPositions(argsString, prefix));
        }

        return positions;
    }

    /**
     * Finds all positions in an arguments string at which a given
     * {@code prefix} appears
     *
     * @throws IllegalValueException
     */
    private List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix)
            throws IllegalValueException {
        List<PrefixPosition> positions = new ArrayList<>();

        int argumentStart = argsString.indexOf(prefix.getPrefix());
        while (argumentStart != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
            positions.add(extendedPrefix);
            argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
        }

        return positions;
    }

    // @@author A0139903B
    /**
     * Extracts the preamble/arguments and stores them in local variables.
     *
     * @param prefixPositions must contain all prefixes in the
     *            {@code argsString}
     */
    private void extractArguments(String argsString, List<PrefixPosition> prefixPositions) {
        // Extract the prefixed arguments and preamble (if any)
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i),
                    prefixPositions.get(i + 1));
            saveArgument(prefixPositions.get(i).getPrefix(), argValue);
        }
    }

    /**
     * Filters a list of prefix positions for dates, it will check for dates
     * between any two prefixes and extract their positions if so. If there
     * isn't a valid date behind a prefix, it will simply treat the prefix as a
     * text
     *
     * @param argsString Argument string
     * @param prefixPositions List of prefix positions to be passed in
     * @return Returns a list of prefix positions with the non-date prefixes
     *         filtered out
     */
    private List<PrefixPosition> filterPositionsForDate(String argsString,
            List<PrefixPosition> prefixPositions) {
        List<PrefixPosition> filteredList = new ArrayList<PrefixPosition>();

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Extract the prefixed arguments and preamble (if any)
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            Prefix prefix = prefixPositions.get(i).getPrefix();

            int valueStartPos = prefixPositions.get(i).getStartPosition() + prefix.getPrefix().length();
            String value = argsString.substring(valueStartPos, prefixPositions.get(i + 1).getStartPosition());

            boolean isValidDate = DateTimeParser.isValidDate(value);

            System.out.println("Prefix " + prefix.getPrefix() + " value: " + value);

            // If it is a date, text following is not empty and is not valid
            // date, continue
            if (prefix.isDateTime() && !value.isEmpty() && !isValidDate) {
                PrefixPosition another = new PrefixPosition(new Prefix(""),
                        prefixPositions.get(i + 1).getStartPosition());
                filteredList.add(another);
                continue;
            }

            // Add the normal prefix here
            filteredList.add(prefixPositions.get(i));
            PrefixPosition another = new PrefixPosition(new Prefix(""),
                    prefixPositions.get(i + 1).getStartPosition());
            filteredList.add(another);
        }
        return filteredList;
    }
    // @@author

    /**
     * Returns the trimmed value of the argument specified by
     * {@code currentPrefixPosition}. The end position of the value is
     * determined by {@code nextPrefixPosition}
     */
    private String extractArgumentValue(String argsString, PrefixPosition currentPrefixPosition,
            PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        // @@author A0139903B
        // Should already be filtered by now, time to convert to fit date time
        // format
        if (prefix.isDateTime()) {
            value = DateTimeParser.parse(value);
        }
        // @@author
        return value.trim();
    }

    /**
     * Stores the value of the given prefix in the state of this tokenizer
     */
    private void saveArgument(Prefix prefix, String value) {
        if (this.tokenizedArguments.containsKey(prefix)) {
            this.tokenizedArguments.get(prefix).add(value);
            return;
        }

        List<String> values = new ArrayList<>();
        values.add(value);
        this.tokenizedArguments.put(prefix, values);
    }

    /**
     * A prefix that marks the beginning of an argument. e.g. '/t' in 'add James
     * /t friend'
     */
    public static class Prefix {
        final String prefix;
        private final boolean isDateTime;

        public Prefix(String prefix) {
            this.prefix = prefix;
            this.isDateTime = false;
        }

        // Overloaded constructor for prefixes with date time that follows
        public Prefix(String prefix, boolean isDateTime) {
            this.prefix = prefix;
            this.isDateTime = isDateTime;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public boolean isDateTime() {
            return this.isDateTime;
        }

        @Override
        public int hashCode() {
            return this.prefix == null ? 0 : this.prefix.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Prefix)) {
                return false;
            }
            if (obj == this) {
                return true;
            }

            Prefix otherPrefix = (Prefix) obj;
            return otherPrefix.getPrefix().equals(getPrefix());
        }
    }

    /**
     * Represents a prefix's position in an arguments string
     */
    private class PrefixPosition {
        private int startPosition;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return this.startPosition;
        }

        Prefix getPrefix() {
            return this.prefix;
        }
    }

}
