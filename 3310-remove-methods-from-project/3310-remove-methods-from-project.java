class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        //Adjacency List bnaaa Visited not visited k liye
         List<List<Integer>> adj = new ArrayList<>(); 
        for(int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        int[] inDegree = new int[n];        //O(V)
        boolean[] suspicious = new boolean[n]; //O(V)

        //Indegree of all nodes
        for(int[] edge : invocations) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            inDegree[v]++;
        }

        //BFS lgaya
        Queue<Integer> que = new LinkedList<>();
        que.offer(k);
        suspicious[k] = true;

        while(!que.isEmpty()) {
            int curr = que.poll();

            for(int ngbr : adj.get(curr)) {
                inDegree[ngbr]--;
                if(!suspicious[ngbr]) {
                    que.offer(ngbr);
                    suspicious[ngbr] = true;
                }
            }
        }

         List<Integer> result = new ArrayList<>();
        boolean cannotRemove = false;
        //A group of methods can only be removed if no method outside the group invokes any methods within it.
        // yeh wala condition chechk kra

        for(int i = 0; i < n; i++) {
            if(suspicious[i] && inDegree[i] > 0) {
                cannotRemove = true;
                break;
            }

            if(!suspicious[i]) {
                result.add(i);
            }
        }

        // Agar htana possible nhi hai toh 

        if(cannotRemove) {
            List<Integer> vec = new ArrayList<>(); //0, 1, 2,... n-1
            for(int i = 0; i < n; i++) {
                vec.add(i);
            }
            return vec;
        }

        return result;
    }
}