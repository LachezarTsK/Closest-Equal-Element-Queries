
package main

import "slices"

const VALUE_OCCURS_ONLY_ONCE = -1

func solveQueries(input []int, queries []int) []int {
    return createListForMinCircularDistancePerValue(input, queries)
}

func createListForMinCircularDistancePerValue(input []int, queries []int) []int {
    minCircularDistancePerValue := make([]int, len(queries))
    valueToIndexes := createMapValueToIndexes(input)

    for i, query := range queries {
        index := query
        value := input[index]
        indexInListOfIndexes, _ := slices.BinarySearch(valueToIndexes[value], index)

        minCircularDistance := getMinCircularDistance(valueToIndexes[value], indexInListOfIndexes, len(input))
        minCircularDistancePerValue[i] = minCircularDistance
    }
    return minCircularDistancePerValue
}

func createMapValueToIndexes(input []int) map[int][]int {
    valueToIndexes := map[int][]int{}
    for i := range input {
        if _, key := valueToIndexes[input[i]]; !key {
            valueToIndexes[input[i]] = []int{}
        }
        valueToIndexes[input[i]] = append(valueToIndexes[input[i]], i)
    }
    return valueToIndexes
}

func getCircularDistanceFromPreviousIndex(indexes []int, index int, sizeInput int) int {
    if index > 0 {
        return indexes[index] - indexes[index - 1]
    }
    return sizeInput - indexes[len(indexes) - 1] + indexes[index]
}

func getCircularDistanceFromFollowingIndex(indexes []int, index int, sizeInput int) int {
    if index+1 < len(indexes) {
        return indexes[index + 1] - indexes[index]
    }
    return sizeInput - indexes[index] + indexes[0]
}

func getMinCircularDistance(indexes []int, index int, sizeInput int) int {
    if len(indexes) == 1 {
        return VALUE_OCCURS_ONLY_ONCE
    }
    previousDistance := getCircularDistanceFromPreviousIndex(indexes, index, sizeInput)
    followingDistance := getCircularDistanceFromFollowingIndex(indexes, index, sizeInput)
    return min(previousDistance, followingDistance)
}
