# 242. Valid Anagram

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution determines whether two strings, `s` and `t`, are anagrams by comparing the frequency of each character in both strings. An anagram is formed when two strings contain the exact same characters with the exact same frequencies, regardless of character order.

The algorithm first checks if the two strings have identical lengths. If they do not, they cannot be anagrams, and the function immediately returns `false`. If the lengths match, it uses a hash map (`HashMap<Character, Integer>`) to count the frequency of each character in `s`. 

Next, the algorithm iterates through string `t` and decrements the stored counts in the hash map. If a character in `t` is missing from the hash map or its count is already zero (indicating `t` contains more instances of that character than `s`), the algorithm immediately returns `false`. If all characters in `t` are successfully matched and decremented, the strings are valid anagrams, and the function returns `true`.

The primary algorithmic pattern used here is **HashMap / Frequency Counting**.

### Why This Approach?

When faced with determining if two strings contain the same set of characters with identical frequencies, we need a way to tally character occurrences efficiently. 

A naive brute-force approach might sort both strings and compare them character-by-character. While simple, sorting takes $O(N \log N)$ time. Another naive approach might search for and cross off matching characters in string `t` for each character in string `s`, taking $O(N^2)$ time.

By shifting our focus from character order to character count, we realize that order is completely irrelevant. A hash map allows us to store character frequencies and look them up in average $O(1)$ time. This reduces the time complexity to optimal $O(N)$ linear time.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to decide if string `t` is an anagram of string `s`. This requires verifying two conditions:
1. Both strings must have the exact same total number of characters.
2. Every distinct character must appear with the exact same frequency in both strings.

### Step 2: Identify the Key Observation

Character order does not matter for anagrams—only character count matters. Therefore, any transformation or data structure that captures frequency counts while ignoring positioning will suffice to solve the problem.

### Step 3: Recognize the Pattern

Because we need to map discrete elements (characters) to their occurrences (integers), a **HashMap** (or frequency array) is the ideal data structure. It allows dynamic insertion, lookup, and updates in $O(1)$ average time per operation.

### Step 4: Decide What Information We Need to Maintain

We need a frequency map `map` where:
- **Key**: `Character` from string `s`.
- **Value**: `Integer` representing the remaining available occurrences of that character.

### Step 5: Derive the Algorithm

1. Compare string lengths; exit early if they differ.
2. Populate the frequency map by traversing string `s`.
3. Traverse string `t`, decrementing counts in the map. If an impossible state occurs (missing key or count is 0), return `false`.
4. If the loop completes without issue, return `true`.

---

## 🔍 Algorithm

1. Check if `s.length() != t.length()`. If true, return `false`.
2. Initialize an empty `HashMap<Character, Integer>` named `map`.
3. Loop through index `i` from `0` to `s.length() - 1`:
   - Increment the count of `s.charAt(i)` in `map` using `getOrDefault`.
4. Loop through index `i` from `0` to `t.length() - 1`:
   - Retrieve character `c = t.charAt(i)`.
   - If `c` is not in `map` or `map.get(c) == 0`, return `false`.
   - Decrement the count of `c` in `map` by 1.
5. If the loop completes without returning `false`, return `true`.

### Important Implementation Details

- `map` → Stores character frequencies of string `s`.
- `s.length() != t.length()` → Early exit check to handle strings of unequal sizes instantly.
- `map.getOrDefault(s.charAt(i), 0) + 1` → Safely increments character count without throwing null pointer exceptions.
- `!map.containsKey(t.charAt(i)) || map.get(t.charAt(i)) == 0` → Guard condition detecting unexpected characters or frequency mismatches.

---

## 🧩 Understanding the Code

### Phase 1: Length Guard

```java
if (s.length() != t.length()) return false;
```
If two strings have different lengths, it is impossible for them to be anagrams of each other. Checking this upfront avoids unnecessary iteration and map population.

### Phase 2: Character Frequency Counting

```java
HashMap<Character,Integer> map = new HashMap<>();
for(int i = 0 ; i < s.length(); i++){
    map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
}
```
This loop builds the frequency dictionary for string `s`. Each character acts as a key, and its associated value tracks how many times it appears in `s`.

### Phase 3: Validation and Consumption

```java
for(int i = 0; i < t.length(); i++){
    if(!map.containsKey(t.charAt(i)) || map.get(t.charAt(i)) == 0){
        return false;
    }
    map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
}
return true;
```
This loop verifies that string `t` uses the exact same characters in the exact same quantities. For every character in `t`, it checks if there is a remaining instance available in `map`. If not, it fails early by returning `false`. Otherwise, it consumes one occurrence by decrementing the count in `map`.

---

## 🧠 Why This Works

The algorithm works because two strings of length $N$ have identical character counts if and only if string `t` can consume all frequencies provided by string `s` without running out of any character. 

Since the length check guarantees $|s| = |t| = N$, consuming $N$ characters from `t` without ever encountering a character count of zero guarantees that no character count remains greater than zero in `map`. Thus, the frequencies match perfectly.

### Key Invariant

During the second loop at index $i$, `map` holds the exact count of unconsumed characters from `s` after matching the prefix `t[0...i-1]`.

---

## ⏱️ Time Complexity

**Time:** `O(N)`

### Why?

- Length comparison `s.length() != t.length()` takes $O(1)$ time.
- The first loop runs $N$ times (where $N$ is the length of `s`). Each `charAt`, `getOrDefault`, and `put` operation on the `HashMap` runs in $O(1)$ average time.
- The second loop runs $N$ times (where $N$ is the length of `t`). Each `charAt`, `containsKey`, `get`, and `put` operation runs in $O(1)$ average time.
- Overall time complexity is $O(N) + O(N) = O(N)$.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(K)`

- The auxiliary space is determined by the `HashMap`, which stores up to $K$ distinct characters present in string `s`.
- If the input is restricted to lowercase English letters, $K \le 26$, making the space effectively $O(1)$.
- For arbitrary character sets (such as Unicode), space complexity is $O(K)$, where $K$ is the number of unique characters in `s`.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using a general-purpose `HashMap<Character, Integer>`, we can use a fixed-size integer array `int[26]` if the problem guarantees lowercase English alphabets. 

We can increment character counts for `s` and decrement character counts for `t` within a single loop. Finally, we check if all values in the array are zero.

### Complexity

**Time:** `O(N)`  
**Space:** `O(1)` (Fixed array of size 26)

### Comparison

| Aspect | Submitted Approach (`HashMap`) | Alternative Approach (`int[26]` Array) |
|---|---|---|
| Main Idea | Count frequencies using `HashMap` | Count frequencies using fixed array |
| Time | `O(N)` average | `O(N)` deterministic |
| Space | `O(K)` where $K$ is distinct chars | `O(1)` bound to 26 elements |
| Advantage | Handles full Unicode / arbitrary character sets natively | Lower overhead, no object boxing, faster in practice |

---

## 📌 Key Takeaways

- **Pattern:** Frequency Counting / Hash Map
- **Core Observation:** Two strings are anagrams if and only if they have equal lengths and identical character frequencies.
- **Important Data Structure:** `HashMap<Character, Integer>` (or `int[26]` array for fixed alphabets)
- **Time:** `O(N)`
- **Space:** `O(K)` where $K$ is the number of distinct characters

### Remember

> When order does not matter but character counts do, count frequencies using a hash map or fixed frequency array instead of sorting.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/valid-anagram/)
