# 33. Search in Rotated Sorted Array

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution solves the problem by adapting the standard **Binary Search** algorithm to handle a rotated sorted array. In a standard binary search, we assume the entire array is sorted. However, when a sorted array is rotated at a pivot, dividing it into two halves around a midpoint `mid` guarantees that **at least one of the two halves will always remain strictly sorted**.

By inspecting the element at the left boundary `nums[l]` and comparing it with `nums[mid]`, we can immediately determine which half is sorted. If `nums[l] <= nums[mid]`, the left contiguous segment from index `l` to `mid` is sorted. Otherwise, the right contiguous segment from index `mid` to `h` must be sorted. 

Once we identify the sorted half, we check whether the `target` falls within the value range of that sorted section. If it does, we narrow our search range to that sorted half; if it does not, we eliminate that half and search in the remaining half.

This strategy relies on the **Binary Search** pattern on a modified search space.

### Why This Approach?

When approaching this problem, a simple linear scan across the array would find the target in $O(n)$ time. However, the problem explicitly demands an $O(\log n)$ runtime complexity. An $O(\log n)$ complexity strongly signals a divide-and-conquer strategy like Binary Search.

To apply binary search effectively, we must be able to eliminate half of the search space at each step. In a standard binary search, we eliminate half based on whether `nums[mid] < target`. In a rotated sorted array, `nums[mid]` alone is not enough to determine which direction to go because the array is split into two sorted subarrays offset by a rotation point. 

The critical observation is that splitting a rotated sorted array in half always isolates at least one standard, fully sorted subarray. Because checking whether a target lies inside a sorted range `[A, B]` requires only a simple boundary check (`A <= target <= B`), we can make definitive decisions about which half to discard in $O(1)$ time per step.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to find the index of a given `target` in an array of distinct integers that was originally sorted in ascending order and then rotated at an unknown pivot index. If the `target` does not exist in the array, we must return `-1`. The algorithm must run in $O(\log n)$ time.

### Step 2: Identify the Key Observation

If you pick any arbitrary index `mid` in a rotated sorted array:
- Either the subarray from `nums[l]` to `nums[mid]` is sorted, OR
- The subarray from `nums[mid]` to `nums[h]` is sorted.

Both halves cannot be unsorted simultaneously because the array contains only a single rotation pivot.

### Step 3: Recognize the Pattern

This is a classic **Binary Search** problem with modified decision logic. Instead of directly comparing `nums[mid]` with `target` to decide whether to go left or right, we first locate the sorted half and use range boundaries to decide which half to prune.

### Step 4: Decide What Information We Need to Maintain

We maintain two pointers defining our current search interval:
- `l`: The lower bound index of the active search range (starts at `0`).
- `h`: The upper bound index of the active search range (starts at `nums.length - 1`).
- `mid`: The midpoint calculation `l + (h - l) / 2` to prevent potential integer overflow.

### Step 5: Derive the Algorithm

1. Compute `mid`. If `nums[mid] == target`, return `mid`.
2. Determine which half is sorted:
   - If `nums[l] <= nums[mid]`, the **left half** `[l...mid]` is sorted.
     - Check if `target` lies within `[nums[l], nums[mid]]`.
     - If yes, discard the right half (`h = mid - 1`).
     - If no, discard the left half (`l = mid + 1`).
   - Otherwise (`nums[l] > nums[mid]`), the **right half** `[mid...h]` is sorted.
     - Check if `target` lies within `[nums[mid], nums[h]]`.
     - If yes, discard the left half (`l = mid + 1`).
     - If no, discard the right half (`h = mid - 1`).
3. Repeat until `l > h`. If not found, return `-1`.

---

## 🔍 Algorithm

1. Initialize two pointers: `l = 0` and `h = nums.length - 1`.
2. Execute a loop while `l <= h`:
   1. Calculate the midpoint `mid = l + (h - l) / 2`.
   2. Check if `nums[mid] == target`. If true, return `mid`.
   3. Check if the left segment is sorted using the condition `nums[l] <= nums[mid]`:
      - **If sorted:** Check if `target` is within range `[nums[l], nums[mid]]`.
        - If true, search the left half by setting `h = mid - 1`.
        - Otherwise, search the right half by setting `l = mid + 1`.
      - **If not sorted:** The right segment `[mid...h]` must be sorted. Check if `target` is within range `[nums[mid], nums[h]]`.
        - If true, search the right half by setting `l = mid + 1`.
        - Otherwise, search the left half by setting `h = mid - 1`.
