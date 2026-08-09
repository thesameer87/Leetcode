# 1140. Stone Game II

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution uses **Top-Down Dynamic Programming with Memoization** based on a **Minimax Game Theory** framework. In this two-player game, Alice and Bob take turns picking $x$ piles of stones from the current position $i$, where $1 \le x \le 2M$. When a player picks $x$ piles, $M$ is updated to $\max(M, x)$. Since both players play optimally, Alice tries to maximize her total stones collected, while Bob tries to minimize Alice's total stones (which is equivalent to Bob maximizing his own score, as the sum of all stones in the remaining piles is fixed).

To represent this game, the solution models the state with three parameters: `person` (indicating whose turn it is: `1` for Alice, `0` for Bob), `i` (the current pile index), and `m` (the current limit parameter $M$). At each step, the current player simulates picking $x$ piles for all valid $1 \le x \le \min(2M, N - i)$. If it is Alice's turn, she adds the stones collected in those $x$ piles to her total score and chooses the move that maximizes her overall payout. If it is Bob's turn, Bob makes the move that minimizes Alice's total score from that point forward.

The main DSA pattern used here is **Dynamic Programming (Minimax Game Theory)**.

### Why This Approach?

When considering how to solve game theory problems with choices that depend on previous choices, a brute-force recursive simulation attempts every valid value of $x$ at every stage. However, at each step, there are up to $2M$ branches, leading to an exponential number of game paths ($O((2M)^N)$). 

Notice that many recursive calls evaluate the exact same game situation: given the same pile index $i$, the same parameter $M$, and the same player's turn `person`, the maximum stones Alice can guarantee from that position onward will always be identical. By memoizing the results of `solve(person, i, m)` in a 3D array `dp[2][101][101]`, we eliminate redundant work and reduce the state space to a polynomial size.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to compute the maximum number of stones Alice can collect assuming both Alice and Bob play with an optimal strategy, starting with $i = 0$ and $M = 1$.

### Step 2: Identify the Key Observation

Stone Game II is a zero-sum game played sequentially. Because all stones are eventually split between Alice and Bob, Alice's optimal strategy is to maximize her stone count. Bob's optimal strategy is to maximize his own stone count, which directly minimizes Alice's score from his turn onwards.

### Step 3: Recognize the Pattern

Because the game state depends only on the remaining piles (starting at index $i$), the current parameter $M$, and whose turn it is (`person`), this problem naturally breaks down into overlapping subproblems with optimal substructure—the hallmark of **Dynamic Programming**.

### Step 4: Decide What Information We Need to Maintain

We need to track:
1. `person`: `1` if it is Alice's turn, `0` if it is Bob's turn.
2. `i`: The index of the first pile available to be picked.
3. `m`: The current parameter $M$, which determines the maximum piles a player can pick ($2M$).

### Step 5: Derive the Algorithm

From state `(person, i, m)`:
- Iterate $x$ from $1$ to $\min(2M, N - i)$.
- Calculate the stones taken in this move: `stone = sum(piles[i ... i + x - 1])`.
- If `person == 1` (Alice):
  - Next state is `solve(0, i + x, max(m, x))`.
  - Alice's total = `stone` + optimal outcome from Bob's turn.
  - Alice chooses $x$ to **maximize** this sum.
- If `person == 0` (Bob):
  - Next state is `solve(1, i + x, max(m, x))`.
  - Bob adds no stones to Alice's score, so Alice gets `solve(1, i + x, max(m, x))`.
  - Bob chooses $x$ to **minimize** Alice's score.

---

## 🔍 Algorithm

