# 633. Sum of Square Numbers

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted code uses a **Two Pointers** approach to determine whether a given non-negative integer $c$ can be expressed as the sum of two square integers ($a^2 + b^2 = c$).

Instead of checking all possible pairs $(a, b)$, the algorithm leverages the bounded search space. The maximum possible value for either number $a$ or $b$ is $\lfloor\sqrt{c}\rfloor$. The algorithm initializes a lower pointer `i` at `0` and an upper pointer `j` at $\lfloor\sqrt{c}\rfloor$.

At each step, it evaluates the sum of squares $i^2 + j^2$. Because the sequence of squares is monotonically increasing, if $i^2 + j^2 > c$, decrementing `j` strictly decreases the sum. Conversely, if $i^2 + j^2 < c$, incrementing `i` strictly increases the sum. This allows us to eliminate one candidate value from our search space in $O(1)$ time per step until either a valid pair is found or the search space is exhausted.

### Why This Approach?

To solve $a^2 + b^2 = c$, a naive brute-force method would iterate over all pairs of integers $(a, b)$ from $0$ up to $\sqrt{c}$ and check if their squared sum equals $c$. That would take $O(c)$ time, which is too slow given that $c$ can be up to $2^{31} - 1$.

By recognizing that both $i$ and $j$ move within a sorted domain $[0, \lfloor\sqrt{c}\rfloor]$, we can view this as a search problem on an implicitly sorted range, analogous to finding two numbers in a sorted array that sum to a target value. The monotonicity of the squared terms guarantees that two pointers starting at opposite ends can safely prune invalid candidates without missing a potential solution.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to determine if there exist non-negative integers $a$ and $b$ such that $a^2 + b^2 = c$. We only need to return `true` if at least one pair exists, and `false` otherwise.

### Step 2: Identify the Key Observation

The maximum value either $a$ or $b$ can take is $\lfloor\sqrt{c}\rfloor$ because if either is larger, its square alone exceeds $c$. Furthermore, the function $f(x) = x^2$ is monotonically increasing for $x \ge 0$. This monotonicity allows us to systematically increment or decrement candidate values.

### Step 3: Recognize the Pattern

This problem directly maps to the **Two Pointers** pattern (specifically, Two-Sum on a sorted domain):
- The candidate values $0, 1, 2, \dots, \lfloor\sqrt{c}\rfloor$ form a sorted sequence.
- $i^2 + j^2$ increases when $i$ increases, and decreases when $j$ decreases.
- If $i^2 + j^2 > c$, no value greater than or equal to $i$ combined with $j$ can equal $c$, so $j$ can be safely decremented.
- If $i^2 + j^2 < c$, no value less than or equal to $j$ combined with $i$ can equal $c$, so $i$ can be safely incremented.

### Step 4: Decide What Information We Need to Maintain

We need two pointers of type `long` (to prevent 32-bit signed integer overflow during multiplication):
- `i`: The candidate lower integer starting at `0`.
- `j`: The candidate upper integer starting at $\lfloor\sqrt{c}\rfloor$.

### Step 5: Derive the Algorithm

1. Set `i = 0` and `j = (long) Math.sqrt(c)`.
2. While `i <= j`:
   - Compute `sum = i * i + j * j`.
   - If `sum == c`, return `true`.
   - If `sum > c`, decrement `j`.
   - If `sum < c`, increment `i`.
3. If the loop ends without finding a pair, return `false`.

---

## 🔍 Algorithm

1. Initialize pointer `j` to `(long) Math.abs(Math.sqrt(c))` (the maximum bound for $b$).
2. Initialize pointer `i` to `0` (the minimum bound for $a$).
3. Enter a loop that continues while `i <= j`.
4. Check the current sum of squares `i * i + j * j`:
   - If `i * i + j * j > c`, decrement `j` by `1` to reduce the sum.
   - Else if `i * i + j * j < c`, increment `i` by `1` to increase the sum.
   - Else (`i * i + j * j == c`), return `true`.
5. If `i` exceeds `j`, exit the loop and return `false`.

### Important Implementation Details

- `i` → `long` variable representing the lower candidate integer, starting at `0`.
- `j` → `long` variable representing the upper candidate integer, starting at $\lfloor\sqrt{c}\rfloor$.
- `long` types for `i` and `j` → prevents 32-bit integer overflow when calculating `i * i + j * j` for large values of $c$.
- `i <= j` → allows $i$ and $j$ to be equal, covering cases where $c = 2 \cdot a^2$ (e.g., $c = 2$ with $i = 1, j = 1$).

