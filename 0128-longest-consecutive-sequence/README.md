# 128. Longest Consecutive Sequence

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

 layoutThe submitted solution finds the length of the longest consecutive sequence in an unsorted array of integers using a `HashSet`. 

The key insight behind this solution is that a number `num` represents the *start* of a consecutive sequence if and only if `num - 1` is absent from the input set. By identifying sequence heads first, the algorithm avoids redundant scans and only expands sequences forward from their true beginning.

This approach utilizes a `HashSet` to achieve $O(1)$ average time lookups. Although there is a nested `while` loop, the overall algorithm achieves linear $O(n)$ time complexity because each number in the input is processed a constant number of times. The primary DSA pattern used here is **HashSet / Hash Table**.

### Why This Approach?

When faced with finding consecutive elements, the most intuitive approach is to sort the input array. Once sorted, consecutive numbers sit adjacent to each other, making the continuous sequence length easy to measure. However, sorting takes $O(n \log n)$ time, whereas the problem explicitly demands an $O(n)$ solution.

To achieve $O(n)$ time, we need a way to instantly check whether adjacent numbers (`num - 1` or `num + 1`) exist in the dataset without ordering the array. A `HashSet` provides average $O(1)$ lookup time for this exact purpose.

However, simply checking forward sequences starting at *every* element would lead to duplicate work—for example, measuring `1 -> 2 -> 3 -> 4` starting at `1`, then measuring `2 -> 3 -> 4` starting at `2`, resulting in an $O(n^2)$ time complexity. By adding the condition `!set.contains(num - 1)`, we skip any element that isn't the beginning of a sequence, guaranteeing that each consecutive chain is traversed exactly once.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to find the length of the longest sequence of consecutive integers in an unsorted array `nums`. The consecutive elements do not need to appear contiguously in the original array, and duplicate values do not increase sequence length.

### Step 2: Identify the Key Observation

Any consecutive sequence of numbers (e.g., `[1, 2, 3, 4]`) has a unique starting element $S$ (in this case, `1`). An element $S$ is the starting element of a sequence if and only if $S - 1$ is NOT present in the input array. 

If we only launch a full sequence length counter when we encounter a starting element $S$, every sequence is built from scratch exactly once.

### Step 3: Recognize the Pattern

- **HashSet Pattern:** When an $O(n)$ runtime is required and sequence lookups (`x + 1`, `x - 1`) are needed, inserting elements into a `HashSet` provides average $O(1)$ queries without spending time to sort the input.

### Step 4: Decide What Information We Need to Maintain

- `set`: A `HashSet<Integer>` containing all unique elements from `nums` to enable $O(1)$ lookups.
- `max`: An integer maintaining the maximum sequence length found so far.
- `current`: A tracking variable for the current element while stepping through a sequence.
- `length`: A counter tracking the current length of the sequence being measured.

### Step 5: Derive the Algorithm

1. Insert all elements of `nums` into a `HashSet` to remove duplicate entries and allow $O(1)$ lookup queries.
2. Iterate through each unique number `num` in the set.
3. Check if `num - 1` exists in the set. If it does exist, skip `num` because it is not the sequence head.
4. If `num - 1` does NOT exist, `num` is a sequence head. Set `current = num` and `length = 1`.
5. Continuously check if `current + 1` exists in the set. If it does, increment both `current` and `length`.
6. Update `max` with `Math.max(max, length)`.
7. Return `max`.

---

## 🔍 Algorithm

1. Create a `HashSet<Integer>` and populate it with all numbers from the `nums` array.
2. Initialize `max = 0` to track the maximum sequence length.
3. Iterate over each unique element `num` in `set`:
   a. Check if `set.contains(num - 1)` is `false`.
   b. If `false`, `num` is the start of a consecutive sequence:
      i. Initialize `current = num` and `length = 1`.
      ii. While `set.contains(current + 1)` is `true`, increment `current` by `1` and `length` by `1`.
      iii. Update `max` with `Math.max(max, length)`.
4. Return `max` as the final result.

### Important Implementation Details

- `set` → Stores all unique values of `nums` to provide $O(1)$ average-time checks.
- `max` → Keeps track of the maximum length among all valid consecutive sequences.
- `!set.contains(num - 1)` → The guard condition ensuring sequence scanning starts only at sequence heads.
- `set.contains(current + 1)` → The expansion condition that steps forward through adjacent integers.

---

## 🧩 Understanding the Code

### Phase 1: Populating the HashSet

```java
HashSet<Integer> set = new HashSet<>();

int max = 0;
for(int num: nums){
    set.add(num);
}
```

