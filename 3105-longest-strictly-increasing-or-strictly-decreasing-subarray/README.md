# 3105. Longest Strictly Increasing or Strictly Decreasing Subarray

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses a single-pass greedy state-tracking approach to find the length of the longest strictly increasing or strictly decreasing contiguous subarray. It maintains two counters, `up` and `down`, representing the current length of the strictly increasing and strictly decreasing subarrays ending at the current index, respectively. 

As the algorithm iterates through the array, it compares adjacent elements `nums[i]` and `nums[i+1]`. If `nums[i] < nums[i+1]`, the increasing sequence extends by 1, while the decreasing sequence resets to 1 (since `nums[i+1]` alone forms a subarray of length 1). Conversely, if `nums[i] > nums[i+1]`, the decreasing sequence extends by 1 and the increasing sequence resets. If adjacent elements are equal, neither strictly increasing nor strictly decreasing condition holds across the boundary, so both counters reset to 1.

Throughout the loop, a running maximum variable `max` keeps track of the largest value observed between `up` and `down`. Once the iteration completes, `max` holds the answer.

### Why This Approach?

When faced with finding the longest contiguous subarray satisfying a specific property (like strict monotonicity), we should first consider how the property behaves locally. 

A naive approach would generate all possible contiguous subarrays using two nested loops, checking whether each subarray is strictly increasing or strictly decreasing. This brute-force method takes $O(N^2)$ time because there are $O(N^2)$ contiguous subarrays.

However, strict monotonicity is a local property: whether an element `nums[i+1]` can extend a strictly increasing subarray ending at `nums[i]` depends solely on the relationship between `nums[i]` and `nums[i+1]`. If `nums[i] < nums[i+1]`, any strictly increasing sequence ending at `nums[i]` can be extended by `nums[i+1]`. If not, a new increasing sequence must start at `nums[i+1]`. By recognizing this local dependence, we only need to scan the array once in $O(N)$ time while updating state variables in $O(1)$ space.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We are given an integer array `nums` and must find the maximum length of a contiguous subarray that is either strictly increasing or strictly decreasing. A single element is always trivially strictly monotonic, so for any non-empty array, the minimum possible answer is 1.

### Step 2: Identify the Key Observation

Monotonicity of a contiguous subarray is preserved across adjacent elements:
- If `nums[i] < nums[i+1]`, the strictly increasing subarray ending at `i` is extended to `i+1`.
- If `nums[i] > nums[i+1]`, the strictly decreasing subarray ending at `i` is extended to `i+1`.
- If `nums[i] == nums[i+1]`, strict monotonicity breaks in both directions.

This means we never need to re-examine elements prior to index `i` when deciding the longest monotonic sequence ending at `i+1`.

### Step 3: Recognize the Pattern

This problem fits the **Single-Pass State Tracking / Greedy** pattern on arrays. We process elements sequentially from left to right, updating state variables (`up` and `down`) based on local transitions between adjacent elements.

### Step 4: Decide What Information We Need to Maintain

To compute the answer in a single pass, we maintain:
- `up`: The length of the strictly increasing contiguous subarray ending at index `i+1`.
- `down`: The length of the strictly decreasing contiguous subarray ending at index `i+1`.
- `max`: The maximum length of any strictly monotonic subarray encountered so far across the entire array.

### Step 5: Derive the Algorithm

Start with `up = 1`, `down = 1`, and `max = 1`. Iterate `i` from `0` to `nums.length - 2`:
- Compare `nums[i]` with `nums[i+1]`.
- Update `up` and `down` based on the comparison result.
- Update `max` with `Math.max(max, Math.max(up, down))`.
- Return `max` after processing all adjacent pairs.

---

## 🔍 Algorithm

1. Initialize `up = 1`, `down = 1`, and `max = 1` to account for single-element subarrays.
2. Iterate `i` from `0` up to `nums.length - 2`.
3. Compare `nums[i]` and `nums[i+1]`:
   - If `nums[i] < nums[i+1]`: Increment `up` by 1 and reset `down` to 1.
   - Else if `nums[i] > nums[i+1]`: Increment `down` by 1 and reset `up` to 1.
   - Else (`nums[i] == nums[i+1]`): Reset both `up` and `down` to 1.
4. Update `max = Math.max(max, Math.max(up, down))`.
5. After the loop finishes, return `max`.

