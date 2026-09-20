# 34. Find First and Last Position of Element in Sorted Array

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses two distinct passes of Binary Search to locate the first and last indices of a target value in a sorted array. Because the input array is already sorted, all identical elements are grouped together sequentially in a single contiguous subarray.

In the first pass, the algorithm performs a modified binary search aimed at finding the leftmost (first) occurrence of the target. Whenever `nums[mid]` equals or exceeds `target`, the algorithm records `mid` as a potential starting position (if `nums[mid] == target`) and continues searching the left half by moving the upper pointer `h = mid - 1`. This aggressive leftward shift ensures that any earlier occurrence of `target` will be found.

In the second pass, the algorithm performs another modified binary search targeting the rightmost (last) occurrence. Whenever `nums[m]` is less than or equal to `target`, it records `m` as a potential ending position (if `nums[m] == target`) and continues searching the right half by moving the lower pointer `left = m + 1`. Once both searches complete, the result array containing `[first_index, last_index]` is returned.

The main DSA pattern used here is **Binary Search**.

### Why This Approach?

When given a sorted array and asked to search for an element in $O(\log n)$ time, Binary Search is the natural choice. 

A naive linear search scanning from left-to-right to find the first occurrence, and right-to-left for the last occurrence, would take $O(n)$ time in the worst case (for instance, when the array contains entirely identical numbers).

To achieve logarithmic complexity, we cannot stop as soon as we find `nums[mid] == target` like standard Binary Search does. Instead, we must continue reducing our search space:
1. To find the **first** occurrence, we record the current index upon finding a match and force the binary search to keep looking in the **left** sub-array (`h = mid - 1`).
2. To find the **last** occurrence, we record the current index upon finding a match and force the binary search to keep looking in the **right** sub-array (`left = m + 1`).

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to return an array of two integers: `[start_index, end_index]`. If the `target` is not found in `nums`, we must return `[-1, -1]`.

### Step 2: Identify the Key Observation

In a sorted array with duplicate values, the target elements form a contiguous block: `[first_occurrence ... last_occurrence]`. Standard Binary Search finds *any* occurrence of `target`, which could land anywhere inside this block. To find the true boundaries, we need to adapt the standard binary search logic to bias toward the boundaries.

### Step 3: Recognize the Pattern

Since the search space is monotonically sorted, every comparison `nums[mid] vs target` allows us to eliminate half of the remaining elements. Thus, modified **Binary Search** is ideal.

### Step 4: Decide What Information We Need to Maintain

- An integer array `arr` of size 2, initialized to `{-1, -1}`.
- Search boundaries `l` and `h` for the first pass (first position).
- Search boundaries `left` and `r` for the second pass (last position).

### Step 5: Derive the Algorithm

Run Binary Search twice sequentially:
1. First search: If `nums[mid] >= target`, collapse the right window `h = mid - 1` (saving `mid` into `arr[0]` if `nums[mid] == target`).
2. Second search: If `nums[m] <= target`, collapse the left window `left = m + 1` (saving `m` into `arr[1]` if `nums[m] == target`).

---

## 🔍 Algorithm

1. Initialize `arr = {-1, -1}` to handle cases where `target` does not exist in `nums`.
2. **First Pass (Find First Position):**
   - Set pointers `l = 0` and `h = nums.length - 1`.
   - While `l <= h`:
     - Calculate midpoint `mid = l + (h - l) / 2`.
     - If `nums[mid] >= target`:
       - If `nums[mid] == target`, set `arr[0] = mid`.
       - Shrink the search window to the left: `h = mid - 1`.
     - Else (`nums[mid] < target`), adjust the left boundary: `l = mid + 1`.
3. **Second Pass (Find Last Position):**
   - Reset pointers `left = 0` and `r = nums.length - 1`.
   - While `left <= r`:
     - Calculate midpoint `m = left + (r - left) / 2`.
     - If `nums[m] <= target`:
       - If `nums[m] == target`, set `arr[1] = m`.
       - Expand the search window to the right: `left = m + 1`.
     - Else (`nums[m] > target`), adjust the right boundary: `r = m - 1`.
4. Return `arr`.

### Important Implementation Details

