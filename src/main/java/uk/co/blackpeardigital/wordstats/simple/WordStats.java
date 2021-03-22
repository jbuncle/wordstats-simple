package uk.co.blackpeardigital.wordstats.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author jbuncle
 */
public class WordStats {

    public static void main(String[] args) throws IOException {

        final Collection<String> lines;

        if (args.length < 1) {
            // No file argument - get data from stdin
            lines = new LinkedList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

        } else {
            // Get lines from file
            final Path path = Paths.get(args[0]);
            lines = Files.lines(path).collect(Collectors.toList());

        }

        // Generate report
        final String report = analyse(lines);

        // Print result
        System.out.println(report);
    }

    /**
     * Analyse the word lengths in the given lines of text.
     *
     * @param lines
     *
     * @return The report
     */
    public static String analyse(final Collection<String> lines) {

        // Get word lengths as a collection of entries, where entry keys are the length and values are the occurences
        final Collection<Entry<Integer, Integer>> wordLengths = getWordLengths(lines);

        // Total word length is the sum of each length mutiplied by the occurrences.
        final double totalWordLength = sum(multiplyEntries(wordLengths));

        // Get a collection of the occurrences
        final Collection<Integer> occurrences = entryValues(wordLengths);

        final int mostFrequentLength = max(occurrences);
        final int totalWordCount = sum(occurrences);

        // Calculate the average word length (the sum of all word lengths divided by the number of words counted)
        final double average = round(totalWordLength / totalWordCount, 3);

        // Get lengths that were top for occurences
        final Collection<Integer> mostFrequentLengths = keysByValue(wordLengths, mostFrequentLength);

        // Return formatted report
        return formatData(
                totalWordCount,
                average,
                wordLengths,
                mostFrequentLengths,
                mostFrequentLength
        );
    }

    public static int max(final Collection<Integer> collection) {
        // Calcualte the most top number of occurrences of any word length
        return collection.stream()
                .mapToInt(Integer::valueOf)
                .max().getAsInt();
    }

    public static int sum(final Collection<Integer> collection) {
        // Calcualte the total number of words counted
        return collection.stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    /**
     * Get the values from the entry collection.
     *
     * @param <K> The entry key type
     * @param <V> The entry value type
     *
     * @param lengths
     *
     * @return The values stream.
     */
    public static <K, V> Collection<V> entryValues(
            final Collection<Entry<K, V>> lengths
    ) {
        return lengths.stream()
                .map((final Entry<K, V> entry) -> {
                    return entry.getValue();
                })
                .collect(Collectors.toList());
    }

    /**
     * Multiply entry keys by their values.
     *
     * @param collection
     * @return The resulting int stream.
     */
    public static Collection<Integer> multiplyEntries(
            final Collection<Entry<Integer, Integer>> collection
    ) {
        return collection.stream()
                .map((final Entry<Integer, Integer> entry) -> {
                    return entry.getKey() * entry.getValue();
                })
                .collect(Collectors.toList());
    }

    /**
     * Extract the keys with the given value.
     *
     * @param collection
     * @param value
     *
     * @return The collection of keys with given value.
     */
    public static Collection<Integer> keysByValue(
            final Collection<Entry<Integer, Integer>> collection,
            final Integer value
    ) {
        return collection.stream()
                .filter((final Entry<Integer, Integer> length) -> {

                    return Integer.compare(length.getValue(), value) == 0;
                })
                .map((final Entry<Integer, Integer> length) -> {

                    return length.getKey();
                })
                .collect(Collectors.toList());
    }

    /**
     * Count the length of the words in the given lines of text.
     *
     * @param lines
     *
     * @return A collection of entries containing word lengths as keys and the occurrences as values.
     */
    public static Collection<Entry<Integer, Integer>> getWordLengths(
            final Collection<String> lines
    ) {
        // Defines a word a string containing alphanumeric characters, ampsersand or forward slashes.
        final Pattern pattern = Pattern.compile("[\\w\\d\\/&]+");

        final Map<Integer, Integer> lengthsMap = new HashMap<>();

        lines.stream().map(line -> pattern.matcher(line)).forEachOrdered(matcher -> {
            while (matcher.find()) {
                final String word = matcher.group(0);

                final int length = word.length();

                if (!lengthsMap.containsKey(length)) {
                    lengthsMap.put(word.length(), 0);
                }
                lengthsMap.put(word.length(), lengthsMap.get(length) + 1);
            }
        });

        return lengthsMap.entrySet()
                .stream()
                .sorted((Entry<Integer, Integer> t, Entry<Integer, Integer> t1) -> {
                    return t.getKey() - t1.getKey();
                })
                .collect(Collectors.toList());
    }

    /**
     * Round the given value to the given decimal places.
     *
     * @param value
     * @param places
     *
     * @return The rounded value
     */
    public static double round(double value, int places) {

        final int factor = (int) Math.pow(10, places);
        return Math.round(value * factor) / (double) factor;
    }

    /**
     * Format the given data into a human readable, printable text.
     *
     * @param totalWordCount
     * @param average
     * @param lengths
     * @param mostFrequentLengths
     * @param mostFrequentLength
     *
     * @return The formatted data string.
     */
    public static String formatData(
            final int totalWordCount,
            final double average,
            final Collection<Entry<Integer, Integer>> lengths,
            final Collection<Integer> mostFrequentLengths,
            final int mostFrequentLength
    ) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Word count = ").append((int) totalWordCount).append("\n");
        sb.append("Average word length = ").append(average).append("\n");

        lengths.forEach((final Entry<Integer, Integer> length) -> {
            sb.append("Number of words of length ").append(length.getKey()).append(" is ").append(length.getValue()).append("\n");
        });

        final String mostFrequentLengthsStr = mostFrequentLengths.stream().map((final Integer value) -> {
            return String.valueOf(value);
        }).collect(Collectors.joining(" & "));
        sb.append("The most frequently occurring word length is ").append((int) mostFrequentLength).append(", for word lengths of ").append(mostFrequentLengthsStr).append("\n");
        return sb.toString();
    }
}
