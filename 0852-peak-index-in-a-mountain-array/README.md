# 852. Peak Index in a Mountain Array

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses **Binary Search** to find the peak index in a mountain array. A mountain array strictly increases until it reaches a single peak element and strictly decreases after that peak.

Instead of scanning through the entire array linearly, the algorithm checks the middle element (`mid`) and compares it with its right neighbor (`mid + 1`). If `arr[mid] < arr[mid + 1]`, the array is currently on the ascending slope, which means the peak must lie strictly to the right of `mid`. Conversely, if `arr[mid] >= arr[mid + 1]`, we are either at the peak itself or on the descending slope, meaning the peak lies at `mid` or somewhere to its left.

By leveraging this slope comparison, the algorithm repeatedly eliminates half of the remaining search range until the search space shrinks down to a single element, which is guaranteed to be the peak index.

### Why This Approach?

To find the peak element in a mountain array, a brute-force search could iterate through the array and find the first index $i$ where $arr[i] > arr[i+1]$. While straightforward, a linear scan inspects elements one by one, resulting in an $O(N)$ time complexity.

We can improve this because the mountain array structure creates a binary predicate on slopes:
- For every index before the peak: $arr[i] < arr[i+1]$ (Slope is positive).
- For the peak and every index after the peak: $arr[i] > arr[i+1]$ (Slope is negative).

This monotonic property guarantees that we can discard half of the remaining candidates at each step, reducing the overall time complexity from linear $O(N)$ to logarithmic $O(\log N)$.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We are given a mountain array `arr` of length $N \ge 3$. We need to find the index `i` (the peak) such that:
`arr[0] < arr[1] < ... < arr[i - 1] < arr[i]` and `arr[i] > arr[i + 1] > ... > arr[arr.length - 1]`.

### Step 2: Identify the Key Observation

At any arbitrary index `mid`, comparing `arr[mid]` with `arr[mid + 1]` tells us which side of the mountain peak we are currently standing on:
- If `arr[mid] < arr[mid + 1]`: We are going uphill. The peak must be further to the right (`mid + 1` or beyond).
- If `arr[mid] > arr[mid + 1]`: We are going downhill (or sitting at the peak). The peak must be at `mid` or further to the left.

### Step 3: Recognize the Pattern

Binary search is often applied to sorted arrays, but it actually applies to any search space that exhibits a monotonic decision property. 

Here, the boolean property `isUphill(i) = arr[i] < arr[i + 1]` evaluates to `true` for all indices before the peak and `false` starting from the peak onwards. This binary split (`true, true, ..., true, false, false, ..., false`) is a classic candidate for **Binary Search**.

### Step 4: Decide What Information We Need to Maintain

To perform binary search, we maintain:
- `l`: The lower boundary of the search range, initially `0`.
- `h`: The upper boundary of the search range, initially `arr.length - 1`.
- `mid`: The middle pointer calculated as `l + (h - l) / 2`.

### Step 5: Derive the Algorithm

1. Initialize `l = 0` and `h = arr.length - 1`.
2. While `l < h`:
   - Compute `mid = l + (h - l) / 2`.
   - If `arr[mid] < arr[mid + 1]`, set `l = mid + 1` (peak is strictly right of `mid`).
   - Otherwise, set `h = mid` (`mid` could be the peak or peak is to the left).
3. When `l == h`, the search space has narrowed down to a single index, which is our peak index. Return `l`.

---

## 🔍 Algorithm

1. Initialize two pointers `l = 0` and `h = arr.length - 1`.
2. Enter a loop that continues as long as `l < h`.
3. Compute the mid-point using `mid = l + (h - l) / 2`.
4. Compare `arr[mid]` with `arr[mid + 1]`:
   - If `arr[mid] < arr[mid + 1]`, shift the left pointer `l` to `mid + 1`.
   - Else, shift the right pointer `h` to `mid`.
5. Once `l == h`, exit the loop and return `l`.

### Important Implementation Details

- `l` → Left boundary of the active search range.
- `h` → Right boundary of the active search range.
- `mid = l + (h - l) / 2` → Midpoint calculation formatted to prevent potential integer overflow.
- `arr[mid] < arr[mid + 1]` → Condition used to check if `mid` lies on the increasing slope of the mountain array.

---

## 🧩 Understanding the Code

### Initializing the Search Range

```java
int l = 0;
int h = arr.length-1;
```

This sets the search boundaries to cover the entire array from index `0` to index `arr.length - 1`.

### Binary Search Loop and Comparison

```java
while (l < h) {
    int mid = l + (h - l) / 2;
    if (arr[mid] < arr[mid + 1]) {
        l = mid + 1;
    } else {
        h = mid;
    }
}
```

The loop runs as long as there is more than one candidate index in the search range (`l < h`). 
- When `arr[mid] < arr[mid + 1]`, we know `mid` cannot be the peak because `arr[mid + 1]` is greater. Thus, we safely exclude `mid` by setting `l = mid + 1`.
- When `arr[mid] >= arr[mid + 1]`, `mid` itself could be the peak, or the peak could be to its left. Therefore, we keep `mid` inside our valid range by setting `h = mid`.

### Returning the Peak Index

```java
return l;
```

When `l == h`, the loop terminates. The range has converged to a single element, which represents the peak index.

---

## 🧠 Why This Works

The problem guarantees that the array strictly increases up to a peak and strictly decreases after it. This guarantees that every index `i` before the peak satisfies `arr[i] < arr[i + 1]`, and every index `i` from the peak onwards satisfies `arr[i] > arr[i + 1]`.

By adjusting `l = mid + 1` on an increasing slope and `h = mid` on a decreasing slope, we narrow the search space without ever discarding the actual peak index.

### Key Invariant

Throughout the execution of the loop, the peak index of the array is guaranteed to remain inside the closed interval `[l, h]`.

---

## ⏱️ Time Complexity

**Time:** `O(log N)`

### Why?

With each iteration of the `while` loop, the search space `[l, h]` is cut approximately in half. Given an array of size $N$, the maximum number of steps needed to narrow the range down to a single element is $\lceil \log_2 N \rceil$. Thus, the time complexity is logarithmic, $O(\log N)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

The solution relies only on a few scalar integer variables (`l`, `h`, and `mid`). No additional data structures or dynamic allocations are used, making the space complexity constant.

---

## 🔄 Alternative Approach

### Alternative Idea

A Linear Scan approach traverses the array starting from index `0` and checks each adjacent pair `arr[i]` and `arr[i + 1]`. The peak is the very first index `i` where `arr[i] > arr[i + 1]`.

### Complexity

**Time:** `O(N)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach (Binary Search) | Alternative (Linear Scan) |
|---|---|---|
| Main Idea | Halve search space based on slope comparison | Scan left-to-right until element drops |
| Time | `O(log N)` | `O(N)` |
| Space | `O(1)` | `O(1)` |
| Advantage | Extremely fast for large arrays | Conceptually simple and easy to implement |

---

## 📌 Key Takeaways

- **Pattern:** Binary Search on Monotonic Predicate
- **Core Observation:** The relationship between `arr[mid]` and `arr[mid + 1]` indicates whether we are on the ascending or descending slope of the mountain.
- **Important Data Structure:** Two pointers (`l`, `h`) defining a shrinking search space
- **Time:** `O(log N)`
- **Space:** `O(1)`

### Remember

> Binary search is not limited to sorted arrays; it can be used whenever a binary condition divides the search space into two distinct monotonic halves.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/peak-index-in-a-mountain-array/)
