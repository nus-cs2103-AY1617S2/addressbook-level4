//@@author A0127545A
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
        String[] keywords = {"non-default"};
        HashMap<String, String> actual = KeywordTokenizer.tokenize(null, null, keywords);
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

    @Test
    public void tokenize_updateTaskCommandCaseSensitive() {
        String description = "update v0.3 to/ next wednesday tags/ ohno tag manysubtasks from/ today " +
                "floating/";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "from/",
                                                                                               "to/",
                                                                                               "by/",
                                                                                               "floating/",
                                                                                               "tags/");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "update v0.3");
        expected.put("from/", "today");
        expected.put("to/", "next wednesday");
        expected.put("tags/", "ohno tag manysubtasks");
        expected.put("floating/", "");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_updateTaskCommandCaseInsensitive() {
        String description = "update v0.3 to/ next wednesday tAgS/ ohno tag manysubtasks frOM/ today " +
                "floating/";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "from/",
                                                                                               "to/",
                                                                                               "by/",
                                                                                               "floating/",
                                                                                               "tags/");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "update v0.3");
        expected.put("from/", "today");
        expected.put("to/", "next wednesday");
        expected.put("tags/", "ohno tag manysubtasks");
        expected.put("floating/", "");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_partialWord() {
        String description = "update v0.3 /repeatuntil tomorrow /repeat daily";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description",
                "/repeatuntil", "/repeat");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "update v0.3");
        expected.put("/repeatuntil", "tomorrow");
        expected.put("/repeat", "daily");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_tokenAtTheEnd() {
        String description = "help help";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description",
                "help");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "help");
        expected.put("help", "");
        assertTrue(actual.equals(expected));
    }
}
