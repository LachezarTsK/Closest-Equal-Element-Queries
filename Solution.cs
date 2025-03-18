
using System;
using System.Collections.Generic;

public class Solution
{
    private static readonly int VALUE_OCCURS_ONLY_ONCE = -1;

    public IList<int> SolveQueries(int[] input, int[] queries)
    {
        return createListForMinCircularDistancePerValue(input, queries);
    }

    private IList<int> createListForMinCircularDistancePerValue(int[] input, int[] queries)
    {
        IList<int> minCircularDistancePerValue = new List<int>();
        Dictionary<int, List<int>> valueToIndexes = createMapValueToIndexes(input);

        foreach (int query in queries)
        {
            int index = query;
            int value = input[index];
            int indexInListOfIndexes = valueToIndexes[value].BinarySearch(index);

            int minCircularDistance = getMinCircularDistance(valueToIndexes[value], indexInListOfIndexes, input.Length);
            minCircularDistancePerValue.Add(minCircularDistance);
        }
        return minCircularDistancePerValue;
    }

    private Dictionary<int, List<int>> createMapValueToIndexes(int[] input)
    {
        Dictionary<int, List<int>> valueToIndexes = new Dictionary<int, List<int>>();
        for (int i = 0; i < input.Length; ++i)
        {
            valueToIndexes.TryAdd(input[i], new List<int>());
            valueToIndexes[input[i]].Add(i);
        }
        return valueToIndexes;
    }

    private int getCircularDistanceFromPreviousIndex(IList<int> indexes, int index, int sizeInput)
    {
        if (index > 0)
        {
            return indexes[index] - indexes[index - 1];
        }
        return sizeInput - indexes[indexes.Count - 1] + indexes[index];
    }

    private int getCircularDistanceFromFollowingIndex(IList<int> indexes, int index, int sizeInput)
    {
        if (index + 1 < indexes.Count)
        {
            return indexes[index + 1] - indexes[index];
        }
        return sizeInput - indexes[index] + indexes[0];
    }

    private int getMinCircularDistance(IList<int> indexes, int index, int sizeInput)
    {
        if (indexes.Count == 1)
        {
            return VALUE_OCCURS_ONLY_ONCE;
        }
        int previousDistance = getCircularDistanceFromPreviousIndex(indexes, index, sizeInput);
        int followingDistance = getCircularDistanceFromFollowingIndex(indexes, index, sizeInput);
        return Math.Min(previousDistance, followingDistance);
    }
}
