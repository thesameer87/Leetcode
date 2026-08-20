# 383. Ransom Note

**Difficulty:** Easy  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution solves the Ransom Note problem using a frequency counting approach backed by a `HashMap`. The fundamental goal is to determine if the `ransomNote` string can be constructed using individual characters from the `magazine` string, where each character in `magazine` can only be used once.

To achieve this, the algorithm first iterates over the `magazine` string and builds a character frequency map. This map stores each distinct character as a key and its total occurrence count in `magazine` as the corresponding value. 

Next, the algorithm iterates through each character of `ransomNote`. For every character, it checks whether that character exists in the frequency map with a count strictly greater than zero. If it does, the count is decremented by 1 to represent using that character. If any character in `ransomNote` is missing or has a count of zero in the map, the function immediately returns `false`. If the entire `ransomNote` is processed successfully, the function returns `true`.

The primary DSA pattern utilized here is the **HashMap / Frequency Counting** pattern.

### Why This Approach?

When faced with matching character requirements between two strings where frequencies matter and order does not, we need a way to count and track available resources (characters from `magazine`) and consume them sequentially for demands (`ransomNote`).

A naive brute-force approach might search for each character of `ransomNote` in `magazine` one by one, removing or marking found characters in `magazine`. Searching and modifying a string repeatedly takes $O(N \cdot M)$ time, where $N$ is the length of `ransomNote` and $M$ is the length of `magazine`. 

By preprocessing `magazine` into a hash table, we can lookup and decrement character counts in $O(1)$ average time. This reduces the overall complexity to linear time, $O(N + M)$, making it significantly more efficient.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to check if `ransomNote` is a multiset subset of `magazine`. In plain terms: does `magazine` contain at least as many of each character as `ransomNote` requires?

### Step 2: Identify the Key Observation

Character order and positions in either string do not matter. The only factor determining validity is character counts. If `magazine` has 3 `'a'`s and `ransomNote` needs 2 `'a'`s, that is valid. If `ransomNote` needs 4 `'a'`s, it is invalid.

### Step 3: Recognize the Pattern

Whenever a problem asks to match items based on occurrence counts without regard to ordering, **Frequency Counting using a Hash Table or Array** is the standard pattern. Hash tables allow $O(1)$ updates and lookups for arbitrary character sets.

### Step 4: Decide What Information We Need to Maintain

We need a frequency map that records:
- Key: `Character` (each character from `magazine`)
- Value: `Integer` (the remaining available count of that character)

### Step 5: Derive the Algorithm

1. Traverse `magazine` and populate the frequency map.
2. Traverse `ransomNote`. For each character:
   - Check if the count in the frequency map is greater than `0`.
   - If not, return `false` immediately (insufficient characters).
   - If yes, decrement the character's count in the map by `1`.
3. If all characters in `ransomNote` are processed successfully, return `true`.

---

## 🔍 Algorithm

1. Initialize a `HashMap<Character, Integer>` named `set` to store character frequencies from `magazine`.
2. Iterate through each character of `magazine` from index `0` to `magazine.length() - 1`:
   - Increment its count in `set` using `getOrDefault(ch, 0) + 1`.
3. Iterate through each character of `ransomNote` from index `0` to `ransomNote.length() - 1`:
   - Retrieve the current count using `set.getOrDefault(ch, 0)`.
   - If the count is `0` (or character is absent), return `false`.
   - Otherwise, update the map by decrementing the character count by `1`.
4. Return `true` if the loop finishes without returning `false`.

### Important Implementation Details

- `HashMap<Character, Integer> set` → Stores available character inventory built from `magazine`.
- `set.getOrDefault(key, 0)` → Safely retrieves the count of a character, returning `0` if the character has not been stored in the map yet.
- `set.getOrDefault(ransomNote.charAt(i), 0) == 0` → Early termination condition when `ransomNote` demands a character that is unavailable or exhausted.

---

## 🧩 Understanding the Code

### Phase 1: Building the Frequency Map from `magazine`

```java
HashMap<Character, Integer> set = new HashMap<>();
for (int i = 0; i < magazine.length(); i++) {
    set.put(magazine.charAt(i), set.getOrDefault(magazine.charAt(i), 0) + 1);
}
```

