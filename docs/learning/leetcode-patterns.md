# LeetCode Problems by Pattern

Organized problem list by pattern/strategy. Start with Easy, progress to Medium, then Hard.

---

## Pattern 1: Hash Map - Complement Search

**When:** "Find pair/two elements", "sum equals target", "difference equals k"

**Strategy:** Store what you've seen, check for complement

### Easy
- [ ] [1. Two Sum](https://leetcode.com/problems/two-sum/) ⭐ START HERE
- [ ] [217. Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)
- [ ] [1512. Number of Good Pairs](https://leetcode.com/problems/number-of-good-pairs/)

### Medium
- [ ] [167. Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
- [ ] [15. 3Sum](https://leetcode.com/problems/3sum/)
- [ ] [454. 4Sum II](https://leetcode.com/problems/4sum-ii/)
- [ ] [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)

### Hard
- [ ] [18. 4Sum](https://leetcode.com/problems/4sum/)

**Template:**
```kotlin
val map = mutableMapOf<Int, Int>()
for ((index, value) in array.withIndex()) {
    val complement = target - value
    if (map.containsKey(complement)) {
        return intArrayOf(map[complement]!!, index)
    }
    map[value] = index
}
```

---

## Pattern 2: Two Pointers (Sorted Array)

**When:** "Sorted array", "find pair in sorted", "remove duplicates"

**Strategy:** Start/end pointers moving toward each other

### Easy
- [ ] [125. Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)
- [ ] [344. Reverse String](https://leetcode.com/problems/reverse-string/)
- [ ] [977. Squares of a Sorted Array](https://leetcode.com/problems/squares-of-a-sorted-array/)

### Medium
- [ ] [167. Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) ⭐
- [ ] [15. 3Sum](https://leetcode.com/problems/3sum/)
- [ ] [11. Container With Most Water](https://leetcode.com/problems/container-with-most-water/) ⭐
- [ ] [16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/)
- [ ] [75. Sort Colors](https://leetcode.com/problems/sort-colors/)

### Hard
- [ ] [42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)

**Template:**
```kotlin
var left = 0
var right = array.size - 1

while (left < right) {
    val sum = array[left] + array[right]
    when {
        sum == target -> return intArrayOf(left, right)
        sum < target -> left++
        else -> right--
    }
}
```

---

## Pattern 3: Sliding Window

**When:** "Subarray", "substring", "consecutive elements", "window of size k"

**Strategy:** Expand/shrink window to maintain condition

### Easy
- [ ] [643. Maximum Average Subarray I](https://leetcode.com/problems/maximum-average-subarray-i/)
- [ ] [1343. Number of Sub-arrays of Size K](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/)

### Medium
- [ ] [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) ⭐ START HERE
- [ ] [424. Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)
- [ ] [567. Permutation in String](https://leetcode.com/problems/permutation-in-string/)
- [ ] [438. Find All Anagrams in a String](https://leetcode.com/problems/find-all-anagrams-in-a-string/)
- [ ] [209. Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)
- [ ] [1004. Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/)

### Hard
- [ ] [76. Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)
- [ ] [239. Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)

**Template:**
```kotlin
var left = 0
var maxLength = 0

for (right in array.indices) {
    // Expand window
    add(array[right])

    // Shrink window if invalid
    while (windowInvalid()) {
        remove(array[left])
        left++
    }

    maxLength = max(maxLength, right - left + 1)
}
```

---

## Pattern 4: Hash Map - Frequency Counting

**When:** "Count frequency", "how many times", "most/least frequent", "k most common"

**Strategy:** Count occurrences in hash map

### Easy
- [ ] [242. Valid Anagram](https://leetcode.com/problems/valid-anagram/) ⭐
- [ ] [383. Ransom Note](https://leetcode.com/problems/ransom-note/)
- [ ] [387. First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/)
- [ ] [1002. Find Common Characters](https://leetcode.com/problems/find-common-characters/)

### Medium
- [ ] [49. Group Anagrams](https://leetcode.com/problems/group-anagrams/) ⭐
- [ ] [347. Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)
- [ ] [451. Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/)
- [ ] [1122. Relative Sort Array](https://leetcode.com/problems/relative-sort-array/)

### Hard
- [ ] [895. Maximum Frequency Stack](https://leetcode.com/problems/maximum-frequency-stack/)

**Template:**
```kotlin
val freq = mutableMapOf<Char, Int>()
for (char in string) {
    freq[char] = freq.getOrDefault(char, 0) + 1
}
// Use freq to solve problem
```

---

## Pattern 5: Hash Set - Existence Check

**When:** "Have I seen this?", "contains duplicate", "unique elements", "cycle detection"

**Strategy:** Track what you've seen

### Easy
- [ ] [217. Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) ⭐
- [ ] [219. Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)
- [ ] [268. Missing Number](https://leetcode.com/problems/missing-number/)
- [ ] [136. Single Number](https://leetcode.com/problems/single-number/)

### Medium
- [ ] [128. Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/) ⭐
- [ ] [287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)
- [ ] [442. Find All Duplicates in an Array](https://leetcode.com/problems/find-all-duplicates-in-an-array/)

### Hard
- [ ] [41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/)

**Template:**
```kotlin
val seen = mutableSetOf<Int>()
for (num in array) {
    if (seen.contains(num)) {
        return true  // Found duplicate
    }
    seen.add(num)
}
```

---

## Pattern 6: Hash Map - Index Tracking

**When:** "Find first/last occurrence", "indices of", "closest/farthest"

**Strategy:** Store element → index mapping

### Easy
- [ ] [1. Two Sum](https://leetcode.com/problems/two-sum/)
- [ ] [387. First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/)

### Medium
- [ ] [299. Bulls and Cows](https://leetcode.com/problems/bulls-and-cows/)
- [ ] [348. Design Tic-Tac-Toe](https://leetcode.com/problems/design-tic-tac-toe/)

**Template:**
```kotlin
val indexMap = mutableMapOf<Int, Int>()
for ((index, value) in array.withIndex()) {
    indexMap[value] = index  // or list of indices
}
```

---

## Pattern 7: Heap / Priority Queue

**When:** "K largest/smallest", "top K", "median", "merge K sorted"

**Strategy:** Use min-heap or max-heap to maintain top K

### Easy
- [ ] [703. Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/)
- [ ] [1046. Last Stone Weight](https://leetcode.com/problems/last-stone-weight/)

### Medium
- [ ] [215. Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) ⭐
- [ ] [347. Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) ⭐
- [ ] [973. K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)
- [ ] [621. Task Scheduler](https://leetcode.com/problems/task-scheduler/)
- [ ] [767. Reorganize String](https://leetcode.com/problems/reorganize-string/)

### Hard
- [ ] [23. Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)
- [ ] [295. Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)
- [ ] [502. IPO](https://leetcode.com/problems/ipo/)

**Template (Min-Heap):**
```kotlin
val heap = PriorityQueue<Int>()  // Min-heap by default
for (num in array) {
    heap.offer(num)
    if (heap.size > k) {
        heap.poll()  // Remove smallest
    }
}
// heap contains k largest elements
```

---

## Pattern 8: Prefix Sum

**When:** "Range sum queries", "subarray sum", "cumulative sum"

**Strategy:** Precompute cumulative sums for O(1) range queries

### Easy
- [ ] [303. Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)
- [ ] [724. Find Pivot Index](https://leetcode.com/problems/find-pivot-index/)
- [ ] [1480. Running Sum of 1d Array](https://leetcode.com/problems/running-sum-of-1d-array/)

### Medium
- [ ] [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) ⭐
- [ ] [523. Continuous Subarray Sum](https://leetcode.com/problems/continuous-subarray-sum/)
- [ ] [974. Subarray Sums Divisible by K](https://leetcode.com/problems/subarray-sums-divisible-by-k/)
- [ ] [304. Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/)

### Hard
- [ ] [363. Max Sum of Rectangle No Larger Than K](https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/)

**Template:**
```kotlin
val prefixSum = IntArray(array.size + 1)
for (i in array.indices) {
    prefixSum[i + 1] = prefixSum[i] + array[i]
}
// Range sum [left, right] = prefixSum[right + 1] - prefixSum[left]
```

---

## Bonus Patterns

### Fast & Slow Pointers (Linked Lists)

**When:** "Cycle detection", "find middle", "linked list"

### Easy
- [ ] [141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)
- [ ] [876. Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/)

### Medium
- [ ] [142. Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/)
- [ ] [287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)

**Template:**
```kotlin
var slow = head
var fast = head
while (fast?.next != null) {
    slow = slow?.next
    fast = fast.next?.next
    if (slow == fast) {
        return true  // Cycle found
    }
}
```

---

### Binary Search

**When:** "Sorted array", "find target", "search in rotated"

### Easy
- [ ] [704. Binary Search](https://leetcode.com/problems/binary-search/)
- [ ] [35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)

### Medium
- [ ] [33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- [ ] [34. Find First and Last Position of Element](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
- [ ] [153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

**Template:**
```kotlin
var left = 0
var right = array.size - 1

while (left <= right) {
    val mid = left + (right - left) / 2
    when {
        array[mid] == target -> return mid
        array[mid] < target -> left = mid + 1
        else -> right = mid - 1
    }
}
```

---

## Practice Strategy

### Week 1-2 (Month 1): Easy Foundation
1. Start with **Hash Map - Complement Search**: Two Sum, Contains Duplicate
2. Then **Two Pointers**: Valid Palindrome
3. Then **Frequency Counting**: Valid Anagram

**Goal:** 7 easy problems (Arrays & Strings from Week 1)

### Week 3-4 (Month 1): Medium Introduction
4. **Sliding Window**: Longest Substring Without Repeating Characters
5. **Hash Set**: Longest Consecutive Sequence
6. **Binary Search**: Search in Rotated Sorted Array

**Goal:** 7 easy-medium mix (HashMaps, Two Pointers from Week 2)

### Month 2-3: Pattern Mastery
- Focus on one pattern per week
- Solve 5-7 problems of that pattern
- Easy → Medium → Hard progression

### Month 4-6: Mixed Practice
- Daily random problems
- Company-tagged problems (Google, Meta, Amazon)
- Timed practice (20-30 min per medium)

---

## Quick Pattern Recognition Guide

**See "pair" or "two elements"?** → Hash Map (Complement)
**See "sorted"?** → Two Pointers or Binary Search
**See "subarray" or "substring"?** → Sliding Window
**See "frequency" or "count"?** → Hash Map (Counting)
**See "duplicate" or "unique"?** → Hash Set
**See "K largest/smallest"?** → Heap
**See "range sum"?** → Prefix Sum

---

**Track your progress by checking off problems as you solve them!**