- `arr` → Stores `[first_index, last_index]`, defaulting to `{-1, -1}` if `target` is missing.
- `mid = l + (h - l) / 2` → Prevents potential integer overflow that could occur with `(l + h) / 2`.
- `h = mid - 1` when `nums[mid] == target` → Forces the search towards smaller indices to find the leftmost boundary.
- `left = m + 1` when `nums[m] == target` → Forces the search towards larger indices to find the rightmost boundary.

---

## 🧩 Understanding the Code

### Phase 1: Finding the Starting Index (First Pass)

```java
int [] arr = {-1,-1};
int l = 0;
int h = nums.length-1;
while(l<=h){
    int mid = l+ (h-l)/2;

    if(nums[mid]>= target){
        if(nums[mid] == target){
            arr[0] = mid;
        }
        h = mid-1;
    }else{
        l = mid+1;
    }
}
```

This block executes the first binary search pass. Notice that when `nums[mid] >= target`, we move `h = mid - 1`. If `nums[mid]` happens to be equal to `target`, we store `mid` in `arr[0]`. Even though we found `target`, we don't `break`; we intentionally keep searching left to see if an even earlier instance of `target` exists.

### Phase 2: Finding the Ending Index (Second Pass)

```java
int left = 0;
int r = nums.length-1;
while(left<=r){
    int m = left+ (r-left)/2;

    if(nums[m]<= target){
        if(nums[m] == target){
            arr[1] = m;
        }
        left = m+1;
    }else{
        r = m-1;
    }
}
return arr;
```

This block resets the binary search range to cover the whole array again. When `nums[m] <= target`, we record `m` into `arr[1]` (if equal) and push `left = m + 1`. Moving `left` to the right forces the search to explore higher indices, eventually capturing the last position of `target`.

---

## 🧠 Why This Works

### Key Invariant

During both binary search passes, the search range `[l, h]` (or `[left, r]`) decreases by roughly half in each step. 

- In Pass 1, `arr[0]` monotonically updates to smaller valid indices where `nums[index] == target`.
- In Pass 2, `arr[1]` monotonically updates to larger valid indices where `nums[index] == target`.

Since every index equal to `target` is encountered or narrowed down, `arr[0]` and `arr[1]` are guaranteed to hold the true first and last boundary indices by the end of the loops.

---

## ⏱️ Time Complexity

**Time:** `O(log n)`

### Why?

- The first binary search loop runs in $O(\log n)$ time because the search space `[l, h]` is halved at every iteration.
- The second binary search loop also runs in $O(\log n)$ time for the same reason.
- Total time complexity is $O(\log n) + O(\log n) = O(\log n)$, where $n$ is the length of `nums`.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

### Why?

The implementation only allocates a few primitive variables (`l`, `h`, `mid`, `left`, `r`, `m`) and a fixed-size array `arr` of size 2. No extra memory proportional to input size $n$ is allocated.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of duplicating the binary search code structure twice, we can write a single helper function `findBound(nums, target, isFirst)` that accepts a boolean flag `isFirst`. 

If `isFirst` is `true`, the helper searches for the lower bound (first occurrence). If `false`, it searches for the upper bound (last occurrence).

### Complexity

**Time:** `O(log n)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach | Helper Function Alternative |
|---|---|---|
| Main Idea | Two sequential binary search code blocks written directly inside `searchRange`. | A modular helper function called twice with a boolean parameter. |
| Time | `O(log n)` | `O(log n)` |
| Space | `O(1)` | `O(1)` |
| Advantage | Self-contained within a single method, easy to follow directly. | Clean, non-repetitive code following DRY (Don't Repeat Yourself) principles. |

---

## 📌 Key Takeaways

- **Pattern:** Binary Search
- **Core Observation:** To find extreme boundaries of duplicate elements in a sorted array, continue searching left or right even after finding a match.
- **Important Data Structure:** Primitive search pointers (`l`, `h`, `mid`)
- **Time:** `O(log n)`
- **Space:** `O(1)`

### Remember

> When using Binary Search to find the boundary of duplicate values in a sorted array, don't stop when you hit the target—record the match and continue shrinking the search space towards the desired boundary.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
