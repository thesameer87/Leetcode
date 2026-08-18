# 645. Set Mismatch

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses the input array itself as a hash table to track frequencies in $O(1)$ auxiliary space. Since the array contains $n$ numbers originally ranging from $1$ to $n$, every valid number corresponds directly to a zero-based index in the array via `idx = Math.abs(value) - 1`.

By iterating through the array and negating the value at the target index `nums[idx]`, the algorithm marks the presence of each number. If an index `idx` is accessed and `nums[idx]` is already negative, it indicates that the value mapping to `idx` has already been encountered, revealing the **duplicate** number.

In a second pass, the algorithm looks for the single index whose element remains positive. Since that index was never marked negative, the number corresponding to it (`index + 1`) is the **missing** number.

The main DSA pattern applied here is **In-Place Array Indexing / Array as a Hash Table**.

### Why This Approach?

When given an array of size $n$ containing elements bounded strictly in the range $[1, n]$, a naive solution would use an external `HashSet` or boolean array of size $n + 1$ to track visited elements. However, that approach requires $O(n)$ extra space.

To optimize auxiliary space to $O(1)$, we realize that the input array itself has enough structural capacity to store binary state ("visited" vs "unvisited"). By using the sign (positive vs negative) of the value at index `i` as a flag, we record that the number `i + 1` exists in the input without destroying the original magnitude of `nums[i]`, which can still be retrieved using `Math.abs()`.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to identify two specific integer values from an array of length $n$:
1. **Duplicate number:** The number that appears twice.
2. **Missing number:** The number from $1$ to $n$ that does not appear at all.

We must return them as a two-element array `[duplicate, missing]`.

### Step 2: Identify the Key Observation

Because elements are constrained to $[1, n]$, there is a 1-to-1 mapping between valid values and array indices ($1 \le \text{val} \le n \implies 0 \le \text{val} - 1 < n$). 

We can encode information into the array itself:
- A negative value at `nums[k]` means the number `k + 1` has been seen.
- A positive value at `nums[k]` means the number `k + 1` has NOT been seen yet.

### Step 3: Recognize the Pattern

This problem falls under the **In-Place Hash / Element-to-Index Mapping** pattern.
When elements are bounded within array index range $[0, n-1]$ or $[1, n]$, the array elements themselves can serve as pointers or indices to encode metadata in-place without needing external hash tables or extra memory allocation.

### Step 4: Decide What Information We Need to Maintain

We need to maintain:
1. `duplicate`: A single integer variable to store the duplicate number once detected.
2. Sign flags within `nums`: Negating numbers in `nums` to mark elements as seen.

### Step 5: Derive the Algorithm

1. Loop through `nums` from index `0` to `n - 1`.
2. Extract the true value using `val = Math.abs(nums[i])`.
3. Compute target index `idx = val - 1`.
4. If `nums[idx] < 0`, `val` has already been processed previously, so store `val` in `duplicate`.
5. Otherwise, set `nums[idx] = -nums[idx]` to mark `val` as visited.
6. After finishing the loop, iterate through `nums` again. The first index `i` where `nums[i] > 0` corresponds to the missing value `i + 1`.

---

## 🔍 Algorithm

1. Initialize `duplicate = -1`.
2. Iterate `i` from `0` to `nums.length - 1`:
   - Compute target index `idx = Math.abs(nums[i]) - 1`.
   - Check if `nums[idx] < 0`. If true, assign `duplicate = Math.abs(nums[i])`.
   - Otherwise, negate `nums[idx]` (`nums[idx] *= -1`).
3. Iterate `i` from `0` to `nums.length - 1`:
   - If `nums[i] > 0`, return `new int[]{duplicate, i + 1}`.
4. Return `new int[]{-1, -1}` as a fallback.

### Important Implementation Details

- `Math.abs(nums[i])` → Retrieves the original number value before any potential sign flips caused by previous steps.
- `idx = Math.abs(nums[i]) - 1` → Maps the number range $[1, n]$ to the zero-based array index range $[0, n - 1]$.
- `nums[idx] < 0` → Serves as a boolean flag indicating that the value `idx + 1` was encountered earlier.
- `nums[idx] *= -1` → Negates the value to mark index `idx` as "visited".

