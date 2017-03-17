package seedu.toluist.controller.commons;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

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
        HashMap<String, String> tokens = new HashMap<String, String>();
        if (!StringUtil.isPresent(description)) {
            return tokens;
        }
        if (keywords == null || keywords.length == 0) {
            if (StringUtil.isPresent(defaultKeyword)) {
                tokens.put(defaultKeyword, description);
            }
            return tokens;
        }
        Vector<Pair<Integer, String>> indexKeywordPairs = new Vector<Pair<Integer, String>>();
        for (String keyword : keywords) {
            int index = description.lastIndexOf(keyword);
            if (index != INVALID_INDEX) {
                Pair<Integer, String> indexKeywordPair = new Pair<Integer, String>(index, keyword);
                indexKeywordPairs.add(indexKeywordPair);
            }
        }
        if (indexKeywordPairs.isEmpty()) {
            if (StringUtil.isPresent(defaultKeyword)) {
                tokens.put(defaultKeyword, description);
            }
            return tokens;
        }
        Collections.sort(indexKeywordPairs, Comparator.comparing(pair -> ((Pair<Integer, String>) pair).getKey()));

        int firstIndex = indexKeywordPairs.get(START_INDEX).getKey();
        tokens.putAll(addToken(description, defaultKeyword, START_INDEX, firstIndex - 1));

        for (int i = 0; i < indexKeywordPairs.size() - 1; i++) {
            Pair<Integer, String> currentIndexKeywordPair = indexKeywordPairs.get(i);
            Pair<Integer, String> nextIndexKeywordPair = indexKeywordPairs.get(i + 1);
            int currentIndex = currentIndexKeywordPair.getKey();
            int nextIndex = nextIndexKeywordPair.getKey();
            String keyword = currentIndexKeywordPair.getValue();
            tokens.putAll(addToken(description, keyword, currentIndex + keyword.length(), nextIndex - 1));
        }

        Pair<Integer, String> lastIndexKeywordPair = indexKeywordPairs.lastElement();
        int lastIndex = lastIndexKeywordPair.getKey();
        String lastKeyword = lastIndexKeywordPair.getValue();
        tokens.putAll(addToken(description, lastKeyword, lastIndex + lastKeyword.length(), description.length()));
        return tokens;
    }

    private static HashMap<String, String> addToken(String description, String keyword, int startIndex, int endIndex) {
        HashMap<String, String> tokens = new HashMap<String, String>();
        if (startIndex > endIndex || !StringUtil.isPresent(keyword)) {
            return tokens;
        }
        String token = description.substring(startIndex, endIndex).trim();
        tokens.put(keyword, token);
        return tokens;
    }
}
