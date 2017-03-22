package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.util.Pair;
import seedu.toluist.commons.util.StringUtil;

/**
 * Tokenize string of description by keywords
 */
public class KeywordTokenizer {
    public static final int INVALID_INDEX = -1;
    public static final int START_INDEX = 0;

    /**
     * Tokenize a string description into their respective keywords (by best effort matching)
     * @param description is the full text supplied by the user to be tokenized
     * @param defaultKeyword is for the rest of the text that did not get tokenized by any user-specified keywords
     * @param keywords is the list of keywords to find and to tokenize
     * @return a HashMap of keyword-token pairs
     */
    public static HashMap<String, String> tokenize(String description, String defaultKeyword, String... keywords) {
        HashMap<String, String> tokens = new HashMap<>();
        if (!StringUtil.isPresent(description)) {
            // Early termination, no description means there is nothing to tokenize.
            return tokens;
        }

        ArrayList<Pair<Integer, String>> indexKeywordPairs = new ArrayList<>();
        String[] nonNullKeywords = keywords == null ? new String[] {} : keywords;
        if (defaultKeyword != null) {
            // Everything that is not matched (guaranteed to be at the left) will be tokenized to the default keyword
            indexKeywordPairs.add(new Pair<>(START_INDEX, defaultKeyword));
        }

        for (String keyword : nonNullKeywords) {
            int index = description.lastIndexOf(keyword);
            if (index != INVALID_INDEX) {
                // Index in indexKeywordPairs refers to the index behind the last character of the keyword.
                Pair<Integer, String> indexKeywordPair = new Pair<>(index + keyword.length(), keyword);
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
                    : description.length();
            String keyword = currentIndexKeywordPair.getValue();
            String token = description.substring(startIndex, endIndex).trim();
            tokens.put(keyword, token);
        }

        return tokens;
    }
}
