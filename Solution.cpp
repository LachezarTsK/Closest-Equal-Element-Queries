
#include <span>
#include <vector>
#include <iterator>
#include <algorithm>
#include <unordered_map>
using namespace std;


class Solution {

    static const int VALUE_OCCURS_ONLY_ONCE = -1;

public:
    vector<int> solveQueries(const vector<int>& input, const vector<int>& queries) const {
        return createListForMinCircularDistancePerValue(input, queries);
    }

private:
    vector<int>
        createListForMinCircularDistancePerValue(span<const int> input, span<const int> queries) const {
        vector<int> minCircularDistancePerValue;
        unordered_map<int, vector<int>> valueToIndexes = createMapValueToIndexes(input);

        for (int query : queries) {
            int index = query;
            int value = input[index];
            int indexInListOfIndexes = distance(valueToIndexes[value].begin(), ranges::lower_bound(valueToIndexes[value], index));

            int minCircularDistance = getMinCircularDistance(valueToIndexes[value], indexInListOfIndexes, input.size());
            minCircularDistancePerValue.push_back(minCircularDistance);
        }
        return minCircularDistancePerValue;
    }

    unordered_map<int, vector<int>> createMapValueToIndexes(span<const int> input) const {
        unordered_map<int, vector<int>> valueToIndexes;
        for (int i = 0; i < input.size(); ++i) {
            valueToIndexes[input[i]].push_back(i);
        }
        return valueToIndexes;
    }

    int getCircularDistanceFromPreviousIndex(span<const int> indexes, int index, int sizeInput) const {
        if (index > 0) {
            return indexes[index] - indexes[index - 1];
        }
        return sizeInput - indexes[indexes.size() - 1] + indexes[index];
    }

    int getCircularDistanceFromFollowingIndex(span<const int> indexes, int index, int sizeInput) const {
        if (index + 1 < indexes.size()) {
            return indexes[index + 1] - indexes[index];
        }
        return sizeInput - indexes[index] + indexes[0];
    }

    int getMinCircularDistance(span<const int> indexes, int index, int sizeInput) const {
        if (indexes.size() == 1) {
            return VALUE_OCCURS_ONLY_ONCE;
        }
        int previousDistance = getCircularDistanceFromPreviousIndex(indexes, index, sizeInput);
        int followingDistance = getCircularDistanceFromFollowingIndex(indexes, index, sizeInput);
        return min(previousDistance, followingDistance);
    }
};
