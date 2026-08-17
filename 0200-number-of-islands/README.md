# 200. Number of Islands

**Difficulty:** Medium  
**Language:** Java

---

## 🧠 Approach

### Core Idea

The submitted solution solves the problem by treating the 2D grid as an undirected graph where each cell containing `'1'` is a node, and edges exist between orthogonally adjacent land cells. The objective is to count the total number of connected components of land cells in this graph.

The algorithm iterates through every cell in the grid. When it encounters an unvisited land cell (`'1'`), it signifies the discovery of a new island. The island count is incremented, and a Breadth-First Search (BFS) traversal is triggered from that cell to explore and mark all reachable land cells belonging to the same island.

To avoid revisiting cells and falling into infinite cycles, the solution maintains a global `visited` set storing coordinate strings (`"r,c"`). By the end of the full grid iteration, every land cell will have been visited as part of its corresponding island's BFS exploration.

**Main DSA Pattern:** Graph Traversal / Breadth-First Search (BFS) on a 2D Grid using a Queue and a Hash Set.

### Why This Approach?

When faced with finding connected regions in a grid, we need a way to group adjacent cells together. Looking at an individual land cell in isolation does not tell us which island it belongs to; we must explore its neighbors recursively or iteratively.

A naive approach might try to re-scan the grid repeatedly or merge regions naively without tracking state, leading to redundant scans or incorrectly splitting connected landmasses. By systematically launching a traversal (BFS or DFS) every time an unvisited land cell is found, we guarantee that:
1. Every cell in a single connected component is visited in one traversal standard pass.
2. The number of times a new traversal is started equals the exact number of isolated islands in the grid.

BFS is an intuitive choice here because it explores cells level-by-level radiating outward from the starting land cell.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to calculate the total number of distinct islands in a given 2D grid. An island is defined as a maximal group of adjacent `'1'`s connected vertically or horizontally, surrounded by water (`'0'`) or the grid boundary.

### Step 2: Identify the Key Observation

If two land cells (`'1'`) are adjacent horizontally or vertically, they belong to the same island. Consequently, an entire island forms a single connected component. If we start at any cell of an island and walk to all accessible neighbor land cells, we will eventually cover every cell of that island and no cells of any other island.

### Step 3: Recognize the Pattern

This problem maps directly to finding the **Number of Connected Components in an Undirected Graph**.
- **Nodes:** Cells in the matrix with value `'1'`.
- **Edges:** Implicit orthogonal adjacencies (up, down, left, right) between land cells.
- **Traversal Strategy:** BFS using a queue to visit all connected land nodes starting from an unvisited root.

### Step 4: Decide What Information We Need to Maintain

- `islands`: An integer counter tracking the total number of islands discovered.
- `visited`: A data structure (here, `Set<String>`) storing string representations of coordinates (`"row,col"`) to track which cells have already been processed.
- `q`: A queue storing coordinate pairs (`int[]`) to control the BFS execution order.
- `directions`: An array of offset vectors `{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}` to succinctly check the 4 orthogonal neighbors of any cell.

### Step 5: Derive the Algorithm

1. Loop through every row `r` and column `c` of the grid.
2. If `grid[r][c] == '1'` and `"r,c"` is not in `visited`:
   - Increment `islands` by 1.
   - Start BFS from `(r, c)`:
     - Mark `(r, c)` as visited and add it to the queue.
     - While queue is not empty, poll a cell and check its 4 neighbors.
     - For each valid neighbor that is `'1'` and unvisited, mark it visited and push it to the queue.
3. Return `islands` after all grid cells have been scanned.

---

## 🔍 Algorithm

1. Initialize `islands = 0`, get `rows` and `cols` dimensions, and instantiate the `visited` hash set and `directions` array.
2. Loop `r` from `0` to `rows - 1` and `c` from `0` to `cols - 1`:
   - Check if `grid[r][c] == '1'` and `r + "," + c` is not present in `visited`.
   - If true, increment `islands++` and call `bfs(grid, r, c, visited, directions, rows, cols)`.
3. In `bfs`:
   - Initialize a queue `q` containing the starting coordinate `new int[]{r, c}` and add `"r,c"` to `visited`.
   - While `q` is not empty:
     - Poll `point` from `q` extract `row = point[0]` and `col = point[1]`.
     - Iterate through each vector in `directions`:
       - Calculate neighbor coordinates `nr = row + direction[0]` and `nc = col + direction[1]`.
       - Check if `(nr, nc)` is within bounds, equals `'1'`, and `"nr,nc"` is not in `visited`.
       - If valid, add `(nr, nc)` to `q` and insert `"nr,nc"` into `visited`.
4. Return `islands`.

### Important Implementation Details

- `visited` → A `Set<String>` storing string formatted representations `"r,c"` of coordinates to keep track of processed cells.
- `directions` → `int[][]` containing standard offset pairs to facilitate 4-directional grid moves without duplicating boundary check logic.
- `q` → A `LinkedList` operating as a Queue of `int[]` coordinate pairs for managing level-order traversal.
- `!visited.contains(nr + "," + nc)` → Pre-enqueue check ensuring each neighbor cell is added to the queue at most once, preventing duplicate work and cycles.

