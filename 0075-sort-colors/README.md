# 75. Sort Colors

**Difficulty:** Medium  
**Language:** Java

---

> 🚨 **API CALL FAILED** 🚨
> **Error:** Gemini API request failed: Request timeout after 45000ms
>
> *This means the AI provider rejected the request completely. Falling back to static structural analysis.*

---

## 🧠 Approach

### Core Idea

The submitted solution solves **Sort Colors** using Bit Manipulation. The algorithm evaluates input states and applies bitwise arithmetic and binary bit flags to achieve an optimal solution without unnecessary computations.

It observes the primary constraints of the problem and leverages Bit Manipulation to maintain efficiency.

### Why This Approach?

Recognizing that **Sort Colors** requires efficient state processing, the approach leverages Bit Manipulation properties to eliminate redundant branches and compute results directly rather than using a naive brute-force search.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

Identify the core targets and constraints of **Sort Colors**.

### Step 2: Identify the Key Observation

Recognize the structural property of the problem that enables an efficient algorithm.

### Step 3: Recognize the Pattern

Apply Bit Manipulation to optimize the search space.

### Step 4: Decide What Information We Need to Maintain

Track the necessary state variables, arrays, pointers, or data structures.

### Step 5: Derive the Algorithm

Construct the loop iterations and conditional state transitions.

---

## 🔍 Algorithm

1. Receive input data and initialize operational structures.
2. Apply Bit Manipulation evaluation to process constraints for **Sort Colors**.
3. Update solution state according to logic rules.
4. Return final computed outcome.

### Important Implementation Details

- `solution state` → Maintains current progress and intermediate results.

---

## 🧩 Understanding the Code

### Core Processing Block

Evaluates input elements sequentially or recursively using Bit Manipulation.

---

## 🧠 Why This Works

The mathematical and algorithmic properties of Bit Manipulation guarantee that all valid constraints for **Sort Colors** are satisfied without missing edge cases.

### Key Invariant

Maintains valid state properties throughout execution.

---

## ⏱️ Time Complexity

**Time:** `O(n)`

### Why?

Dependent on input length and operations executed during traversal.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(1)`

Allocated data structures or recursion stack memory.

---

## 📌 Key Takeaways

- **Pattern:** Bit Manipulation
- **Core Observation:** Optimal state evaluation avoids redundant checks.
- **Important Data Structure:** Primary variables / collections used in code.
- **Time:** `O(n)`
- **Space:** `O(1)`

### Remember

> Carefully analyze state transitions and boundary constraints when solving similar problems.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/sort-colors/)