---

## 🧩 Understanding the Code

### Phase 1: Boundary Initialization

```java
long j = (long) Math.abs(Math.sqrt(c));
long i = 0;
```
This phase sets up the lower and upper bounds using `long` variables. $j$ is initialized to the floor of $\sqrt{c}$, which is the largest non-negative integer whose square does not exceed $c$. $i$ is initialized to $0$.

### Phase 2: Two Pointers Search Loop

```java
while(i<=j){
    if(i*i + j*j > c){
        j--;
    }else if(i*i + j*j < c){
        i++;
    }else{
        return true;
    }
}
```
This loop adjusts the search boundaries based on the calculated sum of squares:
- When $i^2 + j^2 > c$, the sum is too large, so $j$ is decremented.
- When $i^2 + j^2 < c$, the sum is too small, so $i$ is incremented.
- When $i^2 + j^2 == c$, a valid pair $(i, j)$ is found, and `true` is returned immediately.

### Phase 3: Failure Return

```java
return false;
```
If `i` surpasses `j`, all candidate pairs have been systematically evaluated or eliminated. Since no pair satisfied $a^2 + b^2 = c$, the function returns `false`.

---

## 🧠 Why This Works

The two-pointer technique works here because the search space of integers $[0, \lfloor\sqrt{c}\rfloor]$ is sorted, and the function $f(a, b) = a^2 + b^2$ is monotonically increasing with respect to both $a$ and $b$.

At any point in the loop, if $i^2 + j^2 > c$, pairing $j$ with any integer $x \ge i$ would yield $x^2 + j^2 \ge i^2 + j^2 > c$. Thus, $j$ cannot be part of any valid solution with the remaining available elements, and $j$ can be safely discarded (`j--`).

Similarly, if $i^2 + j^2 < c$, pairing $i$ with any integer $y \le j$ would yield $i^2 + y^2 \le i^2 + j^2 < c$. Thus, $i$ cannot be part of any valid solution with the remaining available elements, and $i$ can be safely discarded (`i++`).

### Key Invariant

At the start of each iteration of the `while` loop, if a solution $(a, b)$ with $a \le b$ exists, it must satisfy $i \le a \le b \le j$. No valid pair is ever erroneously eliminated.

---

## ⏱️ Time Complexity

**Time:** `O(√c)`

### Why?

The lower pointer `i` starts at `0` and increases, while the upper pointer `j` starts at $\lfloor\sqrt{c}\rfloor$ and decreases. In each iteration of the `while` loop, either `i` is incremented by `1` or `j` is decremented by `1`. The maximum total number of iterations is therefore bounded by the distance between `0` and $\lfloor\sqrt{c}\rfloor$, which is $O(\sqrt{c})$. Computing `Math.sqrt(c)` takes $O(1)$ time. Overall time complexity is $O(\sqrt{c})$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

The algorithm uses only two scalar variables (`i` and `j`) to maintain state. No additional data structures or recursion are used.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using Two Pointers, we can iterate over all possible values of $a$ from $0$ up to $\lfloor\sqrt{c}\rfloor$. For each choice of $a$, we calculate $b^2 = c - a^2$. We can then perform a **Binary Search** for $b$ in the range $[0, \lfloor\sqrt{c}\rfloor]$ to check if $b^2$ exists in the range.

### Complexity

**Time:** `O(√c log √c)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach | Alternative (Binary Search) |
|---|---|---|
| Main Idea | Two Pointers inward from bounds $0$ and $\lfloor\sqrt{c}\rfloor$ | Iterate $a$ from $0$ to $\lfloor\sqrt{c}\rfloor$ and binary search for $b$ |
| Time | `O(√c)` | `O(√c log √c)` |
| Space | `O(1)` | `O(1)` |
| Advantage | Optimal linear scan over search range, simpler logic | Conceptually straightforward reduction to binary search |

---

## 📌 Key Takeaways

- **Pattern:** Two Pointers
- **Core Observation:** Since $a^2 + b^2 = c$ operates over a sorted range $[0, \lfloor\sqrt{c}\rfloor]$, monotonicity lets us prune the search space from both ends.
- **Important Data Structure:** Primitive variables (`long` to avoid integer overflow)
- **Time:** `O(√c)`
- **Space:** `O(1)`

### Remember

> When searching for two numbers in a sorted sequence (or continuous range) whose combination satisfies a monotonic property, two pointers moving inward can reduce time complexity from quadratic to linear relative to the search boundary.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/sum-of-square-numbers/)