1. Initialize `dp[2][101][101]` array with `-1` to represent uncalculated states.
2. Call `solve(piles, person = 1, i = 0, m = 1)` to start the recursion with Alice.
3. In `solve(piles, person, i, m)`:
   - **Base Case:** If $i \ge N$, return `0` (no piles remaining).
   - **Memoization Check:** If `dp[person][i][m] != -1`, return `dp[person][i][m]`.
   - **Initialize local result:** If `person == 1`, set `result = -1` (maximizing). If `person == 0`, set `result = Integer.MAX_VALUE` (minimizing).
   - **Loop through choices:** For $x$ from $1$ to $\min(2M, N - i)$:
     - Accumulate `stone += piles[i + x - 1]`.
     - If `person == 1`: `result = max(result, stone + solve(piles, 0, i + x, max(m, x)))`.
     - If `person == 0`: `result = min(result, solve(piles, 1, i + x, max(m, x)))`.
   - **Store and return:** Set `dp[person][i][m] = result` and return it.

### Important Implementation Details

- `dp[2][101][101]` → Memoization table storing the outcome for each `(person, i, m)` combination.
- `person` → `1` for Alice (maximizer), `0` for Bob (minimizer).
- `Math.min(2 * m, n - i)` → Ensures players cannot pick more than $2M$ piles or more piles than are left.
- `Math.max(m, x)` → Updates the new $M$ parameter for the next turn according to game rules.

---

## 🧩 Understanding the Code

### Initialization & Entry Point

```java
public int stoneGameII(int[] piles) {
    n = piles.length;
    for (int i = 0; i < 2; i++)
        for (int j = 0; j < 101; j++)
            Arrays.fill(dp[i][j], -1);

    return solve(piles, 1, 0, 1);
}
```
This phase sets up the memoization grid `dp` by filling it with `-1` to represent uncomputed game states. It then kicks off the game logic starting at index `0` with $M = 1$ on Alice's turn (`person = 1`).

### Base Case and Memoization Lookup

```java
if (i >= n)
    return 0;

if (dp[person][i][m] != -1) {
    return dp[person][i][m];
}
```
If index `i` reaches or exceeds `n`, no more stones remain, returning `0`. If the state `(person, i, m)` has already been solved, the function immediately returns the memoized answer to avoid exponential recomputation.

### Minimax Choice Loop

```java
for (int x = 1; x <= Math.min(2 * m, n - i); x++) {
    stone += piles[i + x - 1];

    if (person == 1) {
        result = Math.max(result, stone + solve(piles, 0, i + x, Math.max(m, x)));
    } else {
        result = Math.min(result, solve(piles, 1, i + x, Math.max(m, x)));
    }
}
return dp[person][i][m] = result;
```
This block explores all possible choices of $x$ piles ($1 \le x \le 2M$). `stone` keeps a running prefix sum of the stones acquired in the current move. If it's Alice's turn (`person == 1`), she collects `stone` and maximizes her cumulative total. If it's Bob's turn (`person == 0`), Bob makes a choice that minimizes Alice's score from the remaining piles. Finally, the optimal result is cached in `dp[person][i][m]` and returned.

---

## 🧠 Why This Works

The algorithm correctly models optimal play by exploring all valid moves for both players and selecting the strategy that aligns with each player's objective (Alice maximizing, Bob minimizing Alice's gain). Since dynamic programming computes the optimal payoff for every subproblem reachable in the game, and both players make globally optimal choices at each turn, the resulting DP value for `solve(1, 0, 1)` guarantees the exact number of stones Alice can get under optimal play.

### Key Invariant

For any state `(person, i, m)`, `dp[person][i][m]` represents the maximum stones Alice can collect from pile index `i` to `n - 1`, given parameter `m` and player `person`, assuming both players play optimally from that state onward.

### DP Explanation

**State:**
`dp[person][i][m]` = Maximum number of stones Alice can guarantee receiving from the suffix `piles[i ... n-1]` when the current multiplier parameter is `m` and it is `person`'s turn (`1` for Alice, `0` for Bob).

