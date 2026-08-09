# 125. Valid Palindrome

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses a two-pointer technique to determine whether a given string is a valid palindrome while ignoring non-alphanumeric characters and case sensitivity.

Instead of modifying the input string or creating a cleaned secondary string, the algorithm places two pointers at opposite ends of the original string: `start` at index `0` and `last` at index `s.length() - 1`. It increments `start` and decrements `last` towards the center, skipping non-alphanumeric characters along the way. When both pointers point to valid alphanumeric characters, it compares them in a case-insensitive manner using `Character.toLowerCase()`.

The underlying observation is that a palindrome requires corresponding characters from the outer boundaries moving inward to match. Since non-alphanumeric characters are completely ignored in this problem, we can skip over them dynamically during traversal rather than pre-processing the string.

The primary DSA pattern used here is **Two Pointers**.

### Why This Approach?

Before writing code, we must consider how to process non-alphanumeric characters and uppercase/lowercase differences effectively.

A naive approach would be to clean the string first—removing all non-alphanumeric characters using regular expressions or iteration, converting the resulting string to lowercase, and then reversing it or using two pointers on the cleaned string. While intuitive, this pre-processing step allocates extra memory proportional to the length of the string ($O(N)$ extra space).

To optimize space to $O(1)$, we perform filtering on the fly. By maintaining two pointers directly on the original string, we can skip invalid characters in-place. This allows us to complete the evaluation in a single pass without allocating any extra string or character array buffers.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to determine if a string reads the same forward and backward after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters (letters and digits). The output should be a boolean: `true` if it is a valid palindrome, and `false` otherwise.

### Step 2: Identify the Key Observation

A palindrome is symmetrical across its center. For a filtered version of string $S$, $S[i]$ must equal $S[N - 1 - i]$ for all valid indices. This means we can match characters from the boundaries working inward. Crucially, non-alphanumeric characters do not contribute to symmetry, so any non-alphanumeric character encountered can simply be bypassed.

### Step 3: Recognize the Pattern

This is a classic **Two Pointers** pattern.
- Two pointers starting from opposite ends (`start = 0`, `last = n - 1`) move toward each other.
- Two pointers are useful when we need to compare elements from two ends of a linear sequence without reordering elements or requiring extra memory.

### Step 4: Decide What Information We Need to Maintain

We need to track:
- `start`: An integer index tracking the left boundary of our active search window.
- `last`: An integer index tracking the right boundary of our active search window.
- At each step, we retrieve `currFirst` (`s.charAt(start)`) and `currLast` (`s.charAt(last)`).

### Step 5: Derive the Algorithm

1. Handle edge cases (an empty string returns `true`).
2. Initialize `start = 0` and `last = s.length() - 1`.
3. Loop while `start <= last`:
   - If `s.charAt(start)` is not alphanumeric, increment `start`.
   - Else if `s.charAt(last)` is not alphanumeric, decrement `last`.
   - Else (both are alphanumeric): compare lowercase values. If they don't match, return `false`. Otherwise, increment `start` and decrement `last`.
4. If the loop completes without mismatches, return `true`.

---

## 🔍 Algorithm

1. Check if the string `s` is empty; if so, immediately return `true`.
2. Initialize `start` pointer to `0` and `last` pointer to `s.length() - 1`.
3. Begin a loop that continues as long as `start` is less than or equal to `last`.
4. Extract character `currFirst` at index `start` and `currLast` at index `last`.
5. Check if `currFirst` is alphanumeric using `Character.isLetterOrDigit(currFirst)`:
   - If false, advance `start` by 1 (`start++`) and finish the current loop iteration.
6. Check if `currLast` is alphanumeric using `Character.isLetterOrDigit(currLast)`:
   - If false, decrement `last` by 1 (`last--`) and finish the current loop iteration.
7. If both characters are valid alphanumeric characters:
   - Convert both to lowercase using `Character.toLowerCase(...)` and compare them.
   - If they are not equal, return `false`.
   - If they match, advance `start` (`start++`) and decrement `last` (`last--`).
8. If the loop terminates without finding any mismatch, return `true`.

### Important Implementation Details

- `start` → Tracks the position of the left character under inspection.
- `last` → Tracks the position of the right character under inspection.
- `!Character.isLetterOrDigit(...)` → Used to identify and bypass non-alphanumeric characters dynamically.
- `Character.toLowerCase(currFirst) != Character.toLowerCase(currLast)` → Ensures case-insensitive equality checking for matching characters.