This block loops over every character in `magazine`. For each character, `getOrDefault` checks if it already exists in `set`. If it exists, it returns its current count; otherwise, it returns `0`. The code adds `1` to this count and puts it back into `set`, effectively counting character occurrences.

### Phase 2: Consuming Characters for `ransomNote`

```java
for (int i = 0; i < ransomNote.length(); i++) {
    if (set.getOrDefault(ransomNote.charAt(i), 0) == 0) {
        return false;
    }
    set.put(ransomNote.charAt(i), set.get(ransomNote.charAt(i)) - 1);
}
return true;
```

This loop iterates through `ransomNote`. For each required character, it checks if there is at least one instance available in `set`. If `getOrDefault` returns `0`, it means `magazine` either never had this character or all instances have already been used up, so it returns `false`. Otherwise, it decrements the available count by `1`. If the loop completes, all required characters were successfully satisfied, so it returns `true`.

---

## 🧠 Why This Works

The algorithm guarantees correctness because it mirrors a real-world resource allocation process. `magazine` provides a pool of resources (letters), and `ransomNote` consumes them one by one. 

By checking the count before consuming each character, we ensure that no letter from `magazine` is reused more times than it actually appears. Checking each character of `ransomNote` sequentially ensures that if any single character requirement fails, the overall construction is deemed impossible immediately.

### Key Invariant

At any point during the second loop (iteration `i` over `ransomNote`), the `HashMap` accurately reflects the exact remaining count of each character from `magazine` after satisfying the first `i` characters of `ransomNote`.

---

## ⏱️ Time Complexity

**Time:** `O(m + n)`

where `m` is the length of `magazine` and `n` is the length of `ransomNote`.

### Why?

- Building the `HashMap` takes `O(m)` time because we iterate through all `m` characters of `magazine`, performing `O(1)` average time operations for hash map lookups and insertions.
- Validating against `ransomNote` takes `O(n)` time because we iterate through at most `n` characters of `ransomNote`, performing `O(1)` average time hash map lookups and updates per character.
- Total time complexity is `O(m + n)`.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(k)`

where `k` is the number of unique characters in `magazine`.

- The `HashMap` stores up to `k` unique characters.
- If the input consists only of lowercase English letters, `k <= 26`, making the space effectively `O(1)`.
- If the character set is arbitrary (e.g., Unicode), the space is upper-bounded by `O(k)`, where `k <= m`.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using a generic `HashMap`, we can use a fixed-size integer array `int[26]` as a direct-mapped frequency table, assuming the problem input consists only of lowercase English letters `'a'` through `'z'`. 

Each character `'a'` through `'z'` maps directly to array index `ch - 'a'`. This eliminates object overhead (autoboxing `char` to `Character` and `int` to `Integer`), avoids hash collision handling, and reduces space usage to a tiny fixed-size memory footprint.

### Complexity

**Time:** `O(m + n)`  
**Space:** `O(1)` (fixed size array of 26 integers)

### Comparison

| Aspect | Submitted Approach (`HashMap`) | Alternative Approach (`int[26]` Array) |
|---|---|---|
| Main Idea | Dynamic key-value mapping | Fixed-size index mapping (`ch - 'a'`) |
| Time | `O(m + n)` | `O(m + n)` |
| Space | `O(k)` (up to 26 for English letters) | `O(1)` (fixed 26-element array) |
| Advantage | Works for any character set (e.g., Unicode, ASCII) | Lower cache miss rate, no primitive boxing overhead |

---

## 📌 Key Takeaways

- **Pattern:** Frequency Map / Hash Table
- **Core Observation:** Construction depends solely on character availability counts, independent of character positions.
- **Important Data Structure:** `HashMap<Character, Integer>` (or `int[26]` for lowercase alphabet constraint)
- **Time:** `O(m + n)`
- **Space:** `O(k)` where `k` is the number of unique characters

### Remember

> When checking if one string can be constructed from another without positional constraints, count character frequencies first, then consume them while validating availability.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/ransom-note/)