### Important Implementation Details

- `up` → Stores the length of the current strictly increasing contiguous subarray ending at index `i + 1`.
- `down` → Stores the length of the current strictly decreasing contiguous subarray ending at index `i + 1`.
- `max` → Stores the highest value of `up` or `down` observed at any point during iteration.
- `i < nums.length - 1` → Loop condition ensuring that `nums[i+1]` is always a valid index without throwing an `ArrayIndexOutOfBoundsException`.

---

## 🧩 Understanding the Code

### Variable Initialization and Setup

```java
int up = 1;
int down = 1;
int max = 1;
```

This block sets up the base case. Any array of size $\ge 1$ has at least one element, so the shortest possible strictly monotonic subarray length is `1`. Initializes `up`, `down`, and `max` to `1`.

### State Updates via Neighbor Comparison

```java
for(int i = 0; i<nums.length-1;i++){
    if(nums[i]<nums[i+1]){
        up++;
        down = 1;
    }else if(nums[i] > nums[i+1]){
        down++;
        up = 1;
    }else{
        up = 1;
        down = 1;
    }
```

This loop inspects every pair of adjacent elements `nums[i]` and `nums[i+1]`. 
- When `nums[i] < nums[i+1]`, the increasing sequence length increments (`up++`), while the decreasing sequence resets to `1` because `nums[i+1]` cannot continue any decreasing trend.
- When `nums[i] > nums[i+1]`, `down` increments and `up` resets to `1`.
- When elements are equal, neither condition holds, so both sequence counts reset to `1`.

### Tracking the Global Maximum

```java
    max = Math.max(max,Math.max(up,down));
}
return max;
```

At the end of each step, `max` is updated with the larger of `up` and `down` if either exceeds the previous global maximum. Finally, `max` is returned as the final answer.

---

## 🧠 Why This Works

The algorithm works because at index `i+1`, the maximum strictly increasing subarray ending at `nums[i+1]` can only be formed by appending `nums[i+1]` to the strictly increasing subarray ending at `nums[i]` (if `nums[i] < nums[i+1]`), or starting a brand-new sequence of length 1 (otherwise). The identical logic applies symmetrically to decreasing subarrays.

Because every contiguous monotonic subarray ends at some index in the array, checking the longest increasing and decreasing subarrays ending at every index guarantees that the global maximum length is captured.

### Key Invariant

At the end of step `i` in the loop, `up` equals the exact length of the longest strictly increasing contiguous subarray ending at index `i+1`, and `down` equals the exact length of the longest strictly decreasing contiguous subarray ending at index `i+1`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

The algorithm uses a single `for` loop that runs from index `0` to `nums.length - 2`. Within each iteration, only constant-time $O(1)$ operations (arithmetic comparisons, increments, and `Math.max` calls) are performed. Thus, for an array of length $N$, the time taken grows linearly with $N$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

The solution only allocates a fixed number of integer variables (`up`, `down`, `max`, and loop index `i`). No dynamic memory allocation or additional data structures are used, maintaining constant auxiliary space regardless of input size $N$.

---

## 🔄 Alternative Approach

### Alternative Idea

A Brute Force approach checks all possible starting indices `i` and ending indices `j` of contiguous subarrays. For each subarray `nums[i...j]`, it verifies whether all adjacent pairs are strictly increasing or strictly decreasing.

### Complexity

**Time:** `O(N^2)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach | Alternative (Brute Force) |
|---|---|---|
| Main Idea | Single-pass state tracking of `up` and `down` counts | Check all contiguous subarrays $(i, j)$ for monotonicity |
| Time | `O(N)` | `O(N^2)` |
| Space | `O(1)` | `O(1)` |
| Advantage | Linear time, single traversal | Conceptually straightforward |

---

## 📌 Key Takeaways

- **Pattern:** Array Traversal / Single-Pass State Tracking
- **Core Observation:** Monotonic sequence lengths can be extended or reset based entirely on the comparison between adjacent elements.
- **Important Data Structure:** Scalar variables (`up`, `down`, `max`)
- **Time:** `O(N)`
- **Space:** `O(1)`

### Remember

> When a problem asks for the longest contiguous subarray satisfying a property based on adjacent elements, track the current valid length in a single pass rather than rechecking sub-ranges with nested loops.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/)
