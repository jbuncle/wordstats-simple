package uk.co.blackpeardigital.wordstats.simple;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jbuncle
 */
public class WordStatsTest {

    /**
     * Test of max method, of class WordStats.
     */
    @Test
    public void testMax() {
        System.out.println("max");

        final Collection<Integer> collection = Arrays.asList(1, 2, 5, 3, 4);

        final int result = WordStats.max(collection);

        assertEquals(5, result);
    }

    /**
     * Test of sum method, of class WordStats.
     */
    @Test
    public void testSum() {
        System.out.println("sum");

        final Collection<Integer> occurrences = Arrays.asList(1, 2, 3, 4, 5);

        final int result = WordStats.sum(occurrences);

        assertEquals(15, result);
    }

    /**
     * Test of entryValues method, of class WordStats.
     */
    @Test
    public void testEntryValues() {
        System.out.println("entryValues");

        final Collection<Entry<Integer, Integer>> collection = Arrays.asList(
                new AbstractMap.SimpleEntry<>(1, 2),
                new AbstractMap.SimpleEntry<>(2, 2),
                new AbstractMap.SimpleEntry<>(3, 3)
        );

        Collection result = WordStats.entryValues(collection);

        assertEquals(Arrays.asList(2, 2, 3), result);
    }

    /**
     * Test of multiplyEntries method, of class WordStats.
     */
    @Test
    public void testMultiplyEntries() {
        System.out.println("multiplyEntries");

        final Collection<Entry<Integer, Integer>> collection = Arrays.asList(
                new AbstractMap.SimpleEntry<>(1, 2),
                new AbstractMap.SimpleEntry<>(2, 2),
                new AbstractMap.SimpleEntry<>(3, 3)
        );

        final Collection<Integer> result = WordStats.multiplyEntries(collection);

        assertEquals(Arrays.asList(2, 4, 9), result);

    }

    /**
     * Test of keysByValue method, of class WordStats.
     */
    @Test
    public void testKeysByValue() {
        System.out.println("keysByValue");

        final Collection<Entry<Integer, Integer>> collection = Arrays.asList(
                new AbstractMap.SimpleEntry<>(1, 2),
                new AbstractMap.SimpleEntry<>(2, 2),
                new AbstractMap.SimpleEntry<>(3, 3)
        );

        final Collection<Integer> result = WordStats.keysByValue(collection, 2);

        assertEquals(Arrays.asList(1, 2), result);
    }

    /**
     * Test of getWordLengths method, of class WordStats.
     */
    @Test
    public void testGetWordLengths() {
        System.out.println("getWordLengths");

        final Collection<String> lines = Arrays.asList("one two three four five");
        final Collection<Map.Entry<Integer, Integer>> expResult = Arrays.asList(
                new AbstractMap.SimpleEntry<>(3, 2),
                new AbstractMap.SimpleEntry<>(4, 2),
                new AbstractMap.SimpleEntry<>(5, 1)
        );

        final Collection<Map.Entry<Integer, Integer>> result = WordStats.getWordLengths(lines);

        assertEquals(expResult, result);
    }

    /**
     * Test of round method, of class WordStats.
     */
    @Test
    public void testRound() {
        System.out.println("round");
        final double value = 1.2345;
        final double expResult = 1.235;

        final double result = WordStats.round(value, 3);

        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of formatData method, of class WordStats.
     */
    @Test
    public void testFormatData() {
        System.out.println("formatData");
        final int totalWordCount = 9;
        final double average = 4.556;
        final Collection<Map.Entry<Integer, Integer>> lengths = Arrays.asList(
                new AbstractMap.SimpleEntry<>(1, 1),
                new AbstractMap.SimpleEntry<>(2, 1),
                new AbstractMap.SimpleEntry<>(3, 1),
                new AbstractMap.SimpleEntry<>(4, 2),
                new AbstractMap.SimpleEntry<>(5, 2),
                new AbstractMap.SimpleEntry<>(7, 1),
                new AbstractMap.SimpleEntry<>(10, 1)
        );
        final Collection<Integer> mostFrequentLengths = Arrays.asList(4, 5);
        final int mostFrequentLength = 2;
        final String expResult = "Word count = 9\n"
                + "Average word length = 4.556\n"
                + "Number of words of length 1 is 1\n"
                + "Number of words of length 2 is 1\n"
                + "Number of words of length 3 is 1\n"
                + "Number of words of length 4 is 2\n"
                + "Number of words of length 5 is 2\n"
                + "Number of words of length 7 is 1\n"
                + "Number of words of length 10 is 1\n"
                + "The most frequently occurring word length is 2, for word lengths of 4 & 5\n";

        final String result = WordStats.formatData(totalWordCount, average, lengths, mostFrequentLengths, mostFrequentLength);

        assertEquals(expResult, result);

    }

    /**
     * Test of analyse method, of class SimpleImpl.
     *
     * This is more of an integration test.
     */
    @Test
    public void testAnalyse() {
        System.out.println("anaylse");

        final Collection<String> string = Arrays.asList("Hello world & good morning. The date is 18/05/2016");
        final String expResult = "Word count = 9\n"
                + "Average word length = 4.556\n"
                + "Number of words of length 1 is 1\n"
                + "Number of words of length 2 is 1\n"
                + "Number of words of length 3 is 1\n"
                + "Number of words of length 4 is 2\n"
                + "Number of words of length 5 is 2\n"
                + "Number of words of length 7 is 1\n"
                + "Number of words of length 10 is 1\n"
                + "The most frequently occurring word length is 2, for word lengths of 4 & 5\n";

        final String report = WordStats.analyse(string);

        assertEquals(expResult, report);

    }

}
