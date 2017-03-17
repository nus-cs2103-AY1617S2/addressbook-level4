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
        String[] keywords = {"non-default"};
        HashMap<String, String> actual = KeywordTokenizer.tokenize("description", null, keywords);
        HashMap<String, String> expected = new HashMap<String, String>();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_descriptionAndMatchingNonDefaultKeywordsGiven() {
        String[] keywords = {"non default"};
        HashMap<String, String> actual = KeywordTokenizer.tokenize("non default description", null, keywords);
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("non default", "description");
        assertTrue(actual.equals(expected));
    }

    @Test
    public void tokenize_generalSituationOrderedKeywords() {
        String[] keywords = {"with", "A", "B C D", "EFGHI"};
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", keywords);
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
        String[] keywords = {"B C D", "with", "EFGHI", "A"};
        String description = "long description with many key-value pairs like A x y z B C D x y EFGHI  xyzabc  def  ";
        HashMap<String, String> actual = KeywordTokenizer.tokenize(description, "description", keywords);
        HashMap<String, String> expected = new HashMap<String, String>();
        expected.put("description", "long description");
        expected.put("with", "many key-value pairs like");
        expected.put("A", "x y z");
        expected.put("B C D", "x y");
        expected.put("EFGHI", "xyzabc  def");
        assertTrue(actual.equals(expected));
    }
}
