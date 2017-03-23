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
    public void tokenize_generalSituationOrderedKeywords() {
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "with",
                                                                                               "A",
                                                                                               "B C D",
                                                                                               "EFGHI");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "long description");
        expected.put("with", "many key-value pairs like");
        expected.put("A", "x y z");
        expected.put("B C D", "x y");
        expected.put("EFGHI", "xyzabc  def");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_generalSituationUnorderedKeywords() {
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", "B C D",
                                                                                               "with",
                                                                                               "EFGHI",
                                                                                               "A");
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "long description");
        expected.put("with", "many key-value pairs like");
        expected.put("A", "x y z");
        expected.put("B C D", "x y");
        expected.put("EFGHI", "xyzabc  def");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_updateTaskCommand() {
        String description = "update v0.3 to/next wednesday tags/ohno tag manysubtasks from/today floating/";
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
}