---

## 🧩 Understanding the Code

### Phase 1: Identifying the Duplicate via Negation

```java
int duplicate = -1;
for(int i = 0; i < nums.length; i++){
    int idx = Math.abs(nums[i]) - 1;
    
    if(nums[idx] < 0){
        duplicate = Math.abs(nums[i]);
    }else{
        nums[idx] *= -1;
    }
}
```

This first loop traverses each element. It takes the absolute value of `nums[i]` to avoid errors from previously negated numbers. It maps this value to `idx`. If `nums[idx]` is negative, it means this index was already flipped during a previous iteration, meaning `Math.abs(nums[i])` is the repeated value. Otherwise, it flips `nums[idx]` to negative to mark it as seen.

### Phase 2: Locating the Missing Number

```java
for(int i = 0; i< nums.length;i++){
    if(nums[i] >0){
        return new int[]{duplicate, i+1};
    }
}
return new int[]{-1,-1};
```

This second loop inspects the array state. Exactly one index `i` will still contain a positive value because its corresponding number `i + 1` never appeared in `nums` to trigger a sign flip. Once found, the result array `[duplicate, i + 1]` is immediately returned.

---

## 🧠 Why This Works

The array contains $n$ positions corresponding to numbers $1$ through $n$. Each unique number present in `nums` will mark its corresponding index exactly once by making the element at that index negative. 

- The duplicate number attempts to mark its corresponding index twice. On the second attempt, the target element is already negative, allowing us to capture the duplicate immediately.
- The missing number never attempts to mark its corresponding index. Thus, that specific index remains positive, allowing us to identify the missing number in the second pass.

### Key Invariant

At index `k`, `nums[k] < 0` if and only if the number `k + 1` exists at least once in the input array.

---

## ⏱️ Time Complexity

**Time:** $O(n)$

### Why?

The algorithm performs two separate, non-nested linear iterations over the array of size $n$:
1. First `for` loop runs $n$ times with $O(1)$ operations per iteration (abs, indexing, conditional, sign swap).
2. Second `for` loop runs at most $n$ times with $O(1)$ check per index.

Total time complexity is $O(n) + O(n) = O(n)$.

---

## 💾 Space Complexity

**Auxiliary Space:** $O(1)$

The algorithm modifies the input array `nums` in-place to track visited elements using element signs. It only uses a few primitive variables (`duplicate`, `i`, `idx`), consuming $O(1)$ additional memory.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of modifying the input array, we can use a **Frequency Array / Count Array**. We create an array `count` of size $n + 1$. We traverse `nums`, incrementing `count[num]`. Then we scan `count` from $1$ to $n$: the number with `count[x] == 2` is the duplicate, and `count[x] == 0` is the missing number.

This approach avoids mutating the input array at the cost of using additional memory.

### Complexity

**Time:** $O(n)$  
**Space:** $O(n)$

### Comparison

| Aspect | Submitted Approach | Alternative (Frequency Array) |
|---|---|---|
| Main Idea | In-place sign flipping using array indices | Count frequencies in extra array |
| Time | $O(n)$ | $O(n)$ |
| Space | $O(1)$ auxiliary | $O(n)$ auxiliary |
| Advantage | Optimal $O(1)$ space usage | Non-destructive (does not mutate input array) |

---

## 📌 Key Takeaways

- **Pattern:** In-Place Array Indexing / Hash Table via Sign Negation
- **Core Observation:** When array values are bounded in $[1, n]$, values can be mapped directly to array indices $[0, n-1]$ to store status flags in-place.
- **Important Data Structure:** Modifying input `int[]` in-place as a hash structure.
- **Time:** $O(n)$
- **Space:** $O(1)$ auxiliary

### Remember

> When array values range from $1$ to $n$, the array itself can act as a hash table by using negative signs as visited flags to achieve $O(1)$ extra space.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/set-mismatch/)