**Transition:**
- If `person == 1` (Alice):
  $$\text{dp}[1][i][m] = \max_{1 \le x \le \min(2m, n-i)} \left( \sum_{k=i}^{i+x-1} \text{piles}[k] + \text{dp}[0][i+x][\max(m, x)] \right)$$
- If `person == 0` (Bob):
  $$\text{dp}[0][i][m] = \min_{1 \le x \le \min(2m, n-i)} \left( \text{dp}[1][i+x][\max(m, x)] \right)$$

**Base Case:**
`solve(person, i, m) = 0` when $i \ge n$, because no piles remain to be taken.

**Order of Computation:**
Computed top-down starting from `(1, 0, 1)` using recursion and memoization. States are evaluated on-demand and stored upon completion.

**Memoization vs Tabulation:**
The code uses **Top-Down Dynamic Programming with Memoization**. When a state `(person, i, m)` is encountered for the first time, its calculated optimal value is assigned to `dp[person][i][m]` and reused whenever the recursive tree revisits the same state.

---

## ⏱️ Time Complexity

**Time:** `O(N^3)`

### Why?

- The state space is bounded by the dimensions of `dp`: `person` has 2 values, `i` goes from `0` to $N$, and `m` goes from `1` to $N$. Thus, there are $2 \times N \times N = O(N^2)$ distinct states.
- For each state, the `for` loop iterates $x$ from $1$ to $\min(2M, N - i)$, which takes $O(N)$ operations in the worst case.
- Multiplying the number of states $O(N^2)$ by the transition work per state $O(N)$ gives an overall time complexity of $O(N^3)$. With $N \le 100$, $N^3 = 10^6$, which easily runs within the time limit.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(N^2)`

- The DP table `dp[2][101][101]` requires $2 \times 101 \times 101 \approx 20,200$ integer entries, which takes $O(N^2)$ space.
- The maximum recursion stack depth is $O(N)$, because index `i` increases by at least $1$ in each recursive step.
- Total auxiliary space is $O(N^2) + O(N) = O(N^2)$.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using an explicit `person` parameter to distinguish Alice's turn from Bob's turn, we can eliminate `person` entirely by using **Suffix Sums**. 

In a zero-sum game, the total stones remaining from index `i` to `n - 1` is $\text{suffixSum}[i]$. If the current player picks $x$ piles, the opponent gets $\text{solve}(i + x, \max(m, x))$ from the remaining piles. Thus, the current player's total stones from index $i$ is:
$$\text{suffixSum}[i] - \text{solve}(i + x, \max(m, x))$$

Since both Alice and Bob are trying to maximize their own stone counts, the function simply returns the maximum score the *current player* can get from index `i` with parameter `m`.

### Complexity

**Time:** `O(N^3)`  
**Space:** `O(N^2)`

### Comparison

| Aspect | Submitted Approach | Alternative (Suffix Sum DP) |
|---|---|---|
| Main Idea | Minimax with explicit `person` turn parameter | Maximize current player's score using `suffixSum[i] - solve(...)` |
| State Space | `dp[2][N][N]` (20,000 states) | `dp[N][N]` (10,000 states) |
| Time | `O(N^3)` | `O(N^3)` |
| Space | `O(N^2)` | `O(N^2)` |
| Advantage | Explicitly tracks Alice vs Bob turns clearly | Simpler state representation, half the DP state table size |

---

## 📌 Key Takeaways

- **Pattern:** Dynamic Programming / Game Theory (Minimax)
- **Core Observation:** Explicit turn tracking (`person`) allows Alice to maximize her total score while Bob minimizes Alice's score directly.
- **Important Data Structure:** 3D array `dp[person][i][m]` for memoization.
- **Time:** `O(N^3)`
- **Space:** `O(N^2)`

### Remember

> In two-player turn-based optimal play games, if tracking both players' scores is required, you can either explicitly track the active player's turn to switch between maximizing and minimizing, or use total remaining stones minus the opponent's optimal score to simplify the DP state.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/stone-game-ii/)
