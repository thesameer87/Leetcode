# 3718. Smallest Missing Multiple of K

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution finds the smallest positive multiple of $k$ missing from the array `nums` using a hash-based lookup strategy.

It first converts the input array `nums` into a `HashSet` to enable constant-time $O(1)$ average complexity existence checks.

After population, the algorithm generates consecutive positive multiples of $k$ starting from $k \times 1, k \times 2, k \times 3, \dots$ and checks each candidate against the hash set. The very first candidate multiple that is absent from the set is returned immediately as the smallest missing multiple.

The main DSA pattern used here is **Array / HashSet**.

### Why This Approach?

To find the smallest missing positive multiple of $k$, we must test candidate multiples in strictly increasing order ($k, 2k, 3k, \dots$) and check whether each exists in `nums`.

If we repeatedly searched the original array `nums` sequentially for each candidate multiple $k \times i$, each lookup would take $O(N)$ time. In the worst case, where $N$ multiples are present in the array, this brute-force approach would take $O(N^2)$ time.

By preprocessing `nums` into a `HashSet`, each lookup is reduced from $O(N)$ to $O(1)$ on average. This reduces the total time complexity to $O(N)$ while requiring $O(N)$ extra space.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

The goal is to locate the smallest positive integer $M$ such that:
1. $M$ is a multiple of $k$ ($M = k \times i$ for some integer $i \ge 1$).
2. $M$ does NOT appear anywhere in the input array `nums`.

### Step 2: Identify the Key Observation

Since there are $N$ elements in the array `nums`, at most $N$ distinct multiples of $k$ can exist in the array. Therefore, by checking at most $N + 1$ multiples of $k$ (specifically $k \times 1, k \times 2, \dots, k \times (N + 1)$), we are guaranteed to find at least one multiple that is not present in `nums`.

### Step 3: Recognize the Pattern

This is a standard **HashSet Lookup** pattern. When a problem asks us to quickly test whether specific values exist within an unordered dataset, converting the dataset into a hash-based set converts slow linear searches into fast constant-time checks.

### Step 4: Decide What Information We Need to Maintain

We need:
- A `HashSet<Integer>` containing all elements from `nums`.
- A candidate factor counter $i$ starting at $1$ to generate successive multiples $k \times i$.

### Step 5: Derive the Algorithm

1. Insert every element of `nums` into a `HashSet`.
2. Start a loop with $i = 1$ and compute candidate `num = k * i`.
3. Check if `num` exists in the set.
4. If it does not exist, return `num`.
5. If it exists, increment $i$ and repeat.

---

## 🔍 Algorithm

1. Initialize a `HashSet<Integer>` named `set`.
2. Iterate through each element in `nums` and add it to `set`.
3. Start an infinite loop with counter $i = 1$.
4. Compute the current multiple `num = k * i`.
5. Check if `set.contains(num)` is false. If so, return `num`.
6. Increment $i$ and continue the loop.

### Important Implementation Details

- `set` → `HashSet<Integer>` storing unique numbers from `nums` for $O(1)$ average time lookups.
- `i` → Loop variable starting at $1$, representing the $i$-th multiple factor.
- `num` → Computed value `k * i`, representing the candidate positive multiple of $k$.
- `!set.contains(num)` → Termination condition that identifies the first candidate missing from the input array.

---

## 🧩 Understanding the Code

### Populating the HashSet

```java
HashSet<Integer> set = new HashSet<>();
for(int i =0 ; i<nums.length;i++){
    set.add(nums[i]);
}
```

This loop populates the hash set with all values from `nums`. Doing this upfront cost of $O(N)$ time allows all subsequent membership tests during the multiple check phase to run in $O(1)$ average time.

### Searching for the Smallest Missing Multiple

```java
for(int i = 1; ;i++){
    int num = k * i;
    if(!set.contains(num)){
        return num;
    }
}
```

This unbounded `for` loop checks $k \times 1, k \times 2, k \times 3, \dots$ sequentially. Because $i$ increases monotonically starting from $1$, the first candidate `num` that is not contained in `set` is guaranteed to be the *smallest* missing positive multiple of $k$.

---

## 🧠 Why This Works

The algorithm tests candidate multiples in strictly increasing order ($1k, 2k, 3k, \dots$). Since $k > 0$ and $i$ starts at $1$ and increments by $1$ on each step, the candidate values generated are strictly increasing positive multiples of $k$.

Because the set stores all elements from `nums`, `set.contains(num)` correctly determines whether `num` is present in `nums`. Stopping at the first candidate where `set.contains(num)` returns `false` guarantees that no smaller positive multiple of $k$ was missing, proving correctness.

### Key Invariant

At the start of iteration $i$, all positive multiples $k \times j$ for $1 \le j < i$ have been verified to exist in `nums`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

1. **HashSet Insertion:** Inserting $N$ elements into the `HashSet` takes $O(N)$ average time.
2. **Lookup Loop:** The loop runs at most $N + 1$ times because the array `nums` has $N$ elements, meaning at most $N$ multiples of $k$ can be present in `set`. Each call to `set.contains(num)` takes $O(1)$ average time.

Combining both phases gives $O(N) + O(N) = O(N)$ total average time complexity.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(N)`

1. The `HashSet` stores up to $N$ unique integers from the input array `nums`.
2. Primitive variables (`i`, `num`) use $O(1)$ extra space.

Thus, the auxiliary space required is $O(N)$.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using extra space for a hash set, we can sort the input array `nums` in-place (or operate on a sorted copy). After sorting, we can iterate through `nums` while keeping track of the expected multiple progression $k, 2k, 3k, \dots$.

### Complexity

**Time:** `O(N \log N)`  
**Space:** `O(1)` (if sorting in-place) or `O(N)` (if allocating a sorted copy)

### Comparison

| Aspect | Submitted Approach | Alternative |
|---|---|---|
| Main Idea | Store elements in HashSet and check $k, 2k, \dots$ | Sort array and scan sequentially |
| Time | `O(N)` | `O(N \log N)` |
| Space | `O(N)` | `O(1)` |
| Advantage | Faster linear time execution | Can achieve $O(1)$ auxiliary space if array mutation is allowed |

---

## 📌 Key Takeaways

- **Pattern:** HashSet Lookup / Array Search
- **Core Observation:** Generating multiples sequentially ($k, 2k, 3k, \dots$) ensures the first missing candidate found is the smallest one.
- **Important Data Structure:** `HashSet`
- **Time:** `O(N)`
- **Space:** `O(N)`

### Remember

> When looking for the smallest missing value in a mathematical sequence, generate candidate elements in increasing order and use a HashSet for $O(1)$ existence checks.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/smallest-missing-multiple-of-k/)
