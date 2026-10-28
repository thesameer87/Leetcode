# 1148. Article Views I

**Difficulty:** Easy  
**Language:** MySQL

---

## 🧠 Approach

### Core Idea

The goal of this query is to find all authors who viewed at least one of their own articles. In the `Views` table, each row records a view event with an `author_id` (the creator of the article) and a `viewer_id` (the user who viewed the article).

When an author views their own article, the `author_id` and `viewer_id` in that row are identical. Therefore, filtering the table for rows where `author_id = viewer_id` yields all self-viewing instances. Since an author might view their own articles multiple times, using `DISTINCT` ensures each author appears only once in the final result. Sorting by `author_id ASC` produces the required ascending order.

The main database filtering pattern used here is predicate filtering (`WHERE`) combined with result set deduplication (`DISTINCT`) and explicit sorting (`ORDER BY`).

### Why This Approach?

To extract specific records from a relational table based on field comparison, we evaluate row-level equality between two columns. 

Rather than scanning all records manually or performing unnecessary self-joins, filtering directly in the `WHERE` clause allows the database engine to inspect each row in a single pass. Once matching rows are identified, deduplication handles duplicate views, and sorting fulfills the output ordering requirement.

---

## 💡 How to Think About the Problem

### Step 1: Understand What We Need

We need to identify all distinct authors who have read their own articles. The output column must be named `id` and sorted in ascending order.

### Step 2: Identify the Key Observation

An author views their own article if and only if `author_id` is equal to `viewer_id` in a given row.

### Step 3: Recognize the Pattern

This problem fits the Relational Filtering and Deduplication pattern:
- **Filtering (`WHERE`):** Selecting rows matching a conditional relationship across columns within the same row.
- **Deduplication (`DISTINCT`):** Eliminating duplicate values when entities appear multiple times across rows.
- **Sorting (`ORDER BY`):** Ordering the output deterministically.

### Step 4: Decide What Information We Need to Maintain

We only need the `author_id` column from matching rows, renamed to `id`.

### Step 5: Derive the Algorithm

1. Scan the `Views` table.
2. Filter for rows where `author_id = viewer_id`.
3. Project the `author_id` column as `id`.
4. Remove duplicate author IDs using `DISTINCT`.
5. Sort the resulting list of IDs in ascending order.

---

## 🔍 Algorithm

1. **Filtering:** Iterate through records in `Views` and select rows where `author_id` equals `viewer_id`.
2. **Projection & Renaming:** Select `author_id` and alias it as `id`.
3. **Deduplication:** Apply `DISTINCT` to ensure duplicate author entries are collapsed into a single instance.
4. **Ordering:** Sort the output set by `author_id` in ascending order (`ASC`).

### Important Implementation Details

- `author_id = viewer_id` → Filter condition to detect self-views.
- `DISTINCT author_id` → Removes duplicate entries for authors with multiple self-views.
- `AS id` → Aliases the output column name to match the target schema requirement.
- `ORDER BY author_id ASC` → Guarantees the output is sorted in ascending order.

---

## 🧩 Understanding the Code

### Filtering and Column Selection

```sql
Select DISTINCT author_id AS id from Views AS id
WHERE author_id = viewer_id
```

This block extracts rows from the `Views` table where the author is also the viewer (`author_id = viewer_id`). The `DISTINCT` keyword prevents the same author from appearing multiple times if they viewed their own articles repeatedly. The column `author_id` is renamed to `id` as requested. (Note: `Views AS id` gives the table alias `id`, though filtering relies directly on column names).

### Sorting Results

```sql
ORDER BY author_id ASC
```

This clause sorts the deduplicated author IDs in ascending numerical order before returning the final result set.

---

## 🧠 Why This Works

The query correctly evaluates row-level conditions. Every row where an author read their own work satisfies `author_id = viewer_id`. `DISTINCT` processes the candidate set to discard identical IDs, guaranteeing uniqueness. Finally, `ORDER BY` imposes the specified sort sequence.

### Key Invariant

The output set contains only unique `author_id` values from rows where `author_id = viewer_id`, and all values are strictly ordered in ascending sequence.

---

## ⏱️ Time Complexity

**Time:** `O(N \log N)`

### Why?

Filtering rows where `author_id = viewer_id` takes `O(N)` time, where `N` is the total number of rows in the `Views` table. Sorting the filtered results (and performing deduplication via sorting or hashing) takes `O(K \log K)` time, where `K` is the number of matching rows (`K ≤ N`). Thus, the overall time complexity is bounded by `O(N \log N)`.

---

## 💾 Space Complexity

**Auxiliary Space:** `O(K)`

The database engine requires `O(K)` space to buffer and deduplicate/sort the `K` matching rows that satisfy the `WHERE` clause before returning the final dataset.

---

## 🔄 Alternative Approach

### Alternative Idea

Instead of using `DISTINCT`, we can group the filtered rows by `author_id` using `GROUP BY`. Grouping naturally collapses duplicate author IDs.

```sql
SELECT author_id AS id
FROM Views
WHERE author_id = viewer_id
GROUP BY author_id
ORDER BY id ASC;
```

### Complexity

**Time:** `O(N \log N)`  
**Space:** `O(K)`

### Comparison

| Aspect | Submitted Approach (`DISTINCT`) | Alternative (`GROUP BY`) |
|---|---|---|
| Main Idea | Deduplicate using `DISTINCT` keyword | Deduplicate using `GROUP BY` aggregation clause |
| Time | `O(N \log N)` | `O(N \log N)` |
| Space | `O(K)` | `O(K)` |
| Advantage | Concise and standard for simple deduplication | Flexible if aggregate metrics (e.g., view count) are added later |

---

## 📌 Key Takeaways

- **Pattern:** Row-level Filtering & Deduplication (`WHERE` + `DISTINCT` / `GROUP BY`).
- **Core Observation:** A self-view occurs when `author_id = viewer_id` in the same record.
- **Important Data Structure:** Relational Table / Hash or B-Tree index for sorting/deduplication.
- **Time:** `O(N \log N)`
- **Space:** `O(K)`

### Remember

> When comparing two attributes of the same entity within a single record, filter directly in the `WHERE` clause using column equality (`colA = colB`), and use `DISTINCT` or `GROUP BY` to handle duplicate matches.

---

## 🔗 Problem

[LeetCode Problem](https://leetcode.com/problems/article-views-i/)
