# 238. Product of Array Except Self

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

For any given index `i` in an array, the product of all elements except `nums[i]` can be decomposed into two independent parts: the product of all elements strictly to its left (the prefix product) and the product of all elements strictly to its right (the suffix product). 

The submitted solution uses a two-pass approach with a single result array to compute these products without using division. In the first pass (left to right), the algorithm computes the cumulative prefix product for each element and stores it directly in the result array `arr`. At index `i`, `arr[i]` holds the product of all elements from index `0` to `i - 1`.

In the second pass (right to left), the algorithm computes the cumulative suffix product using a single variable `suffix` and multiplies it into `arr[i]`. Combining the prefix product already stored in `arr[i]` with the running `suffix` product produces the final answer for every element.

**Main DSA Pattern:** Prefix / Suffix Product Array

### Why This Approach?

To solve this problem, we must compute the product of all elements except `nums[i]` for every index `i`. 

A brute-force approach would iterate through the array for every index `i` and multiply all other elements `nums[j]` where `j != i`. This requires $O(N)$ operations for each of the $N$ elements, resulting in an inefficient $O(N^2)$ time complexity.

Another naive solution is to calculate the total product of all elements in the array and divide by `nums[i]` at each position. However, the problem explicitly forbids using the division operator. Furthermore, division fails when one or more zeroes exist in the array due to division-by-zero errors.

By observing that `result[i] = (product of left elements) * (product of right elements)`, we can calculate all prefix products in one linear pass and all suffix products in a second linear pass. Using the output array itself to store the intermediate prefix products allows us to meet the $O(1)$ auxiliary space constraint.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to create an array where the value at index `i` equals the product of every element in `nums` except `nums[i]`. We must achieve this in $O(N)$ time complexity and without using division.

### Step 2: Identify the Key Observation

Any element `nums[i]` splits the rest of the array into two disjoint segments:
1. Left segment: `nums[0]` through `nums[i - 1]`
2. Right segment: `nums[i + 1]` through `nums[n - 1]`

The answer for index `i` is simply `LeftSegmentProduct * RightSegmentProduct`.

### Step 3: Recognize the Pattern

This problem naturally fits the **Prefix / Suffix accumulation pattern**. Just as prefix sums allow us to answer range sum queries in $O(1)$ time, maintaining running prefix and suffix products lets us compute left and right segment products incrementally in $O(N)$ total operations.

### Step 4: Decide What Information We Need to Maintain

To achieve $O(1)$ extra space (excluding the output array):
- An output array `arr` of length $N$ to store intermediate prefix products and final results.
- A running scalar variable `prefix` to track the cumulative product from left to right.
- A running scalar variable `suffix` to track the cumulative product from right to left.

### Step 5: Derive the Algorithm

1. **Forward Pass:** Initialize `prefix = 1`. For each index `i` from `0` to `n - 1`, assign `arr[i] = prefix`, then update `prefix = prefix * nums[i]`. This ensures `arr[i]` stores the product of all elements to the left of `i`.
2. **Backward Pass:** Initialize `suffix = 1`. For each index `i` from `n - 1` down to `0`, multiply `arr[i]` by `suffix`, then update `suffix = suffix * nums[i]`. This multiplies the left product by the right product at index `i`.

---

## 🔍 Algorithm

1. Read the length `n` of the input array `nums` and allocate an output array `arr` of size `n`.
2. Initialize a running variable `prefix = 1`.
3. Iterate forward through `nums` from `i = 0` to `n - 1`:
   - Set `arr[i] = prefix`.
   - Update `prefix *= nums[i]`.
4. Initialize a running variable `suffix = 1`.
5. Iterate backward through `nums` from `i = n - 1` down to `0`:
   - Set `arr[i] *= suffix`.
   - Update `suffix *= nums[i]`.
6. Return `arr`.

### Important Implementation Details

- `prefix` → Accumulates the product of elements `nums[0 ... i-1]` before index `i`.
- `suffix` → Accumulates the product of elements `nums[i+1 ... n-1]` after index `i`.
- `arr[i] = prefix` → Guarantees that index `0` gets a prefix product of `1` (since it has no left neighbors).
- `arr[i] *= suffix` → Combines the previously calculated left product with the current right product in-place.

