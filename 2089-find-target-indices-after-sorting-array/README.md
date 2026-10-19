# 2089. Find Target Indices After Sorting Array

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution solves the problem by directly following the instructions given in the problem statement. First, it sorts the input array `nums` in non-decreasing order using Java's built-in `Arrays.sort()` method.

Once the array is sorted, all elements equal to `target` become contiguous. The algorithm then performs a single linear scan across the sorted array. Whenever `nums[i]` equals `target`, the loop index `i` is added to the result list `ans`.

This approach uses the **Sorting** pattern followed by an **Array Traversal**. Sorting places the elements in the required order, and traversing guarantees that the target indices are collected in strictly increasing order.

### Why This Approach?

To solve this problem, we need to know where `target` ends up after sorting `nums`. The most direct mental model is:
1. Reorder the array into sorted order.
2. Find every position where `target` appears.

By physically sorting the array first, we eliminate any ambiguity regarding element positions. Scanning from index `0` to `nums.length - 1` guarantees that matched indices are naturally appended in ascending order as required by the problem.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to return a list of all 0-based indices where `nums[i] == target` AFTER the array `nums` has been sorted in non-decreasing order.

### Step 2: Identify the Key Observation

Sorting an array groups identical elements together in contiguous blocks. Once `nums` is sorted, all occurrences of `target` will be situated next to each other.

### Step 3: Recognize the Pattern

This solution utilizes **Sorting** to establish order, followed by a **Linear Scan** to locate and collect valid indices.

### Step 4: Decide What Information We Need to Maintain

- The array `nums`, modified in-place to be sorted.
- An output list `ans` to store the target indices.
- A loop variable `i` tracking the current array index during iteration.

### Step 5: Derive the Algorithm

1. Sort `nums` in non-decreasing order.
2. Initialize an empty list `ans`.
3. Loop through every index `i` in `nums`.
4. If `nums[i] == target`, append `i` to `ans`.
5. Return `ans`.

---

## 🔍 Algorithm

1. Call `Arrays.sort(nums)` to sort the array `nums` in non-decreasing order.
2. Initialize a dynamic array `ans` (`ArrayList<Integer>`) to store the target indices.
3. Iterate over the sorted `nums` array using a `for` loop from `i = 0` up to `nums.length - 1`.
4. During each iteration, check if `nums[i]` is equal to `target`.
5. If `nums[i] == target`, add `i` to `ans`.
6. Return `ans` after the loop completes.

### Important Implementation Details

- `nums` → input array of integers, sorted in-place in non-decreasing order
- `ans` → dynamic list (`ArrayList`) used to collect all valid target indices
- `nums[i] == target` → condition ensuring only indices matching the target value are added

---

## 🧩 Understanding the Code

### Array Sorting

```java
Arrays.sort(nums);
```

This line sorts the array `nums` in ascending order in-place. After this execution, identical numbers are grouped contiguously.

### Index Collection Loop

```java
List<Integer> ans = new ArrayList<>();
for(int i = 0; i<nums.length;i++){
    if(nums[i] == target){
        ans.add(i);
    }
}
```

This block creates the response list `ans` and iterates through `nums` from left to right. Whenever `nums[i]` matches `target`, the current index `i` is appended to `ans`. Since `i` increases monotonically, the resulting indices in `ans` are guaranteed to be sorted.

---

## 🧠 Why This Works

Sorting guarantees that elements are ordered such that `nums[0] <= nums[1] <= ... <= nums[n-1]`. Any occurrence of `target` will form a contiguous range in this sorted sequence.

By scanning the sorted array sequentially from index `0` to `n - 1`, the loop visits every element exactly once. Checking `nums[i] == target` guarantees all matching positions are captured without missing any or introducing invalid indices.

### Key Invariant

During the loop iteration `i`, `ans` contains all 0-based indices less than or equal to `i` where `nums[k] == target` in the sorted array, in strictly increasing order.

---

## ⏱️ Time Complexity

**Time:** `O(N log N)`

### Why?

- Sorting the array using `Arrays.sort(nums)` (which uses Dual-Pivot Quicksort for primitives in Java) takes $O(N \log N)$ time on average and worst-case, where $N$ is the number of elements in `nums`.
- The `for` loop iterates through the array of length $N$ exactly once, taking $O(N)$ time.
- Total Time Complexity: $O(N \log N) + O(N) = O(N \log N)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(log N)`

- Dual-Pivot Quicksort in Java requires $O(\log N)$ auxiliary space for the recursion call stack.
- The output list `ans` stores at most $N$ elements, taking $O(N)$ space (if output space is counted), but purely auxiliary algorithm space is $O(\log N)$.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of actually sorting the array, we can use a **Counting Approach** in $O(N)$ time:
1. Count how many numbers in `nums` are strictly smaller than `target` (`countLess`).
2. Count how many numbers in `nums` are equal to `target` (`countTarget`).
3. In a sorted array, all numbers smaller than `target` will occupy indices `0` to `countLess - 1`.
4. Therefore, `target` must occupy indices from `countLess` up to `countLess + countTarget - 1`.
5. We can directly populate the list with these indices without sorting at all!

### Complexity

**Time:** `O(N)`  
**Space:** `O(1)` auxiliary (excluding result list)

### Comparison

| Aspect | Submitted Approach | Alternative |
|---|---|---|
| Main Idea | Sort array, then iterate to collect indices | Count elements `< target` and `== target` in one pass |
| Time | `O(N log N)` | `O(N)` |
| Space | `O(log N)` | `O(1)` |
| Advantage | Very straightforward, follows problem description directly | Faster $O(N)$ runtime, does not modify input array |

---

## 📌 Key Takeaways

- **Pattern:** Sorting & Array Traversal
- **Core Observation:** Sorting arranges identical elements sequentially, making linear target retrieval trivial.
- **Important Data Structure:** `ArrayList` for dynamic index collection.
- **Time:** `O(N log N)`
- **Space:** `O(log N)` auxiliary

### Remember

> When asked for target positions *after* sorting, ask yourself if you actually need to sort in $O(N \log N)$, or if you can simply count smaller and equal elements to derive the final indices in $O(N)$ time!

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/find-target-indices-after-sorting-array/)
