
/**
 * @param {number[]} input
 * @param {number[]} queries
 * @return {number[]}
 */
var solveQueries = function (input, queries) {
    this.VALUE_OCCURS_ONLY_ONCE = -1;
    return createListForMinCircularDistancePerValue(input, queries);
};

/**
 * @param {number[]} input
 * @param {number[]} queries
 * @return {number[]}
 */
function createListForMinCircularDistancePerValue(input, queries) {
    const minCircularDistancePerValue = new Array();
    const valueToIndexes = createMapValueToIndexes(input);

    for (let query of queries) {
        const index = query;
        const value = input[index];
        const indexInListOfIndexes = binarySearch(valueToIndexes.get(value), index);

        const minCircularDistance = getMinCircularDistance(valueToIndexes.get(value), indexInListOfIndexes, input.length);
        minCircularDistancePerValue.push(minCircularDistance);
    }
    return minCircularDistancePerValue;
}

/**
 * @param {number[]} input
 * @return {Map<number, number[]>}
 */
function createMapValueToIndexes(input) {
    const valueToIndexes = new Map();
    for (let i = 0; i < input.length; ++i) {
        if (!valueToIndexes.has(input[i])) {
            valueToIndexes.set(input[i], new Array());
        }
        valueToIndexes.get(input[i]).push(i);
    }
    return valueToIndexes;
}

/**
 * @param {number[]} indexes
 * @param {number} index
 * @param {number} sizeInput
 * @return {number}
 */
function getCircularDistanceFromPreviousIndex(indexes, index, sizeInput) {
    if (index > 0) {
        return indexes[index] - indexes[index - 1];
    }
    return sizeInput - indexes[indexes.length - 1] + indexes[index];
}

/**
 * @param {number[]} indexes
 * @param {number} index
 * @param {number} sizeInput
 * @return {number}
 */
function getCircularDistanceFromFollowingIndex(indexes, index, sizeInput) {
    if (index + 1 < indexes.length) {
        return indexes[index + 1] - indexes[index];
    }
    return sizeInput - indexes[index] + indexes[0];
}

/**
 * @param {number[]} indexes
 * @param {number} index
 * @param {number} sizeInput
 * @return {number}
 */
function getMinCircularDistance(indexes, index, sizeInput) {
    if (indexes.length === 1) {
        return this.VALUE_OCCURS_ONLY_ONCE;
    }
    const previousDistance = getCircularDistanceFromPreviousIndex(indexes, index, sizeInput);
    const followingDistance = getCircularDistanceFromFollowingIndex(indexes, index, sizeInput);
    return Math.min(previousDistance, followingDistance);
}

/**
 * @param {number[]} indexes
 * @param {number} index
 * @return {number}
 */
function binarySearch(indexes, index) {
    let left = 0;
    let right = indexes.length - 1;
    const NOT_FOUND = -1;

    while (left <= right) {
        let mid = left + Math.floor((right - left) / 2);
        if (indexes[mid] === index) {
            return mid;
        }
        if (indexes[mid] < index) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return NOT_FOUND;
}
