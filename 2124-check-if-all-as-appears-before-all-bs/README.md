# 2124. Check if All A's Appears Before All B's

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses a two-pass linear traversal to verify that all occurrences of `'a'` appear before any occurrence of `'b'`. 

In the first pass, the code scans the string from left to right to find the index of the very first `'b'`. If a `'b'` is found, its index is stored in `ans`; if no `'b'` exists in the string, `ans` defaults to the length of the string `s.length()`.

In the second pass, the code starts reading from the index `ans` to the end of the string. If it encounters any `'a'` at or after the index of the first `'b'`, it immediately returns `false`. If the loop finishes without finding any `'a'` after the first `'b'`, it returns `true`.

The solution uses an Array / Linear Scan pattern. By isolating the exact transition point where `'b'` first appears, it reduces the problem to checking whether any `'a'` exists past that boundary.

### Why This Approach?

To check if all `'a'`s appear before all `'b'`s, we need to ensure there is no index pair $(i, j)$ such that $i < j$ where $s[i] == \text{'b'}$ and $s[j] == \text{'a'}$. 

A naive brute-force approach would check every pair of indices $(i, j)$ with $i < j$ to see if $s[i] == \text{'b'}$ and $s[j] == \text{'a'}$, taking $O(N^2)$ time.

However, we can simplify this requirement. If any `'a'` appears after *some* `'b'`, then that `'a'` must definitely appear after the *first* `'b'`. Therefore, finding the first occurrence of `'b'` and ensuring no `'a'` exists after it is both necessary and sufficient to satisfy the condition in $O(N)$ time.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We are given a string `s` consisting only of characters `'a'` and `'b'`. We need to determine if all `'a'` characters appear strictly before all `'b'` characters. This means once we encounter the first `'b'`, no `'a'` is allowed to appear anywhere later in the string.

### Step 2: Identify the Key Observation

If the string is valid, it must follow the pattern `a...ab...b`, `a...a`, or `b...b`. The invalid state occurs if and only if an `'a'` is positioned somewhere after a `'b'`. 

Crucially, if any `'a'` appears after a `'b'`, it will definitely appear after the **first** `'b'` in the string.

### Step 3: Recognize the Pattern

This problem fits the **Linear Traversal / Array Scanning** pattern. We only need to scan the sequence to find a milestone (the first `'b'`), and then check if subsequent elements violate our constraint (finding an `'a'`).

### Step 4: Decide What Information We Need to Maintain

We need:
1. A char array representation of the string for fast array indexing (`chars`).
2. An integer variable (`ans`) to store the index of the first occurrence of `'b'`.

### Step 5: Derive the Algorithm

1. Convert string `s` into a character array.
2. Initialize `ans` to `s.length()` to handle the case where no `'b'` exists.
3. Loop through `chars` from index `0` to `s.length() - 1`. When the first `'b'` is found, record its index in `ans` and break out of the loop.
4. Loop from index `ans` to `s.length() - 1`. If any character is `'a'`, return `false`.
5. If the second loop completes without finding an `'a'`, return `true`.

---

## 🔍 Algorithm

1. Convert the input string `s` to a character array `chars`.
2. Initialize `ans = s.length()`.
3. Iterate `i` from `0` to `s.length() - 1`:
   - If `chars[i] == 'b'`, assign `ans = i` and terminate the loop.
4. Iterate `i` from `ans` to `s.length() - 1`:
   - If `chars[i] == 'a'`, return `false`.
5. Return `true`.

### Important Implementation Details

- `chars` → Array holding character data of string `s`.
- `ans` → Stores the index of the first `'b'`. Initialized to `s.length()` so that if no `'b'` is present, the second loop is skipped entirely.
- `if(chars[i] == 'b') ... break;` → Ensures `ans` strictly captures the *first* occurrence of `'b'`.
- `if(chars[i] == 'a') return false;` → Triggers an early exit if an invalid sequence is detected.

---

## 🧩 Understanding the Code

