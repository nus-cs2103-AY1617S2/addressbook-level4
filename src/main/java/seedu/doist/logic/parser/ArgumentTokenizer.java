package seedu.doist.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@@author A0147620L
/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text /t 11.00/dToday /t 12.00 /k /m July}  where prefixes are {@code /t /d /k /m}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code /k} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. A prefix need not have leading and trailing spaces e.g. the {@code /d in 11.00/dToday} in the above example<br>
 * 4. An argument may be repeated and all its values will be accumulated e.g. the value of {@code /t}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /** Given prefixes **/
    private final List<Prefix> prefixes;
    private ArrayList<Prefix> temp;
    private int dateFormat;
    public static final int DATE_BY = 1;
    public static final int DATE_FROM = 2;
    public static final int DATE_TO = 2;
    public static final int DATE_INVALID = -1;
    public static final int DATE_NIL = 0;

    /** Arguments found after tokenizing **/
    private final Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();

    /** Method to 'BY' parameter is used **/
    private int validateBy(ArrayList<String> token) {
        for (String prefix: token) {
            if (prefix.equals(CliSyntax.PREFIX_BY.getPrefix())) {
                return DATE_BY;
            }
        }
        return DATE_NIL;
    }

    /** Method to check if 'FROM' parameter is used**/
    private int validateFrom(ArrayList<String> token) {
        for (String prefix: token) {
            if (prefix.equals(CliSyntax.PREFIX_FROM.getPrefix())) {
                return DATE_FROM;
            }
        }
        return DATE_NIL;
    }

    /** Method to check if 'TO' parameter is used **/
    private int validateTo(ArrayList<String> token) {
        for (String prefix: token) {
            if (prefix.equals(CliSyntax.PREFIX_TO.getPrefix())) {
                return DATE_TO;
            }
        }
        return DATE_NIL;
    }

    /**
     * Creates an ArgumentTokenizer that can tokenize arguments string as described by prefixes
     */
    public ArgumentTokenizer(Prefix... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
        dateFormat = -1;
    }

    /**
     * @param argsString arguments string of the form: preamble <prefix>value <prefix>value ...
     */
    public void tokenize(String argsString) {
        resetTokenizerState();
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        extractArguments(argsString, positions);
    }

    /**
     * Returns a HashMap that maps the present prefixes with their arguments
     * @return
     */
    public Map<String, List<String>> getTokenizedArguments() {
        Map<String, List<String>> arguments = new HashMap<String, List<String>>();
        for (int i = 0; i < prefixes.size(); i++) {
            Prefix prefix = prefixes.get(i);
            List<String> prefixArgs = tokenizedArguments.get(prefix);
            if (!(prefixArgs == null || prefixArgs.size() == 0)) {
                arguments.put(prefix.getPrefix(), prefixArgs);
            }
        }
        return arguments;
    }

    /**
     * Returns true if all tokens passed are valid prefixes
     * @param tokens
     * @return boolean
     */
    public boolean validateTokens(ArrayList<String> tokens) {
        boolean flag = true;
        this.temp = new ArrayList<Prefix>(this.prefixes);
        for (String token : tokens) {
            flag = flag && validateToken(token);
        }
        return flag;
    }

    /**
     * Returns true if a token is a valid prefix
     * @param tokens
     * @return boolean
     */
    public boolean validateToken(String token) {
        boolean flag = false;
        for (Prefix prefix: temp) {
            if (prefix.getPrefix().equals(token)) {
                temp.remove(prefix);
                return true;
            }
        }
        return flag;
    }

    /**
     * Method to validate whether the date parameters in the command are valid
     * eg. The command "add Buy milk \\from today \\by Friday" should fail, as \from should always be accompanied by \to
     * @param tokens recognized in the command
     * @return A number corresponding to the command format it matches
     * eg 0 means no date parameters were provided, 1 means only \by was user,
     * 2 means both \from and \to were provided, and -1 means the command format is illegal
     */

    public int validateDate(ArrayList<String> tokens) {
        int count = 0;
        count = count + validateBy(tokens) + validateFrom(tokens) + validateTo(tokens);
        switch (count) {
        case DATE_NIL : dateFormat = DATE_NIL; break;
        case DATE_BY : dateFormat = DATE_BY; break;
        case (DATE_FROM + DATE_TO) : dateFormat = DATE_TO; break;
        default : dateFormat = DATE_INVALID; break;
        }
        return dateFormat;
    }

    public int getDateFormat() {
        return dateFormat;
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
     * Returns the preamble (text before the first valid prefix), if any. Leading/trailing spaces will be trimmed.
     *     If the string before the first prefix is empty, Optional.empty() will be returned.
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

    /**
     * Finds all positions in an arguments string at which a given {@code prefix} appears
     */
    private List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int argumentStart = argsString.indexOf(prefix.getPrefix());
        while (argumentStart != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
            positions.add(extendedPrefix);
            argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
        }

        return positions;
    }

    /**
     * Extracts the preamble/arguments and stores them in local variables.
     * @param prefixPositions must contain all prefixes in the {@code argsString}
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
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            saveArgument(prefixPositions.get(i).getPrefix(), argValue);
        }

    }

    /**
     * Returns the trimmed value of the argument specified by {@code currentPrefixPosition}.
     *    The end position of the value is determined by {@code nextPrefixPosition}
     */
    private String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
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
     * A prefix that marks the beginning of an argument.
     * e.g. '\\under' in 'add James \\under friend'
     */
    public static class Prefix {
        final String prefix;

        Prefix(String prefix) {
            this.prefix = prefix;
        }

        String getPrefix() {
            return this.prefix;
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

        @Override
        public String toString() {
            return this.prefix;
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
