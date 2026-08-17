# 1342. Number of Steps to Reduce a Number to Zero

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution directly simulates the step-by-step rules specified by the problem. It repeatedly inspects the current integer `num` until `num` becomes zero. At each iteration, it checks whether `num` is even or odd using the modulo operator (`num % 2 == 0`). 

If `num` is even, the solution divides it by 2. If `num` is odd, it subtracts 1 from `num`. For every operation performed (either a division or a subtraction), an operation counter `count` is incremented by 1.

This straight-forward simulation pattern accurately mimics the exact process required to reduce any positive integer to zero.

### Why This Approach?

When a problem defines a clear set of deterministic rules to transform a number until a termination condition is met (reaching 0), the most intuitive starting point is direct simulation.

We don't need a complex algorithm or data structure because division by 2 exponentially shrinks the magnitude of `num`. Every division cuts the number in half, and every subtraction turns an odd number into an even number, which will immediately be halved in the next step. Because `num` decreases rapidly with each iteration, simply running a `while` loop to execute the logic directly is both easy to write and exceptionally efficient.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to calculate the total number of operations required to transform an integer `num` down to `0`. We are given two distinct allowed actions:
1. Divide `num` by `2` if `num` is even.
2. Subtract `1` from `num` if `num` is odd.

### Step 2: Identify the Key Observation

Notice that subtracting `1` from an odd number always produces an even number. This means an odd step is always immediately followed by an even step (except when the number reaches `0`). Furthermore, dividing an even number by 2 reduces the value significantly. Since every odd number becomes even after subtracting 1, the number of steps is strictly bounded by the binary representation of the integer.

### Step 3: Recognize the Pattern

This problem fits the **Simulation** pattern. We track a state variable (`num`) and apply simple conditional logic in a loop until a base condition (`num == 0`) is met.

### Step 4: Decide What Information We Need to Maintain

To implement this simulation, we only need to keep track of two values:
1. The working value of `num` as it gets modified.
2. An integer counter `count` initialized to `0` to count the number of operations performed.

### Step 5: Derive the Algorithm

1. Initialize `count = 0`.
2. Enter a loop that continues as long as `num > 0`.
3. Inside the loop, check if `num % 2 == 0`:
   - If true (even): update `num = num / 2`.
   - If false (odd): update `num = num - 1`.
4. Increment `count` after taking either action.
5. Once `num` becomes `0`, terminate the loop and return `count`.

---

## 🔍 Algorithm

1. Initialize a step counter `count` to `0`.
2. Check if `num > 0`. If `num` is `0`, skip the loop and return `0`.
3. While `num > 0`:
   - Evaluate `num % 2 == 0`.
   - If even, perform integer division `num = num / 2` and increment `count` by `1`.
   - If odd, perform `num = num - 1` and increment `count` by `1`.
4. Return `count` when the loop exits.

### Important Implementation Details

- `count` → Keeps track of the cumulative number of reduction steps taken.
- `num > 0` → The termination condition ensuring the algorithm stops as soon as `num` reaches zero.
- `num % 2 == 0` → Check used to determine whether `num` is even or odd.

---

## 🧩 Understanding the Code

### Initialization and Loop Condition

```java
int count = 0;
while(num > 0){
```

This sets up our accumulator variable `count` to track total steps. The `while` loop ensures that we process `num` repeatedly until it is reduced to zero.

### Even Number Branching

```java
if(num % 2 == 0){
    num = num / 2;
    count++;
}
```

When `num` is even, dividing by 2 is the required operation. We perform the division and increment `count` by 1 to record this step.

### Odd Number Branching

```java
else{
    num = num - 1;
    count++;
}
```

When `num` is odd, it cannot be evenly divided by 2, so we subtract 1 to make it even and increment `count` by 1.

---

## 🧠 Why This Works

The logic guarantees progress on every iteration because every operation strictly decreases the value of `num` whenever `num > 0`. 

- An even operation replaces `num` with `num / 2`, which is strictly smaller than `num` for all `num >= 2`.
- An odd operation replaces `num` with `num - 1`, which reduces `num` by 1 and produces an even number.

Because `num` is always strictly decreasing and non-negative, the process is guaranteed to terminate at `0` in a finite number of steps.

### Key Invariant

At the beginning of each iteration of the `while` loop, `num` is a non-negative integer representing the remaining value to be reduced, and `count` accurately reflects the exact number of operations executed so far.

---

## ⏱️ Time Complexity

**Time:** `O(log N)`

### Why?

Dividing a number by 2 reduces its value exponentially. In binary representation, an even operation corresponds to a bit shift to the right (`>> 1`), and an odd operation corresponds to flipping the least significant bit from `1` to `0`. 

The total number of steps is proportional to the number of bits in the binary representation of `num`. A non-zero integer `N` has approximately `⌊log₂ N⌋ + 1` bits. Thus, the loop runs at most `2 * log₂ N` times, yielding an `O(log N)` time complexity.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

### Why?

The algorithm only allocates a single scalar variable (`count`) and modifies the input primitive parameter `num` in place. No dynamic memory, auxiliary arrays, or recursive stacks are used. Therefore, memory usage remains constant.

---

## 🔄 Alternative Approach

### Alternative Idea: Bitwise Operations & Bit Counting

Instead of explicitly simulating the process step by step, we can think of the problem in terms of binary bits:
- Shifting `num` right by 1 bit (`num >> 1`) is equivalent to dividing an even number by 2.
- Decrementing an odd number (`num - 1`) changes its least significant set bit (`1`) to `0`.

For any positive integer `N` written in binary:
1. Each `1` bit requires **2 steps**: one subtraction to change it to `0`, and one shift to move to the next bit.
2. Each `0` bit (except leading zeros) requires **1 step**: a shift to move to the next bit.
3. The most significant set bit (`1`) only needs **1 step** (subtraction) because once it becomes `0`, the number reaches zero without needing a final shift.

Therefore, total steps = `(total number of bits) + (number of set bits '1') - 1`.

Using Java's built-in methods or bitwise logic, this can be computed directly without a simulation loop.

### Complexity

**Time:** `O(1)` (or `O(log N)` depending on bit length analysis)  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach (Simulation) | Alternative (Bitwise Counting) |
|---|---|---|
| Main Idea | Iteratively perform `/ 2` or `- 1` until `0` | Count total bits and set bits of the binary representation |
| Time | `O(log N)` | `O(1)` for fixed 32-bit integers |
| Space | `O(1)` | `O(1)` |
| Advantage | Very intuitive, easy to read and debug | Faster execution, avoids loop iterations |

---

## 📌 Key Takeaways

- **Pattern:** Simulation / Bit Manipulation
- **Core Observation:** Halving a number in binary is a bit shift, and subtracting 1 flips the last bit from 1 to 0.
- **Important Data Structure:** Primitive integer variables
- **Time:** `O(log N)`
- **Space:** `O(1)`

### Remember

> When a problem asks to count operations that repeatedly divide a number by 2, think about the binary representation: dividing by 2 is a bit shift, and subtracting 1 flips the lowest bit.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/)
