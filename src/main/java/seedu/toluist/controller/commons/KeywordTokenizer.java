package seedu.toluist.controller.commons;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.util.Pair;
import seedu.toluist.commons.util.StringUtil;

public class KeywordTokenizer {
    public static final int INVALID_INDEX = -1;
    public static final int START_INDEX = 0;

    public static HashMap<String, String> tokenize(String description, String defaultKeyword, String... keywords) {
        HashMap<String, String> tokens = new HashMap<String, String>();
        if (keywords.length == 0) {

            tokens.put(defaultKeyword, description);

            return tokens;
        }
        Vector<Pair<Integer, String> > tokenStartIndexes = new Vector<Pair<Integer, String> >();
        for (String keyword : keywords) {
            int index = description.lastIndexOf(keyword);
            if (index != INVALID_INDEX) {
                tokenStartIndexes.add(new Pair<Integer, String>(index, keyword));
            }
        }
        if (tokenStartIndexes.size() == 0) {
            if (StringUtil.isPresent(description) && StringUtil.isPresent(defaultKeyword)) {

                tokens.put(defaultKeyword, description);

            }
            return tokens;
        }
        Collections.sort(tokenStartIndexes, Comparator.comparing(pair -> ((Pair<Integer, String>) pair).getKey()));
        int firstStartIndex = tokenStartIndexes.get(START_INDEX).getKey();
        if (firstStartIndex > 0 && StringUtil.isPresent(defaultKeyword)) {
            String defaultToken = description.substring(START_INDEX, firstStartIndex - 1).trim();
            tokens.put(defaultKeyword, defaultToken);
        }
        for (int i = 0; i < tokenStartIndexes.size() - 1; i++) {
            Pair<Integer, String> currentTokenStartIndex = tokenStartIndexes.get(i);
            Pair<Integer, String> nextTokenStartIndex = tokenStartIndexes.get(i + 1);
            int currentIndex = currentTokenStartIndex.getKey();
            int nextIndex = nextTokenStartIndex.getKey();
            String keyword = currentTokenStartIndex.getValue();
            int keywordLength = keyword.length();
            assert currentIndex + keywordLength <= nextIndex - 1;
            String token = description.
                    substring(currentIndex + keywordLength, nextIndex - 1).
                    trim();
            if (StringUtil.isPresent(keyword) && StringUtil.isPresent(token)) {
                tokens.put(keyword, token);
            }
        }
        Pair<Integer, String> lastTokenStartIndex = tokenStartIndexes.get(tokenStartIndexes.size() - 1);
        int lastIndex = lastTokenStartIndex.getKey();
        String lastKeyword = lastTokenStartIndex.getValue();
        String lastToken = description.substring(lastIndex + lastKeyword.length()).trim();
        if (StringUtil.isPresent(lastKeyword) && StringUtil.isPresent(lastToken)) {
            tokens.put(lastKeyword, lastToken);
        }
        return tokens;
    }
}
