package seedu.toluist.controller.commons;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class KeywordTokenizerTest {
    //---------------- Tests for tokenize --------------------------------------

    @Test
    public void tokenize_allNullValues() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, null, (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyDescriptionGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("Description", null, (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyDefaultKeywordGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, "default", (String[]) null);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_onlyOneNonDefaultKeywordGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, null, "non-default");
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndDefaultKeywordGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("description", "default");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("default", "description");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndNonMatchingNonDefaultKeywordsGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("description", null, "non-default");
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndMatchingNonDefaultKeywordsGiven() {
        HashMap<String, String> actual = KeywordTokenizer.tokenize("non default description", null, "non default");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("non default", "description");
        assertTrue(actual.equals(expected));
    }
}