This section transfers all array values into a `HashSet`. This operation filters out duplicate entries (which do not affect sequence length) and prepares the dataset for $O(1)$ existence queries.

### Phase 2: Identifying Sequence Heads

```java
for(int num :set){
    if(!set.contains(num-1)){
        ...
    }
}
```

Iterating directly over `set` avoids checking duplicate numbers. The `if(!set.contains(num - 1))` check filters out numbers that are middle or end elements of a sequence, ensuring that the inner counting loop only executes when `num` is a true sequence start.

### Phase 3: Measuring Sequence Length and Updating Max

```java
int current = num;
int length = 1;

while(set.contains(current+1)){
    current++;
    length++;
}
max= Math.max(max,length);
```

When a sequence start is confirmed, the code initializes a search for contiguous elements. The `while` loop steps forward through consecutive integers as long as `current + 1` exists in `set`. Once the end of the sequence is reached, `max` is updated with the candidate length.

---

## 🧠 Why This Works

The algorithm guarantees correctness because:
1. Every element in `nums` belongs to some consecutive sequence of length $\ge 1$.
2. Every consecutive sequence has a unique minimum element $S$ (where $S - 1$ is absent from `nums`).
3. The guard condition `!set.contains(num - 1)` triggers counting if and only if `num` is $S$.
4. Starting at $S$, the `while` loop exhaustively traverses every element $S, S+1, S+2, \dots, S+k-1$ until the chain breaks, accurately calculating the sequence length $k$.
5. Thus, every valid consecutive sequence is evaluated once at its start, and the maximum length overall is recorded.

### Key Invariant

- **Sequence Head Invariant:** A consecutive sequence starting at $S$ of length $k$ is counted if and only if the outer loop visits $S$ (i.e., $S - 1 \notin \text{set}$). No element in any sequence is traversed more than once during the `while` loops across the entire execution.

---

## ⏱️ Time Complexity

**Time:** `O(n)`

### Why?

- Populating the `HashSet` takes $O(n)$ time on average, as inserting $n$ elements into a hash set takes $O(1)$ per insertion.
- The outer loop iterates over $u$ unique elements in the set, where $u \le n$.
- Although there is a nested `while` loop, it only executes when `num` is the first element of a sequence.
- Across all iterations of the outer loop, each unique number in the dataset is visited at most once by the inner `while` loop.
- Therefore, the inner `while` loop performs at most $n$ total steps across all outer iterations combined, giving an amortized time complexity of $O(n)$ overall.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(n)`

- A `HashSet` is constructed to store elements from `nums`.
- In the worst-case scenario where all elements in `nums` are unique, the set stores $n$ integers, requiring $O(n)$ auxiliary memory.

---

## 🔄 Alternative Approach

### Alternative Idea

An alternative approach is to **sort** the input array first. After sorting, consecutive integers sit adjacent to each other. We can iterate through the sorted array, keeping a running length counter that increments when `nums[i] == nums[i-1] + 1`, skips duplicate values when `nums[i] == nums[i-1]`, and resets to `1` whenever a sequence gap occurs (`nums[i] > nums[i-1] + 1`).

While sorting increases the time complexity to $O(n \log n)$, it can reduce auxiliary space complexity to $O(1)$ or $O(\log n)$ depending on the space used by the sorting algorithm.

### Complexity

**Time:** `O(n \log n)`  
**Space:** `O(1)` (or `O(\log n)` / `O(n)` depending on primitive array sorting implementation)

### Comparison

| Aspect | Submitted Approach (HashSet) | Alternative (Sorting) |
|---|---|---|
| Main Idea | Store in HashSet, measure sequence length only from sequence heads | Sort array first, scan sequentially to count consecutive elements |
| Time | `O(n)` | `O(n \log n)` |
| Space | `O(n)` | `O(1)` or `O(\log n)` |
| Advantage | Optimal linear time complexity | Avoids explicit hash map allocation |

---

## 📌 Key Takeaways

- **Pattern:** HashSet / Array
- **Core Observation:** A consecutive sequence can be completely measured in $O(n)$ time by starting expansion strictly from sequence head elements (`num - 1` is absent).
- **Important Data Structure:** `HashSet<Integer>`
- **Time:** `O(n)`
- **Space:** `O(n)`

### Remember

> To achieve $O(n)$ time on sequence lookups without sorting, use a HashSet for $O(1)$ queries and only trigger chain traversal from sequence starting elements (`num - 1` absent).

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/longest-consecutive-sequence/)
