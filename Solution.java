
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private static final int VALUE_OCCURS_ONLY_ONCE = -1;

    public List<Integer> solveQueries(int[] input, int[] queries) {
        return createListForMinCircularDistancePerValue(input, queries);
    }

    private List<Integer> createListForMinCircularDistancePerValue(int[] input, int[] queries) {
        List<Integer> minCircularDistancePerValue = new ArrayList<>();
        Map<Integer, List<Integer>> valueToIndexes = createMapValueToIndexes(input);

        for (int query : queries) {
            int index = query;
            int value = input[index];
            int indexInListOfIndexes = Collections.binarySearch(valueToIndexes.get(value), index);

            int minCircularDistance = getMinCircularDistance(valueToIndexes.get(value), indexInListOfIndexes, input.length);
            minCircularDistancePerValue.add(minCircularDistance);
        }
        return minCircularDistancePerValue;
    }

    private Map<Integer, List<Integer>> createMapValueToIndexes(int[] input) {
        Map<Integer, List<Integer>> valueToIndexes = new HashMap<>();
        for (int i = 0; i < input.length; ++i) {
            valueToIndexes.computeIfAbsent(input[i], indexes -> new ArrayList<>()).add(i);
        }
        return valueToIndexes;
    }

    private int getCircularDistanceFromPreviousIndex(List<Integer> indexes, int index, int sizeInput) {
        if (index > 0) {
            return indexes.get(index) - indexes.get(index - 1);
        }
        return sizeInput - indexes.get(indexes.size() - 1) + indexes.get(index);
    }

    private int getCircularDistanceFromFollowingIndex(List<Integer> indexes, int index, int sizeInput) {
        if (index + 1 < indexes.size()) {
            return indexes.get(index + 1) - indexes.get(index);
        }
        return sizeInput - indexes.get(index) + indexes.get(0);
    }

    private int getMinCircularDistance(List<Integer> indexes, int index, int sizeInput) {
        if (indexes.size() == 1) {
            return VALUE_OCCURS_ONLY_ONCE;
        }
        int previousDistance = getCircularDistanceFromPreviousIndex(indexes, index, sizeInput);
        int followingDistance = getCircularDistanceFromFollowingIndex(indexes, index, sizeInput);
        return Math.min(previousDistance, followingDistance);
    }
}
