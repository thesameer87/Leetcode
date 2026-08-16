# 1748. Sum of Unique Elements

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

 elements (elements that appear exactly once in the array).

To identify which elements appear exactly once, the submitted solution uses a frequency map (`HashMap<Integer, Integer>`). In the first pass over the array, the algorithm counts the occurrences of every element. In the second pass, it iterates through the entries in the hash map, identifying keys with a frequency equal to 1, and adds those keys to a running sum.

This approach leverages a **HashMap** frequency-counting pattern. By tracking element counts, the solution cleanly decouples element discovery from sum computation, ensuring that duplicates are filtered out regardless of how many times they appear in the original array.

### Why This Approach?

When asked to compute properties of unique elements, a naive solution might involve checking every element against the rest of the array using nested loops. For each element `nums[i]`, we would iterate over all other elements to see if `nums[i]` appears elsewhere. If it doesn't, we add it to the sum. However, this brute-force approach requires $O(N^2)$ time, which is inefficient.

To optimize, we realize that we don't need to re-scan the array for every element. Instead, we can record the count of each number in a single linear scan using a hash table. Once all counts are established, a single traversal of the distinct map keys lets us pick out elements with a frequency of exactly 1 in $O(1)$ lookup time per unique key.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We are given an integer array `nums`. We need to calculate the sum of all elements that appear **exactly once** in the array. Elements that appear two or more times must be completely ignored (not counted even once).

### Step 2: Identify the Key Observation

An element should contribute to the final sum if and only if its total frequency in `nums` is strictly equal to 1. If an element appears zero times (not in array) or two or more times, its contribution to the sum must be 0.

### Step 3: Recognize the Pattern

This problem fits the **Frequency Counting via Map** pattern. When we need to categorize or filter array elements based on how often they occur across the entire dataset, maintaining a mapping from `element -> count` allows us to gather global frequency statistics in linear time.

### Step 4: Decide What Information We Need to Maintain

To execute this strategy, we need:
1. `map` (a `HashMap<Integer, Integer>`): Stores each distinct element in `nums` as a key, and its total count as the value.
2. `sum` (an `int` variable): Maintains the cumulative sum of all elements whose count in `map` is equal to 1.

### Step 5: Derive the Algorithm

1. Populate the `map` by iterating through `nums` and incrementing the count for each encountered integer using `map.getOrDefault(val, 0) + 1`.
2. Initialize `sum = 0`.
3. Iterate over all entries in `map`. For each entry `(key, value)`:
   - Check if `value == 1`.
   - If true, add `key` to `sum`.
4. Return `sum`.

---

## 🔍 Algorithm

1. Initialize an empty `HashMap<Integer, Integer>` named `map`.
2. Iterate through each element in the input array `nums`:
   - Retrieve the current count of the element (default to 0 if not present).
   - Insert/update the map entry with the key as the element and value as `count + 1`.
3. Initialize an integer variable `sum` to 0.
4. Iterate through all key-value entries in `map.entrySet()`:
   - Check if `entry.getValue()` equals 1.
   - If it equals 1, add `entry.getKey()` to `sum`.
5. Return the accumulated `sum`.

### Important Implementation Details

- `map` → Hash table used to store the frequency count of each distinct number in `nums`.
- `map.getOrDefault(nums[i], 0) + 1` → Idiomatic Java method to safely retrieve the existing frequency or default to `0` before adding `1`.
- `entry.getValue() == 1` → Condition ensuring that only strictly unique elements are included in the total sum.
- `sum += entry.getKey()` → Accumulator step that adds the unique value itself (the key) to the output.

---

## 🧩 Understanding the Code

### Frequency Counting Phase (First Loop)

```java
HashMap<Integer,Integer> map = new HashMap<>();
for(int i = 0; i < nums.length; i++){
    map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
}
```

This section builds the frequency distribution of the input array. By iterating through `nums` from index `0` to `nums.length - 1`, every number is processed. Using `getOrDefault`, we avoid explicit `containsKey` checks and map each distinct number to its total occurrence count.

### Summation Phase (Second Loop)

```java
int sum = 0;
for(Map.Entry<Integer,Integer> entry : map.entrySet()) {
    if(entry.getValue() == 1){
        sum += entry.getKey();
    }
}
return sum;
```

This phase filters the counted entries. Iterating through `map.entrySet()` visits every unique integer present in the original input. The `if` statement isolates keys whose frequency is precisely `1`, summing them up into `sum` before returning the final result.

---

## 🧠 Why This Works

### Key Invariant

During the summation phase, `sum` represents the total of all distinct key values processed so far whose value in `map` is equal to 1. Because `map` stores accurate global frequencies computed in the first pass, checking `entry.getValue() == 1` guarantees that no element with a total occurrence count $> 1$ is ever added to `sum`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

1. **First Loop (Map Building):** Iterates through the input array of size $N$ exactly once. Hash map insertions and lookups take $O(1)$ time on average. Thus, this phase takes $O(N)$ time.
2. **Second Loop (Map Traversal):** Iterates over the entries in `map`. The number of unique keys $U$ in `map` is bounded by $N$ ($U \le N$). Inspecting each entry takes $O(1)$ time. Thus, this phase takes $O(U)$ time.

Combining both phases gives $O(N + U) = O(N)$ time complexity.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(N)`

### Why?

The `HashMap` stores each unique number present in `nums`. In the worst-case scenario where all elements in `nums` are distinct, the map will contain $N$ key-value pairs, taking $O(N)$ auxiliary space.

---

## 🔄 Alternative Approach

### Alternative Idea

Given the problem constraints on LeetCode where $1 \le nums[i] \le 100$, we can use a fixed-size Direct Address Table (Frequency Array) instead of a `HashMap`.

By initializing an integer array `freq` of size `101`, we can count frequencies in $O(1)$ space overhead (since array size is fixed) and avoid the overhead of `HashMap` object creation and boxing/unboxing `Integer` objects.

Alternatively, we can compute the sum in a **single pass** using a frequency array:
- When a number appears for the first time (`freq[x] == 0`), add it to `sum` and set `freq[x] = 1`.
- When a number appears for the second time (`freq[x] == 1`), subtract it from `sum` (reversing its initial addition) and set `freq[x] = 2`.
- When a number appears three or more times (`freq[x] > 1`), ignore it.

### Complexity

**Time:** `O(N)`  
**Space:** `O(1)` (since the frequency array size is constant, fixed at 101)

### Comparison

| Aspect | Submitted Approach (HashMap) | Alternative Approach (Fixed Frequency Array) |
|---|---|---|
| Main Idea | Count occurrences in `HashMap`, sum keys with count 1 | Count occurrences in array of size 101 |
| Time | `O(N)` | `O(N)` |
| Space | `O(N)` | `O(1)` (Bounded by max value 100) |
| Advantage | Works for any range of integer values | Faster due to lower memory overhead and no object instantiation |

---

## 📌 Key Takeaways

- **Pattern:** HashMap / Frequency Array Counting
- **Core Observation:** Pre-computing element frequencies allows us to easily filter and sum elements with a total count of 1.
- **Important Data Structure:** `HashMap<Integer, Integer>` (or a frequency array `int[101]` for fixed small ranges).
- **Time:** `O(N)`
- **Space:** `O(N)`

### Remember

> When a problem requires operating on elements based on their total occurrences across an array, use a hash map or frequency array to separate counting from processing.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/sum-of-unique-elements/)
