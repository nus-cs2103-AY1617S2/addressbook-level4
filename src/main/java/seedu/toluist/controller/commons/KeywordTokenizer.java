//@@author A0127545A
package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.util.Pair;
import seedu.toluist.commons.util.StringUtil;

/**
 * Tokenize string of description by keywords
 */
public class KeywordTokenizer {
    public static final int INDEX_INVALID = -1;
    public static final int INDEX_START = 0;

    /**
     * Tokenize a string description into their respective keywords (by best effort matching)
     * @param description is the full text supplied by the user to be tokenized
     * @param defaultKeyword is for the rest of the text that did not get tokenized by any user-specified keywords
     * @param keywords is the list of keywords to find and to tokenize
     * @return a HashMap of keyword-token pairs
     */
    public static HashMap<String, String> tokenize(String description, String defaultKeyword, String... keywords) {
        HashMap<String, String> tokens = new HashMap<>();
        for (Pair<String, String> token : tokenizeInOrder(description, defaultKeyword, keywords)) {
            tokens.put(token.getKey(), token.getValue());
        }
        return tokens;
    }

    /**
     * Tokenize a string description into their respective keywords (by best effort matching)
     * @param description is the full text supplied by the user to be tokenized
     * @param defaultKeyword is for the rest of the text that did not get tokenized by any user-specified keywords
     * @param keywords is the list of keywords to find and to tokenize
     * @return a list of keyword-token pairs, in order of appearances
     */
    public static List<Pair<String, String>> tokenizeInOrder(String description, String defaultKeyword,
                                                             String... keywords) {
        List<Pair<String, String>> tokens = new ArrayList<>();
        if (!StringUtil.isPresent(description)) {
            // Early termination, no description means there is nothing to tokenize.
            return tokens;
        }
        String normalizedDescription = StringUtil.SINGLE_SPACE + description + StringUtil.SINGLE_SPACE;
        String normalizedDescriptionInLowerCase = normalizedDescription.toLowerCase();

        ArrayList<Pair<Integer, String>> indexKeywordPairs = new ArrayList<>();
        String[] nonNullKeywords = keywords == null ? new String[0] : keywords;
        if (defaultKeyword != null) {
            // Everything that is not matched (guaranteed to be at the left) will be tokenized to the default keyword
            indexKeywordPairs.add(new Pair<>(INDEX_START, defaultKeyword));
        }

        for (String keyword : nonNullKeywords) {
            int index = normalizedDescriptionInLowerCase
                    .lastIndexOf(StringUtil.SINGLE_SPACE + keyword + StringUtil.SINGLE_SPACE);

            if (index != INDEX_INVALID) {
                // Index in indexKeywordPairs refers to the index behind the last character of the keyword.
                Pair<Integer, String> indexKeywordPair = new Pair<>(index + keyword.length() + 1, keyword);
                indexKeywordPairs.add(indexKeywordPair);
            }
        }

        Collections.sort(indexKeywordPairs, Comparator.comparing(pair -> ((Pair<Integer, String>) pair).getKey()));


        for (int i = 0; i < indexKeywordPairs.size(); i++) {
            Pair<Integer, String> currentIndexKeywordPair = indexKeywordPairs.get(i);
            int startIndex = currentIndexKeywordPair.getKey();
            // Generally, we match the text to the index before the first character of the next keyword.
            // For last pair of currentIndexKeywordPair, we simply match the text to the end of the description.
            int endIndex = i + 1 < indexKeywordPairs.size()
                    ? indexKeywordPairs.get(i + 1).getKey() - indexKeywordPairs.get(i + 1).getValue().length()
                    : normalizedDescription.length();
            String keyword = currentIndexKeywordPair.getValue();
            String token = normalizedDescription.substring(startIndex, endIndex).trim();
            tokens.add(new Pair<>(keyword, token));
        }

        return tokens;
    }
}
