package werkbook.task.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werkbook.task.model.task.EndDateTime;

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
     */
    public void tokenize(String argsString) {
        resetTokenizerState();
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        extractArguments(argsString, positions);
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

    /**
     * Returns the preamble (text before the first valid prefix), if any.
     * Leading/trailing spaces will be trimmed. If the string before the first
     * prefix is empty, Optional.empty() will be returned.
     */
    public Optional<String> getPreamble() {

        Optional<String> storedPreamble = getValue(new Prefix(""));

        /* An empty preamble is considered 'no preamble present' */
        if (storedPreamble.isPresent() && !storedPreamble.get().isEmpty()) {
            return storedPreamble;
        } else {
            return Optional.empty();
        }
    }

    private void resetTokenizerState() {
        this.tokenizedArguments.clear();
    }

    /**
     * Finds all positions in an arguments string at which any prefix appears
     */
    private List<PrefixPosition> findAllPrefixPositions(String argsString) {
        List<PrefixPosition> positions = new ArrayList<>();

        for (Prefix prefix : this.prefixes) {
            positions.addAll(findPrefixPositions(argsString, prefix));
        }

        return positions;
    }

    //@@author A0139903B
    /**
     * Finds all positions in an arguments string at which a given
     * {@code prefix} appears
     */
    private List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        // -1 means not found
        int argumentStart = argsString.indexOf(prefix.getPrefix());
//        System.out.println("Current prefix is:" + prefix.getPrefix() + " at pos: " + argumentStart);

        // If it's a date time, find the position that returns a date time behind it
        if (prefix.isDateTime()) {
            while (argumentStart != -1) {
                PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
//                System.out.println("Current index: " + argumentStart);
                if (isValidDateCommand(argsString, argumentStart + prefix.getPrefix().length())) {
                    positions.add(extendedPrefix);
                }
                argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
            }
        } else {
            while (argumentStart != -1) {
                PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
                positions.add(extendedPrefix);
                argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
            }
        }

        return positions;
    }

    //@@author A0139903B
    /**
     * Checks if a command is a date time command by checking the string
     * after the command to see if a valid date exists.
     * If there is no valid date, then it will return false.
     * @param argsString    String entered by user
     * @param prefix        Starting position of the command
     * @return              True if the command is valid
     */
    private boolean isValidDateCommand(String argsString, int startPosition) {
        // Get the value in between this prefix and the next
        String dateTime = argsString.substring(startPosition, startPosition + " 01/01/2000 2000".length())
                .trim();

        boolean isValid = EndDateTime.isValidEndDateTime(dateTime);
        System.out.println("The date: " + dateTime + " is " + isValid);
        // Check if it's a valid date, if so, then break
        return isValid;
    }

    /**
     * Extracts the preamble/arguments and stores them in local variables.
     *
     * @param prefixPositions must contain all prefixes in the
     *            {@code argsString}
     */
    private void extractArguments(String argsString, List<PrefixPosition> prefixPositions) {
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
//            System.out.println("Looking command at: " + prefixPositions.get(i).getPrefix().getPrefix());
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i),
                    prefixPositions.get(i + 1));

//            System.out.println("Extracted: " + argValue + " from: " + prefixPositions.get(i).startPosition + " to: "
//                    + prefixPositions.get(i + 1).startPosition);
            saveArgument(prefixPositions.get(i).getPrefix(), argValue);
        }

    }

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
