# 2091. Removing Minimum and Maximum From Array

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The core idea behind the submitted solution is to reduce the problem to finding the 0-based indices of the minimum and maximum elements in the array, and then evaluating three distinct options for deleting both targets from either end of the array.

Since deletion operations are only permitted from the front (left) or back (right) of the array, removing an element located at index `k` from the left requires `k + 1` deletions, while removing it from the right requires `n - k` deletions (where `n` is the array length). When two specific target elements (the minimum and the maximum) need to be removed, there are only three possible deletion strategies to consider:
1. Deleting both elements starting from the **left** side (removing up to the further index).
2. Deleting both elements starting from the **right** side (removing up to the closer index to the left from the right).
3. Deleting one element from the **left** side and the other from the **right** side.

The primary DSA pattern applied here is **Array / Greedy Case Analysis**. By finding the positions of the minimum and maximum values, the optimal answer is simply the minimum operation count across the three valid strategies.

### Why This Approach?

When confronted with removing specific targets from the boundaries of an array, a naive approach might simulate all possible sequences of left and right deletions using recursion or dynamic programming. However, this is unnecessary because the elements between or around our target elements do not matter—only the relative positions of the minimum and maximum elements determine how many elements must be removed.

By observing that deleting an element at index `idx` from the left automatically removes all elements before it, and deleting from the right removes all elements after it, we can formulate exact operation counts for all valid deletion paths. Evaluating these three deterministic strategies guarantees finding the global minimum in linear time.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to remove both the minimum element and the maximum element from the array `nums` using the minimum total number of deletions. Deletions can only take place at the extreme left (front) or extreme right (back) of the array in each move.

### Step 2: Identify the Key Observation

Let the smaller index between the minimum and maximum elements be `first_idx` and the larger index be `second_idx`. To remove both target elements, any valid set of operations must clear out both `first_idx` and `second_idx`.

There are only three ways to cover both indices:
- **Both from Front (Left):** Delete elements from index `0` up to `second_idx`. Total operations = `second_idx + 1`.
- **Both from Back (Right):** Delete elements from index `n - 1` down to `first_idx`. Total operations = `n - first_idx`.
- **Split (Left & Right):** Delete elements from index `0` up to `first_idx` from the left, and from `n - 1` down to `second_idx` from the right. Total operations = `(first_idx + 1) + (n - second_idx)`.

### Step 3: Recognize the Pattern

This is an **Array Indexing and Case Minimization** problem. Once the target locations are determined, no state tracking or complex searching is required. A constant-time check across all valid options yields the minimal deletions.

### Step 4: Decide What Information We Need to Maintain

To perform the calculations, we only need:
1. `n`: The total length of the array.
2. `idx_min`: The index of the minimum element in `nums`.
3. `idx_max`: The index of the maximum element in `nums`.

### Step 5: Derive the Algorithm

1. Scan `nums` to find the index of the minimum value (`smallest`).
2. Scan `nums` to find the index of the maximum value (`largest`).
3. Compute `first_idx = min(smallest, largest)` and `second_idx = max(smallest, largest)`.
4. Calculate the cost for all three removal strategies.
5. Return the minimum cost among the three strategies.

---

## 🔍 Algorithm

1. Find the index of the minimum element in `nums` by iterating through the array.
2. Find the index of the maximum element in `nums` by iterating through the array.
3. Determine the minimum index `min_pos = Math.min(smallest, largest)` and maximum index `max_pos = Math.max(smallest, largest)`.
4. Compute `left` removal cost: `max_pos + 1`.
5. Compute `right` removal cost: `n - min_pos`.
6. Compute `both` (split) removal cost: `(min_pos + 1) + (n - max_pos)`.
7. Return the overall minimum: `Math.min(left, Math.min(right, both))`.

### Important Implementation Details

- `smallest(nums)` → Helper method returning the 0-based index of the smallest element in `nums`.
- `largest(nums)` → Helper method returning the 0-based index of the largest element in `nums`.
- `max = 1 + Math.max(...)` → Cost of removing both targets from the left side.
- `right = n - Math.min(...)` → Cost of removing both targets from the right side.
- `both = 1 + Math.min(...) + n - Math.max(...)` → Cost of removing the closer target from the left and the farther target from the right.

---

## 🧩 Understanding the Code

### Helper Methods: Locating Minimum and Maximum

```java
public int smallest(int nums[]) {
    int small = Integer.MAX_VALUE;
    int idx = -1;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] < small) {
            small = nums[i];
            idx = i;
        }
    }
    return idx;
}

public int largest(int nums[]) {
    int small = Integer.MIN_VALUE; // Used as max tracker variable
    int idx = -1;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > small) {
            small = nums[i];
            idx = i;
        }
    }
    return idx;
}
```

