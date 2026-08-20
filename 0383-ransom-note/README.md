# 383. Ransom Note

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution solves the Ransom Note problem by counting character frequencies using a `HashMap`. The core idea is to treat the `magazine` as a pool of available characters and `ransomNote` as a requirement list that consumes characters from that pool.

First, the algorithm iterates through the `magazine` string and populates a map (`set`) where each character maps to its total occurrence count. Next, it iterates through each character in `ransomNote`. For every character, it checks if there are remaining instances available in the map. If available, it decrements the count in the map; if not available (or count is zero), it immediately returns `false`.

This approach leverages the **Frequency Counter / HashMap** pattern. By converting character availability into counts, the order of characters in both strings becomes irrelevant, reducing the problem to a simple inventory check.

### Why This Approach?

When considering how to check if `ransomNote` can be built from `magazine`, a naive approach might search `magazine` for each character in `ransomNote` and erase or mark matched characters. However, searching and modifying strings repeatedly leads to an inefficient $O(m \times n)$ time complexity, where $m$ is the length of `magazine` and $n$ is the length of `ransomNote`.

To optimize, we observe that character order does not matter; only the quantity of each character matters. By pre-counting character occurrences in `magazine`, we reduce lookups and updates to average $O(1)$ time complexity using a hash table. This reduces the overall time complexity to linear time $O(m + n)$.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to determine if every character in `ransomNote` can be supplied by `magazine`, respecting both character identity and character count (each letter in `magazine` can only be used once).

### Step 2: Identify the Key Observation

The problem is fundamentally about inventory management. `magazine` provides a inventory of letters, and `ransomNote` demands a specific quantity of each letter. We only need to verify:
$$\text{Count of character } c \text{ in } \text{ransomNote} \le \text{Count of character } c \text{ in } \text{magazine}$$

### Step 3: Recognize the Pattern

Because we need to track frequencies of discrete keys (`char`), a **HashMap** (or frequency array) is the ideal data structure. It allows $O(1)$ average time lookups, insertions, and updates.

### Step 4: Decide What Information We Need to Maintain

We need a map or lookup table to store:
- **Key**: Character (`Character`)
- **Value**: Remaining available count in `magazine` (`Integer`)

### Step 5: Derive the Algorithm

1. Traverse `magazine` and record character frequencies into the hash map.
2. Traverse `ransomNote` character by character:
   - Check if the character exists in the map with a count greater than 0.
   - If yes, decrement its count by 1.
   - If no, return `false` because the inventory is exhausted or missing that letter.
3. If all characters of `ransomNote` are successfully processed, return `true`.

---

## 🔍 Algorithm

1. Initialize a `HashMap<Character, Integer>` named `set` to store character frequencies.
2. Loop through `magazine` character by character, updating `set` by incrementing the count for each character using `set.getOrDefault(ch, 0) + 1`.
3. Loop through `ransomNote` character by character:
   - Use `set.getOrDefault(ch, 0)` to check remaining available instances.
   - If the returned value is `0`, return `false` immediately.
   - Otherwise, decrement the count of `ch` in `set` by `1`.
4. If the loop completes without returning `false`, return `true`.

### Important Implementation Details

- `set` → `HashMap<Character, Integer>` used as a frequency map to store available character counts from `magazine`.
- `set.getOrDefault(key, 0)` → Retrieves the current count of a character, returning `0` if the character was never present in `magazine`.
- `set.getOrDefault(..., 0) == 0` → The failure condition checked before attempting to consume a character.

---

## 🧩 Understanding the Code

### Phase 1: Frequency Map Construction

```java
HashMap<Character, Integer> set = new HashMap<>();
for (int i = 0; i < magazine.length(); i++) {
    set.put(magazine.charAt(i), set.getOrDefault(magazine.charAt(i), 0) + 1);
}
```

This block loops through `magazine` and fills `set` with character frequencies. `getOrDefault` handles new characters gracefully by returning `0` before adding `1`.

### Phase 2: Consumption and Validation

```java
for (int i = 0; i < ransomNote.length(); i++) {
    if (set.getOrDefault(ransomNote.charAt(i), 0) == 0) {
        return false;
    }
    set.put(ransomNote.charAt(i), set.get(ransomNote.charAt(i)) - 1);
}
return true;
```

This block iterates through `ransomNote`. If a required character is missing or has a count of `0`, the note cannot be formed, so it returns `false` early. Otherwise, it updates the map with the decremented count.

---

## 🧠 Why This Works

The algorithm works because it enforces a exact 1-to-1 matching strategy for every character required by `ransomNote`. By maintaining exact counts of available characters, decrementing upon use, and terminating as soon as a required character count hits zero, it guarantees that no character from `magazine` is reused beyond its available occurrences.

### Key Invariant

At the start of processing any character at index `i` of `ransomNote`, the map `set` contains the exact unconsumed frequency of every character from `magazine` after satisfying the prefix `ransomNote[0 ... i-1]`.

---

## ⏱️ Time Complexity

**Time:** `O(m + n)`

### Why?

- **Magazine Pass:** Iterating over `magazine` takes $O(m)$ time, where $m$ is the length of `magazine`. Inserting into or updating a `HashMap` takes $O(1)$ time on average.
- **Ransom Note Pass:** Iterating over `ransomNote` takes $O(n)$ time, where $n$ is the length of `ransomNote`. Map lookups and updates take $O(1)$ time on average.
- Total time complexity is $O(m + n)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(k)`

### Why?

The algorithm uses auxiliary space for the `HashMap`. The maximum number of entries in the map is bounded by $k$, the number of unique characters in `magazine`. Since `magazine` consists of lowercase English letters, $k \le 26$, making the auxiliary space bounded by $O(1)$ in terms of input size length.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using a `HashMap<Character, Integer>`, we can use a fixed-size primitive integer array `int[26]` (assuming lowercase English letters). Each index `0` to `25` corresponds to characters `'a'` to `'z'`.

This approach eliminates the overhead of object creation, autoboxing (`char` to `Character`, `int` to `Integer`), and hash calculations.

### Complexity

**Time:** `O(m + n)`  
**Space:** `O(1)`

### Comparison

| Aspect | Submitted Approach (`HashMap`) | Alternative Approach (`int[26]` Array) |
|---|---|---|
| Main Idea | Dynamic `HashMap<Character, Integer>` | Fixed `int[26]` frequency array |
| Time | `O(m + n)` | `O(m + n)` |
| Space | `O(k)` where $k \le 26$ | `O(1)` fixed size |
| Advantage | Extensible to any character set (Unicode/ASCII) | Lower memory overhead, faster execution (no hashing or object wrapper overhead) |

---

## 📌 Key Takeaways

- **Pattern:** HashMap / Frequency Array
- **Core Observation:** The problem reduces to verifying if the available frequency of each character in the source string is greater than or equal to its required frequency in the target string.
- **Important Data Structure:** `HashMap<Character, Integer>` (or `int[26]` array)
- **Time:** `O(m + n)`
- **Space:** `O(k)` where $k$ is the alphabet size

### Remember

> When order does not matter and character counts dictate feasibility, convert strings into frequency counters to enable $O(1)$ lookups and updates.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/ransom-note/)
