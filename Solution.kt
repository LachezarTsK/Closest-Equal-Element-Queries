
import kotlin.math.min

class Solution {

    private companion object {
        const val VALUE_OCCURS_ONLY_ONCE = -1
    }

    fun solveQueries(input: IntArray, queries: IntArray): List<Int> {
        return createListForMinCircularDistancePerValue(input, queries)
    }

    private fun createListForMinCircularDistancePerValue(input: IntArray, queries: IntArray): ArrayList<Int> {
        val minCircularDistancePerValue = ArrayList<Int>()
        val valueToIndexes = createMapValueToIndexes(input)

        for (query in queries) {
            val index = query
            val value = input[index]
            val indexInListOfIndexes = valueToIndexes[value]!!.binarySearch(index)

            val minCircularDistance = getMinCircularDistance(valueToIndexes[value]!!, indexInListOfIndexes, input.size)
            minCircularDistancePerValue.add(minCircularDistance)
        }
        return minCircularDistancePerValue
    }

    private fun createMapValueToIndexes(input: IntArray): HashMap<Int, ArrayList<Int>> {
        val valueToIndexes = HashMap<Int, ArrayList<Int>>()
        for (i in input.indices) {
            valueToIndexes.computeIfAbsent(input[i]) { ArrayList<Int>() }.add(i)
        }
        return valueToIndexes
    }

    private fun getCircularDistanceFromPreviousIndex(indexes: ArrayList<Int>, index: Int, sizeInput: Int): Int {
        if (index > 0) {
            return indexes[index] - indexes[index - 1]
        }
        return sizeInput - indexes[indexes.size - 1] +
                indexes[index]
    }

    private fun getCircularDistanceFromFollowingIndex(indexes: ArrayList<Int>, index: Int, sizeInput: Int): Int {
        if (index + 1 < indexes.size) {
            return indexes[index + 1] - indexes[index]
        }
        return sizeInput - indexes[index] + indexes[0]
    }

    private fun getMinCircularDistance(indexes: ArrayList<Int>, index: Int, sizeInput: Int): Int {
        if (indexes.size == 1) {
            return VALUE_OCCURS_ONLY_ONCE
        }
        val previousDistance = getCircularDistanceFromPreviousIndex(indexes, index, sizeInput)
        val followingDistance = getCircularDistanceFromFollowingIndex(indexes, index, sizeInput)
        return min(previousDistance, followingDistance)
    }
}