### Phase 1: Locate the First `'b'`

```java
char[] chars = s.toCharArray();
int ans = s.length();
for(int i = 0; i < s.length(); i++){
    if(chars[i] == 'b'){
        ans = i;
        break;
    }
}
```

This section creates a character array and searches for the first index containing `'b'`. The `break` statement ensures we stop searching after the first `'b'` is found. If no `'b'` exists, `ans` remains equal to `s.length()`.

### Phase 2: Verify Remaining Characters

```java
for(int i = ans; i < s.length(); i++){
    if(chars[i] == 'a'){
        return false;
    }
}
return true;
```

This phase scans the rest of the string starting from index `ans` (the position of the first `'b'`). If any `'a'` is detected at or after this position, it means an `'a'` appears after a `'b'`, violating the problem condition, so `false` is returned. If the loop completes without issue, the function returns `true`.

---

## 🧠 Why This Works

The algorithm works because any valid string where all `'a'`s appear before all `'b'`s can be split into two contiguous segments: a prefix of `'a'`s (possibly empty) followed by a suffix of `'b'`s (possibly empty). 

By finding the boundary index `ans` of the first `'b'`, every character before `ans` is guaranteed to be `'a'` (since `ans` is the *first* `'b'`). To satisfy the valid format, every character from `ans` to the end of the string must be `'b'`. Checking that no `'a'` exists from `ans` onward directly verifies whether this condition holds.

### Key Invariant

For all indices $k$ in $[0, \text{ans} - 1]$, $s[k] == \text{'a'}$. The algorithm then asserts that for all indices $j$ in $[\text{ans}, N-1]$, $s[j] \neq \text{'a'}$.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

- Converting the string to a character array takes $O(N)$ time, where $N$ is the length of `s`.
- The first loop runs at most $N$ times to find the first `'b'`.
- The second loop runs at most $N$ times from index `ans` to $N - 1$.
- Total work across both loops is bounded by $2N$ steps, resulting in linear time complexity $O(N)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(N)`

### Why?

`s.toCharArray()` allocates a new `char[]` of length $N$. Aside from this array, only a few primitive integer variables (`ans`, `i`) are used, which consume $O(1)$ additional space. Thus, the auxiliary space complexity is $O(N)$.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using a two-pass approach with a temporary character array, we can use a single pass that checks whether the substring `"ba"` exists in `s`. An `'a'` appearing after a `'b'` in any position implies that somewhere in the string, a `'b'` must immediately precede an `'a'` (or appear before it). In Java, checking `!s.contains("ba")` accomplishes this in a clean, single line of code.

Alternatively, a single manual pass can maintain a `boolean seenB = false`. Whenever we see `'b'`, we set `seenB = true`. If we see `'a'` while `seenB` is `true`, we return `false`.

### Complexity

**Time:** `O(N)`  
**Space:** `O(1)` (when using a single pass with `charAt()` without `toCharArray()`)

### Comparison

| Aspect | Submitted Approach | Single-Pass (`seenB` Flag) |
|---|---|---|
| Main Idea | Find first `'b'`, check for `'a'` after it | Track if `'b'` was seen; fail if `'a'` appears after |
| Time | `O(N)` | `O(N)` |
| Space | `O(N)` (due to `toCharArray()`) | `O(1)` |
| Advantage | Clear separation of two phases | Single pass, $O(1)$ extra space |

---

## 📌 Key Takeaways

- **Pattern:** Linear Array Scan / Sequence Validation
- **Core Observation:** An invalid configuration exists if and only if an `'a'` appears after the *first* `'b'`.
- **Important Data Structure:** Primitive variables (`int`, `char[]`)
- **Time:** `O(N)`
- **Space:** `O(N)`

### Remember

> To check if elements of type A always appear before elements of type B, find the first occurrence of B and verify that no A appears after it (or check for the illegal adjacent pair `"BA"`).

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/check-if-all-as-appears-before-all-bs/)
