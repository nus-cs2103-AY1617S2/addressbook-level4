//@@author Melvin
package seedu.toluist.controller.commons;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class IndexParserTest {
    //---------------- Tests for splitIndexes --------------------------------------

    @Test
    public void splitIndexes_obtainSingleIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" 3  ", 8);
        List<Integer> expected = Arrays.asList(3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesToEndIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" - 3", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromStartIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes(" 3 -", 8);
        List<Integer> expected = Arrays.asList(3, 4, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromStartIndexToEndIndex() {
        List<Integer> actual = IndexParser.splitStringToIndexes("3-5", 8);
        List<Integer> expected = Arrays.asList(3, 4, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromPoorlyFormattedStringIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("1-2  ,    3-   4   5  -6   , 7   -      8", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("1 3,  7", 8);
        List<Integer> expected = Arrays.asList(1, 3, 7);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedUnorderedIndexes1() {
        List<Integer> actual = IndexParser.splitStringToIndexes("3, 2, 8, 5-7", 8);
        List<Integer> expected = Arrays.asList(2, 3, 5, 6, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesFromAssortedUnorderedIndexes2() {
        List<Integer> actual = IndexParser.splitStringToIndexes("7, - 3", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 7);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_obtainIndexesComplexStringIndexes() {
        List<Integer> actual = IndexParser.splitStringToIndexes("- 3, 5, 7 - 12", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 7, 8);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex1() {
        List<Integer> actual = IndexParser.splitStringToIndexes("banana", 8);
        List<Integer> expected = Arrays.asList();
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex2() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3 banana 5", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex3() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3 5 banana", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex4() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3, banana, 5", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void splitIndexes_withInvalidIndex5() {
        List<Integer> actual = IndexParser.splitStringToIndexes("-3, 5, banana", 8);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5);
        assertTrue(actual.equals(expected));
    }
}