---

## 🧩 Understanding the Code

### Base Case & Pointer Initialization

```java
if (s.isEmpty()) {
    return true;
}
int start = 0;
int last = s.length() - 1;
```

This part handles the base case for an empty string, which is vacuously a valid palindrome. It then initializes the two pointers at the boundary positions of string `s`.

### Character Skipping Logic

```java
while(start <= last) {
    char currFirst = s.charAt(start);
    char currLast = s.charAt(last);
    if (!Character.isLetterOrDigit(currFirst )) {
        start++;
    } else if(!Character.isLetterOrDigit(currLast)) {
        last--;
    } ...
```

Inside the loop, the code inspects characters at `start` and `last`. If the left character is not alphanumeric, `start` is incremented to skip it. If the left character is valid but the right character is not, `last` is decremented. This conditional chaining ensures pointers move past non-alphanumeric characters before comparing.

### Case-Insensitive Comparison & Convergence

```java
    } else {
        if (Character.toLowerCase(currFirst) != Character.toLowerCase(currLast)) {
            return false;
        }
        start++;
        last--;
    }
```

When both `currFirst` and `currLast` are valid alphanumeric characters, the code converts both to lowercase and compares them. If they differ, the string cannot be a palindrome, so it returns `false`. If they match, both pointers move inward to continue checking the rest of the string.

---

## 🧠 Why This Works

The algorithm works because symmetry in a palindrome requires every $i$-th valid character from the start to equal the $i$-th valid character from the end. By skipping invalid characters dynamically on both ends, `start` and `last` are guaranteed to point to the next available valid alphanumeric characters simultaneously. Comparing these characters after lowercasing ensures case-insensitive matching. Since the pointers move inward monotonically and stop when `start > last`, every relevant character pair is evaluated exactly once without missing valid characters or introducing false matches.

### Key Invariant

At the start of each iteration of the loop, all valid alphanumeric characters to the left of `start` have been verified to match their corresponding valid alphanumeric counterparts to the right of `last`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

Where $N$ is the length of string `s`. In every iteration of the `while` loop, at least one of the following occurs:
- `start` increases by 1,
- `last` decreases by 1, or
- both `start` increases and `last` decreases by 1.

Since `start` begins at `0` and `last` begins at $N - 1$, the distance `last - start` decreases with every iteration. Thus, the loop runs at most $N$ times. Inside the loop, `charAt`, `isLetterOrDigit`, and `toLowerCase` are all $O(1)$ constant-time operations. Therefore, the overall time complexity is strictly linear, $O(N)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

### Why?

The algorithm operates directly on the input string `s` using only primitive integer variables (`start`, `last`) and primitive character variables (`currFirst`, `currLast`). No extra data structures (such as `StringBuilder`, arrays, or collections) are created, resulting in constant auxiliary space $O(1)$.

---

## 🔄 Alternative Approach

### Alternative Idea

An alternative approach is to first clean the string by filtering out all non-alphanumeric characters and converting all remaining characters to lowercase using a `StringBuilder`. After constructing the cleaned string, we can either compare it with its reverse or run a standard two-pointer check on the cleaned string.

While this approach is easy to conceptualize and implement using built-in methods, it requires allocating memory for the cleaned string buffer.

### Complexity

**Time:** `O(N)`  
**Space:** `O(N)`

### Comparison

| Aspect | Submitted Approach | Alternative |
|---|---|---|
| Main Idea | In-place two pointers skipping invalid chars on the fly | Build cleaned lowercase string first, then verify |
| Time | `O(N)` | `O(N)` |
| Space | `O(1)` | `O(N)` |
| Advantage | Highly memory-efficient; no extra allocations | Conceptually simple and straightforward to read |

---

## 📌 Key Takeaways

- **Pattern:** Two Pointers
- **Core Observation:** Non-alphanumeric characters can be skipped in-place from both ends without allocating a new string.
- **Important Data Structure:** None (uses primitive pointer variables)
- **Time:** `O(N)`
- **Space:** `O(1)`

### Remember

> When a problem requires filtering or transforming elements before checking boundary symmetry, use two pointers directly on the original input to skip unwanted elements in-place and achieve optimal $O(1)$ space complexity.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/valid-palindrome/)