---

## 🧩 Understanding the Code

### Outer Loop Grid Scanning

```java
for (int r = 0; r < rows; r++) {
    for (int c = 0; c < cols; c++) {
        if (grid[r][c] == '1' && !visited.contains(r + "," + c)) {
            islands++;
            bfs(grid, r, c, visited, directions, rows, cols);
        }
    }
}
```

This nested loop scans every coordinate in the grid sequentially. The conditional statement filters out water cells (`'0'`) and land cells that were already visited during a previous BFS run. Encountering an unvisited land cell signals a new connected component (island), triggering an increment of `islands` and launching `bfs`.

### Queue Initialization and BFS Setup

```java
Queue<int[]> q = new LinkedList<>();
visited.add(r + "," + c);
q.add(new int[]{r, c});
```

This marks the entry point of the BFS call. The starting land cell `(r, c)` is added to the `visited` set immediately upon enqueueing to prevent any other path from adding it again.

### BFS Queue Expansion and Neighbor Checks

```java
while (!q.isEmpty()) {
    int[] point = q.poll();
    int row = point[0], col = point[1];

    for (int[] direction : directions) {
        int nr = row + direction[0], nc = col + direction[1];
        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1' && !visited.contains(nr + "," + nc)) {
            q.add(new int[]{nr, nc});
            visited.add(nr + "," + nc);
        }
    }
}
```

This loop processes cells layer by layer. For each popped cell `(row, col)`, all 4 orthogonal directions are examined. Boundary checks (`nr >= 0 && nr < rows && nc >= 0 && nc < cols`) ensure safe grid indexing. If a valid, unvisited land cell is found, it is enqueued and immediately marked in `visited`.

---

## 🧠 Why This Works

The grid can be decomposed into disjoint connected components of `'1'`s. BFS guarantees that starting from any node in a component, it will traverse every reachable node in that specific component before terminating.

Since every land cell visited in a BFS call is marked in `visited`, subsequent iterations of the main loop will skip all cells belonging to previously visited islands. Thus, the outer loop will trigger `bfs` exact $K$ times, where $K$ is the number of disconnected components (islands).

### Key Invariant

At any point during execution, any coordinate in `visited` belongs to an island that has already been accounted for in the total `islands` count, ensuring no land cell is double-counted across different islands.

---

## ⏱️ Time Complexity

**Time:** `O(M × N)`

where $M$ is the number of rows and $N$ is the number of columns in the grid.

### Why?

- The outer nested loops check every cell in the grid exactly once, contributing $O(M \times N)$ operations.
- Each land cell `'1'` is added to the `visited` set and enqueued into `q` at most once across the entire runtime.
- For each cell dequeued during BFS, we inspect a constant 4 directions.
- String concatenation and set lookup operations (`"r,c"`) run in $O(1)$ average time given the maximum string lengths for standard grid bounds.
- Therefore, the total time spent traversing nodes and checking bounds scales linearly with the total number of cells in the grid.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(M × N)`

### Why?

- **Visited Set:** In the worst-case scenario (e.g., the grid is completely filled with `'1'`s), the `visited` set will store coordinate strings for all $M \times N$ cells, requiring $O(M \times N)$ memory.
- **BFS Queue:** The queue `q` can store up to $O(\min(M, N))$ coordinates in a typical grid breadth-first expansion, but worst-case memory across the set dominates at $O(M \times N)$.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using an external `Set<String>` to track visited cells (which incurs overhead from string formatting and hash set storage), we can modify the input grid in-place (if allowed) using **Depth-First Search (DFS) with Grid Mutation**.

When an unvisited land cell `'1'` is found, we mutate it directly to `'0'` (sinking the island) and recursively trigger DFS on all 4 directions. Converting visited land cells to `'0'` eliminates the need for an explicit `visited` data structure.

### Complexity

**Time:** `O(M × N)`  
**Space:** `O(M × N)` worst-case stack space for recursion (or $O(1)$ auxiliary space if not counting call stack).

### Comparison

| Aspect | Submitted Approach | Alternative (In-Place DFS) |
|---|---|---|
| Main Idea | BFS with explicit `HashSet<String>` | DFS with in-place grid modification (`'1'` $\rightarrow$ `'0'`) |
| Time | `O(M * N)` | `O(M * N)` |
| Space | `O(M * N)` (Queue + HashSet) | `O(M * N)` (Recursion call stack worst case) |
| Advantage | Preserves original grid intact | Cleaner code, lower memory footprint, no string formatting |

---

## 📌 Key Takeaways

- **Pattern:** BFS on a 2D Grid / Connected Components in Graph
- **Core Observation:** Traversing all reachable nodes from an unvisited land cell marks one complete island; the number of traversals launched equals the total island count.
- **Important Data Structure:** `Queue<int[]>` for BFS order and `Set<String>` for visited tracking.
- **Time:** `O(M × N)`
- **Space:** `O(M × N)`

### Remember

> When counting connected components on a grid, mark cells as visited *immediately upon enqueueing or visiting* to avoid redundant queue additions and infinite loops.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/number-of-islands/)