---

## 🧩 Understanding the Code

### Phase 1: Forward Pass (Prefix Product Construction)

```java
int prefix = 1;
for(int i = 0; i < n; i++) {
    arr[i] = prefix;
    prefix *= nums[i];
}
```

This block fills `arr` with prefix products. Before processing `nums[i]`, `prefix` holds the product of all elements before `i`. We store this value in `arr[i]` first, and then update `prefix` by multiplying `nums[i]` into it for the next iteration.

### Phase 2: Backward Pass (Suffix Multiplication)

```java
int suffix = 1;
for(int i = n - 1; i >= 0; i--) {
    arr[i] *= suffix;
    suffix *= nums[i];
}
```

This block traverses from right to left. `suffix` represents the product of all elements to the right of `i`. By multiplying `arr[i]` (which currently holds the prefix product) by `suffix`, `arr[i]` becomes the complete product except `nums[i]`. `suffix` is then updated with `nums[i]` for the next step moving left.

---

## 🧠 Why This Works

The algorithm works because multiplication is associative and commutative. For any index `i`, the product of all elements except `nums[i]` can be written as:

$$\text{Result}[i] = \left( \prod_{k=0}^{i-1} \text{nums}[k] \right) \times \left( \prod_{k=i+1}^{n-1} \text{nums}[k] \right)$$

After the first loop, `arr[i]` is mathematically equal to $\prod_{k=0}^{i-1} \text{nums}[k]$. 

During the second loop, before line `arr[i] *= suffix` executes, `suffix` is mathematically equal to $\prod_{k=i+1}^{n-1} \text{nums}[k]$. 

Multiplying `arr[i]` by `suffix` combines the left product and right product, yielding the exact required result for position `i`.

### Key Invariant

- **After Pass 1:** For every index `i`, `arr[i]` strictly equals the product of all elements in `nums[0 ... i-1]`.
- **During Pass 2:** When processing index `i`, `suffix` strictly equals the product of all elements in `nums[i+1 ... n-1]`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

- The first `for` loop runs `n` times, performing $O(1)$ operations per iteration.
- The second `for` loop runs `n` times, performing $O(1)$ operations per iteration.
- Total operations: $2N$, which simplifies to $O(N)$ linear time complexity, where $N$ is the length of `nums`.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

### Why?

- The problem context specifies that the output array `arr` does not count towards the auxiliary space complexity.
- Aside from `arr`, the solution only uses a fixed number of integer variables (`n`, `prefix`, `suffix`, and loop counters `i`).
- No extra arrays, hash maps, or recursive stacks are used, yielding $O(1)$ auxiliary space complexity.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of calculating the suffix products in-place, an alternative approach is to construct two separate auxiliary arrays: `left[N]` and `right[N]`. 

1. `left[i]` stores the product of all elements to the left of `i`.
2. `right[i]` stores the product of all elements to the right of `i`.
3. Construct the output array by setting `ans[i] = left[i] * right[i]`.

While this approach is conceptually easier to visualize, it requires $O(N)$ extra auxiliary space to store the `left` and `right` arrays.

### Complexity

**Time:** `O(N)`  
**Space:** `O(N)`

### Comparison

| Aspect | Submitted Approach | Two Extra Arrays Approach |
|---|---|---|
| Main Idea | Store prefix products in output array; multiply running suffix product in reverse pass | Maintain explicit `left` and `right` product arrays |
| Time | `O(N)` | `O(N)` |
| Auxiliary Space | `O(1)` | `O(N)` |
| Advantage | Optimal space complexity; satisfies $O(1)$ extra space follow-up | Easier to understand and debug |

---

## 📌 Key Takeaways

- **Pattern:** Prefix / Suffix Accumulation
- **Core Observation:** Any element's product except itself is equal to `(Product of all left elements) * (Product of all right elements)`.
- **Important Data Structure:** Single output array combined with running scalar variables.
- **Time:** `O(N)`
- **Space:** `O(1)` auxiliary space

### Remember

> When a problem requires combining information from all elements except the current one without division, split the problem into prefix and suffix passes, using the output array to achieve $O(1)$ auxiliary space.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/product-of-array-except-self/)