3. If the loop finishes without finding `target`, return `-1`.

### Important Implementation Details

- `l` → Pointer to the left boundary of the remaining search space.
- `h` → Pointer to the right boundary of the remaining search space.
- `mid = l + (h - l) / 2` → Safely computes the midpoint avoiding integer overflow.
- `nums[l] <= nums[mid]` → Condition used to check if the left contiguous segment is monotonically increasing (sorted).

---

## 🧩 Understanding the Code

### Midpoint Evaluation & Target Check

```java
int mid = l + (h - l) / 2;
if (nums[mid] == target) {
    return mid;
}
```

This block calculates the middle index of the active search space and checks if the element at `mid` is the target. If it matches, the index is returned immediately.

### Left-Half Sorted Branch

```java
if (nums[l] <= nums[mid]) {
    if (nums[l] <= target && target <= nums[mid]) {
        h = mid - 1;
    } else {
        l = mid + 1;
    }
}
```

This logic executes when the left half (from `l` to `mid`) is properly ordered. It checks whether `target` falls between `nums[l]` and `nums[mid]`. If `target` is within this range, the search space is restricted to the left side by moving `h`. Otherwise, the target must reside in the right side, so `l` is shifted past `mid`.

### Right-Half Sorted Branch

```java
else {
    if (nums[mid] <= target && target <= nums[h]) {
        l = mid + 1;
    } else {
        h = mid - 1;
    }
}
```

If the left half is not sorted, the rotation point lies in the left half, which guarantees that the right half (from `mid` to `h`) is sorted. This block checks whether `target` falls in the sorted range `[nums[mid], nums[h]]`. If it does, `l` is moved to search the right half. Otherwise, `h` is updated to search the left half.

---

## 🧠 Why This Works

### Key Invariant

Throughout all iterations of the `while (l <= h)` loop, the target value (if present in `nums`) is guaranteed to exist within the boundary indices `[l, h]`. Every step halves the remaining candidate elements by identifying a guaranteed sorted sub-range and accurately determining whether the target resides inside it.

---

## ⏱️ Time Complexity

**Time:** `O(log n)`

### Why?

At each iteration of the `while` loop, the algorithm performs constant time $O(1)$ operations (arithmetic calculations and conditional comparisons) and cuts the search space in half by eliminating either the left or right segment. Halving an array of size $n$ iteratively takes at most $\lceil \log_2 n \rceil$ steps, yielding a logarithmic runtime of $O(\log n)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

The algorithm uses a fixed set of primitive variables (`l`, `h`, `mid`) to track array indices during iteration. No additional dynamic data structures, sub-arrays, or recursive call stacks are created, keeping the auxiliary space strictly constant $O(1)$.

---

## 🔄 Alternative Approach

### Alternative Idea

An alternative two-pass approach is to first find the pivot point (the index of the smallest element in the rotated array) using a standard binary search. Once the pivot index is found, the array is effectively split into two standard sorted subarrays: `[0 ... pivot-1]` and `[pivot ... n-1]`. 

We can then determine which subarray target could potentially belong to by comparing `target` with `nums[n-1]`, and perform a single standard binary search on that specific subarray.

### Complexity

**Time:** `O(log n)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach | Alternative |
|---|---|---|
| Main Idea | Single-pass Binary Search using range checks on sorted half | Two-pass Binary Search (find pivot first, then search target) |
| Time | `O(log n)` | `O(log n)` |
| Space | `O(1)` | `O(1)` |
| Advantage | Done in a single pass; fewer comparisons per cycle | Conceptually simpler because step 2 is standard binary search |

---

## 📌 Key Takeaways

- **Pattern:** Binary Search (Modified Search Space)
- **Core Observation:** Splitting a rotated sorted array at any mid-point guarantees that at least one half is strictly sorted.
- **Important Data Structure:** Two Pointers (`l` and `h`)
- **Time:** `O(log n)`
- **Space:** `O(1)`

### Remember

> When dealing with rotated sorted arrays, check `nums[l] <= nums[mid]` to locate the sorted half first, then use simple boundary comparisons to decide which half to keep.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/search-in-rotated-sorted-array/)
