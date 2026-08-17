# 136. Single Number

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution utilizes the bitwise XOR operator (`^`) to identify the single non-repeating element in an array where every other element appears exactly twice. By taking advantage of the fundamental properties of XOR arithmetic, the algorithm cancels out all paired elements in a single linear pass over the array.

The algebraic properties of bitwise XOR make this cancellation possible:
1. **Identity:** $x \oplus 0 = x$ (XORing any number with zero leaves the number unchanged).
2. **Self-Cancellation:** $x \oplus x = 0$ (XORing any number with itself results in zero).
3. **Commutativity and Associativity:** $a \oplus b = b \oplus a$ and $(a \oplus b) \oplus c = a \oplus (b \oplus c)$ (the order in which numbers are XORed does not affect the final result).

Because XOR is commutative and associative, we can conceptually reorder the array such that all identical pairs are adjacent. When identical pairs are XORed together, they evaluate to 0. Finally, XORing all these 0s with the single unique number leaves the unique number itself.

The primary DSA pattern applied here is **Bit Manipulation**.

### Why This Approach?

When approaching this problem, the initial intuitive thought might be to count the frequency of each number. A naive solution using nested loops takes $O(n^2)$ time. A hash map or hash set frequency counter reduces the time complexity to $O(n)$, but requires $O(n)$ auxiliary space to store elements.

To optimize space to $O(1)$ without sacrificing $O(n)$ time complexity, we look for mathematical or bitwise properties that can erase duplicate information on the fly. The self-canceling property of the XOR operation ($x \oplus x = 0$) fits this constraint perfectly. It allows us to track state using only a single integer variable instead of an auxiliary data structure.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to extract the single unique integer from an input array where every other integer occurs twice. The solution must run efficiently and ideally without using extra memory structures like maps or sets.

### Step 2: Identify the Key Observation

Consider what happens when binary representations of numbers undergo XOR operations:
- Bits that are the same resolve to `0`.
- Bits that are different resolve to `1`.

If we perform a cumulative XOR across the entire array:
$$\text{ans} = \text{nums}[0] \oplus \text{nums}[1] \oplus \text{nums}[2] \oplus \dots \oplus \text{nums}[n-1]$$

Every number that appears twice will pair up with its duplicate and evaluate to $0$. The single number will remain paired with $0$, yielding the single number itself.

### Step 3: Recognize the Pattern

This problem is a classic application of **Bit Manipulation**. Bitwise operations are appropriate whenever we need:
- In-place tracking of state without extra memory.
- Parity checks or pair cancellation properties.
- Fast, hardware-level arithmetic evaluation.

### Step 4: Decide What Information We Need to Maintain

We only need a single integer variable, `ans`, initialized to `0`. 

- Initial value: `0` (since $0 \oplus x = x$, it acts as the neutral element).
- Updated state: `ans` holds the running XOR product of all array elements processed up to the current index.

### Step 5: Derive the Algorithm

1. Initialize `ans = 0`.
2. Iterate through each number in `nums`.
3. Update `ans` by XORing it with the current number (`ans ^= nums[i]`).
4. Once the loop finishes, return `ans`.

---

## 🔍 Algorithm

1. Initialize an integer variable `ans` to `0`.
2. Loop through the array `nums` from index `0` to `nums.length - 1`.
3. In each iteration, perform a bitwise XOR between `ans` and `nums[i]`, assigning the result back to `ans`.
4. After completing the loop, return `ans`.

### Important Implementation Details

- `ans` → Holds the running cumulative XOR value.
- `ans ^= nums[i]` → Shorthand bitwise assignment operator that evaluates `ans = ans ^ nums[i]`.

---

## 🧩 Understanding the Code

### Initialization Phase

```java
int ans = 0;
```

This line sets up the accumulator variable. Initializing `ans` to `0` ensures that the first XOR operation (`0 ^ nums[0]`) yields `nums[0]`, successfully beginning the cumulative accumulation.

### Iteration and XOR Phase

```java
for(int i = 0; i < nums.length; i++) {
    ans ^= nums[i];
}
```

This loop traverses the array from start to finish. In each step, `ans` combines with `nums[i]` using bitwise XOR. Duplicate values encountered at any point in the iteration will eventually cancel each other out, regardless of where they appear in the array.

### Returning the Unique Value

```java
return ans;
```

After iterating through all elements, all paired numbers have neutralized each other to zero. The variable `ans` now holds the exact value of the single unpaired element, which is returned.

---

## 🧠 Why This Works

Suppose our input array is `[4, 1, 2, 1, 2]`.

The cumulative XOR expression evaluated by the code is:
$$\text{ans} = 0 \oplus 4 \oplus 1 \oplus 2 \oplus 1 \oplus 2$$

By applying the commutative and associative properties of XOR, we can rearrange the terms:
$$\text{ans} = 0 \oplus 4 \oplus (1 \oplus 1) \oplus (2 \oplus 2)$$

Using the self-cancellation property ($x \oplus x = 0$):
$$\text{ans} = 0 \oplus 4 \oplus 0 \oplus 0$$

Using the identity property ($x \oplus 0 = x$):
$$\text{ans} = 4$$

Regardless of the length of the array or the distribution of numbers, every number appearing twice cancels out to zero, leaving only the single number behind.

### Key Invariant

Throughout the execution of the loop at index `i`, `ans` maintains the exact XOR sum of all elements in the prefix `nums[0 ... i]`.

---

## ⏱️ Time Complexity

**Time:** `O(n)`

### Why?

The code uses a single `for` loop that iterates through the array from index `0` to `nums.length - 1`. Within each iteration, a bitwise XOR operation and loop index increment are executed in constant time $O(1)$. Therefore, the total time spent scales linearly with the size of the input array, $n$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

### Why?

The algorithm only allocates a single scalar variable (`ans`) and a loop counter (`i`). No additional dynamic memory, hash tables, arrays, or recursion stacks are created. The auxiliary space required remains constant regardless of the input size $n$.

---

## 🔄 Alternative Approach

### Alternative Idea

A common alternative approach uses a **HashSet** to track frequency:
1. Iterate through `nums`.
2. For each number, if it is already present in the set, remove it (since we found its pair).
3. If it is not present in the set, add it.
4. After processing all numbers, the HashSet will contain exactly one element, which is the single number.

### Complexity

**Time:** `O(n)`  
**Space:** `O(n)`

### Comparison

| Aspect | Submitted Approach (Bit Manipulation) | Alternative (HashSet) |
|---|---|---|
| Main Idea | Cumulative XOR cancels out duplicates | Insert/remove elements to leave the unique element |
| Time | `O(n)` | `O(n)` |
| Space | `O(1)` | `O(n)` |
| Advantage | Optimal space complexity, faster execution without memory overhead | Intuitive to derive without knowledge of bitwise operations |

---

## 📌 Key Takeaways

- **Pattern:** Bit Manipulation (Bitwise XOR)
- **Core Observation:** Identical numbers cancel out under bitwise XOR ($x \oplus x = 0$), leaving behind the single non-repeating number.
- **Important Data Structure:** None (uses a single primitive integer accumulator variable).
- **Time:** `O(n)`
- **Space:** `O(1)`

### Remember

> When a problem involves paired duplicates where all elements except one appear an even number of times, cumulative bitwise XOR cancels the duplicates in $O(n)$ time and $O(1)$ space.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/single-number/)