These helper functions iterate through `nums` to find the indices of the minimum and maximum elements respectively. Note that in `largest`, the variable tracking the maximum value is named `small`, but its logic correctly identifies the maximum value index.

### Decision Phase: Evaluating the Three Strategies

```java
int n = nums.length;
int ans = -1;
int max = 1 + Math.max(smallest(nums), largest(nums));

int left = max;
int right = n - Math.min(smallest(nums), largest(nums));
int both = 1 + Math.min(smallest(nums), largest(nums))
        + n - Math.max(smallest(nums), largest(nums));

ans = Math.min(left, (Math.min(right, both)));
return ans;
```

This block calculates the candidate answer for each strategy:
- `left`: Deletes from index `0` up to `max(smallest, largest)`.
- `right`: Deletes from index `n - 1` down to `min(smallest, largest)`.
- `both`: Deletes from index `0` up to `min(smallest, largest)` from the front, and from `n - 1` down to `max(smallest, largest)` from the back.

Finally, `Math.min(left, Math.min(right, both))` selects the option requiring the fewest total deletions.

---

## 🧠 Why This Works

Every removal operation takes an element from either the current left boundary or the current right boundary. Thus, after $k$ total operations, we will have removed some prefix of length $L$ and some suffix of length $R$ such that $L + R = k$.

To satisfy the problem constraints, the removed prefix and suffix together must contain both the minimum and maximum elements. 
Since any valid solution is defined by choosing a prefix length $L$ and a suffix length $R$:
1. If $R = 0$, $L$ must cover both indices $\implies L = \max(\text{idx}_{\min}, \text{idx}_{\max}) + 1$.
2. If $L = 0$, $R$ must cover both indices $\implies R = n - \min(\text{idx}_{\min}, \text{idx}_{\max})$.
3. If both $L > 0$ and $R > 0$, $L$ covers the closer index to the left ($\min(\text{idx}_{\min}, \text{idx}_{\max}) + 1$) and $R$ covers the closer index to the right ($n - \max(\text{idx}_{\min}, \text{idx}_{\max})$).

Because any other combination of $L$ and $R$ that covers both indices would strictly perform redundant deletions without covering any new necessary targets, these three cases form an exhaustive search space for the optimal answer.

### Key Invariant

The minimum number of deletions needed to remove two specific indices $i$ and $j$ ($i \le j$) from an array of length $n$ is always bounded by $\min(j + 1, n - i, (i + 1) + (n - j))$.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

- Finding the minimum index requires traversing the array of size $N$ once ($O(N)$).
- Finding the maximum index requires traversing the array of size $N$ once ($O(N)$).
- In `minimumDeletions`, `smallest(nums)` and `largest(nums)` are called multiple times. Specifically, helper functions are executed 6 times in total across the formula expressions.
- $6 \times O(N) = O(N)$ total operations. The time complexity scales linearly with the input array length $N$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

The implementation uses a constant number of primitive integer variables (`n`, `ans`, `max`, `left`, `right`, `both`, `small`, `idx`, `i`) to compute the answer. No additional dynamic data structures or memory allocations proportional to the input size are used.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of invoking helper methods multiple times (which re-scans the array 6 times), we can find both the minimum and maximum indices in a **single pass**. Furthermore, storing the indices in local variables avoids redundant computations and makes the code cleaner.

### Complexity

**Time:** `O(N)` — Single pass over the array instead of six passes.  
**Space:** `O(1)` — Only constant extra space used.

### Comparison

| Aspect | Submitted Approach | Single-Pass Alternative |
|---|---|---|
| Main Idea | Multi-pass index lookup + 3-way min calculation | Single-pass index lookup + 3-way min calculation |
| Time | `O(N)` (6 array passes) | `O(N)` (1 array pass) |
| Space | `O(1)` | `O(1)` |
| Advantage | Modular logic using helper functions | Faster execution runtime and eliminates redundant calls |

---

## 📌 Key Takeaways

- **Pattern:** Array Indexing & Case Minimization
- **Core Observation:** Any removal of two elements from array ends reduces to three candidate strategies: all from left, all from right, or split from both sides.
- **Important Data Structure:** None (Primitive variables)
- **Time:** `O(N)`
- **Space:** `O(1)`

### Remember

> When removing multiple target elements from the ends of an array, avoid complex simulation—find the target positions first, then minimize over the fixed positional deletion strategies.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/removing-minimum-and-maximum-from-array/)
