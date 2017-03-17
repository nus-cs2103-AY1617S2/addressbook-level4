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
     * Tokenize a string description into their respective keywords
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
            if (StringUtil.isPresent(description) && StringUtil.isPresent(defaultKeyword)) {
                tokens.put(defaultKeyword, description);
            }
            return tokens;
        }
        Vector<Pair<Integer, String> > indexKeywordPairs = new Vector<Pair<Integer, String> >();
        for (String keyword : keywords) {
            int index = description.lastIndexOf(keyword);
            if (index != INVALID_INDEX) {
                Pair<Integer, String> indexKeywordPair = new Pair<Integer, String>(index, keyword);
                indexKeywordPairs.add(indexKeywordPair);
            }
        }
        if (indexKeywordPairs.size() == 0) {
            if (StringUtil.isPresent(description) && StringUtil.isPresent(defaultKeyword)) {
                tokens.put(defaultKeyword, description);
            }
            return tokens;
        }
        Collections.sort(indexKeywordPairs, Comparator.comparing(pair -> ((Pair<Integer, String>) pair).getKey()));

        int firstIndex = indexKeywordPairs.get(START_INDEX).getKey();
        if (firstIndex > 0 && StringUtil.isPresent(defaultKeyword)) {
            String defaultToken = description.substring(START_INDEX, firstIndex - 1).trim();
            tokens.put(defaultKeyword, defaultToken);
        }

        for (int i = 0; i < indexKeywordPairs.size() - 1; i++) {
            Pair<Integer, String> currentIndexKeywordPair = indexKeywordPairs.get(i);
            Pair<Integer, String> nextIndexKeywordPair = indexKeywordPairs.get(i + 1);
            int currentIndex = currentIndexKeywordPair.getKey();
            int nextIndex = nextIndexKeywordPair.getKey();
            String keyword = currentIndexKeywordPair.getValue();
            assert currentIndex + keyword.length() <= nextIndex - 1;
            String token = description.substring(currentIndex + keyword.length(), nextIndex - 1).trim();
            if (StringUtil.isPresent(keyword) && StringUtil.isPresent(token)) {
                tokens.put(keyword, token);
            }
        }

        Pair<Integer, String> lastIndexKeywordPair = indexKeywordPairs.lastElement();
        int lastIndex = lastIndexKeywordPair.getKey();
        String lastKeyword = lastIndexKeywordPair.getValue();
        assert lastIndex + lastKeyword.length() < description.length();
        String lastToken = description.substring(lastIndex + lastKeyword.length()).trim();
        if (StringUtil.isPresent(lastKeyword) && StringUtil.isPresent(lastToken)) {
            tokens.put(lastKeyword, lastToken);
        }
        return tokens;
    }
}
