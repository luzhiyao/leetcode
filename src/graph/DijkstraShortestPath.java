package src.graph;

import java.util.*;

/**
 * Dijkstra最短路径算法
 * 
 * 问题描述：
 * 给定一个带权有向图，找到从源节点到所有其他节点的最短路径。
 * Dijkstra算法适用于所有边的权重为非负数的图。
 * 
 * 时间复杂度：
 * - 使用优先队列：O(E log V)，其中E是边的数量，V是顶点的数量
 * - 使用邻接矩阵和数组：O(V^2)
 * 
 * 空间复杂度：O(V)
 */
public class DijkstraShortestPath {
    
    /**
     * 使用优先队列实现的Dijkstra算法
     * 
     * @param graph 邻接表表示的图，graph[i]是一个列表，包含从节点i出发的所有边
     * @param source 源节点
     * @param n 节点总数
     * @return 从源节点到各个节点的最短距离数组
     */
    public int[] dijkstraWithPriorityQueue(List<List<int[]>> graph, int source, int n) {
        // 存储源节点到所有节点的最短距离
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        
        // 使用优先队列（小顶堆）按距离排序节点
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{source, 0}); // [节点, 距离]
        
        // 存储节点是否已经处理
        boolean[] visited = new boolean[n];
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];
            
            // 如果节点已经处理过，或当前路径不是最短路径，则跳过
            if (visited[node] || distance > distances[node]) {
                continue;
            }
            
            // 标记节点为已处理
            visited[node] = true;
            
            // 遍历当前节点的邻居
            for (int[] edge : graph.get(node)) {
                int neighbor = edge[0];
                int weight = edge[1];
                
                // 更新距离如果找到更短的路径
                if (!visited[neighbor] && distances[node] + weight < distances[neighbor]) {
                    distances[neighbor] = distances[node] + weight;
                    pq.offer(new int[]{neighbor, distances[neighbor]});
                }
            }
        }
        
        return distances;
    }
    
    /**
     * 使用邻接矩阵实现的Dijkstra算法
     * 
     * @param graph 邻接矩阵表示的图，graph[i][j]表示从节点i到节点j的边的权重，不存在的边用Integer.MAX_VALUE表示
     * @param source 源节点
     * @return 从源节点到各个节点的最短距离数组
     */
    public int[] dijkstraWithMatrix(int[][] graph, int source) {
        int n = graph.length; // 节点数量
        
        // 存储源节点到所有节点的最短距离
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        
        // 存储节点是否已经处理
        boolean[] visited = new boolean[n];
        
        // 迭代n次，每次找到一个距离最近的未处理节点
        for (int i = 0; i < n; i++) {
            // 找到距离最近的未处理节点
            int minDistNode = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (minDistNode == -1 || distances[j] < distances[minDistNode])) {
                    minDistNode = j;
                }
            }
            
            // 如果找不到更近的节点，算法结束
            if (minDistNode == -1 || distances[minDistNode] == Integer.MAX_VALUE) {
                break;
            }
            
            // 标记节点为已处理
            visited[minDistNode] = true;
            
            // 更新通过当前节点可达的所有邻居的距离
            for (int j = 0; j < n; j++) {
                // 检查边是否存在
                if (graph[minDistNode][j] != Integer.MAX_VALUE) {
                    // 更新距离如果找到更短的路径
                    if (!visited[j] && distances[minDistNode] != Integer.MAX_VALUE
                            && distances[minDistNode] + graph[minDistNode][j] < distances[j]) {
                        distances[j] = distances[minDistNode] + graph[minDistNode][j];
                    }
                }
            }
        }
        
        return distances;
    }
    
    /**
     * 带路径记录的Dijkstra算法
     * 
     * @param graph 邻接表表示的图
     * @param source 源节点
     * @param n 节点总数
     * @return 包含距离和路径信息的对象
     */
    public PathInfo dijkstraWithPath(List<List<int[]>> graph, int source, int n) {
        // 存储源节点到所有节点的最短距离
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        
        // 存储前驱节点，用于重建路径
        int[] predecessors = new int[n];
        Arrays.fill(predecessors, -1);
        
        // 使用优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{source, 0});
        
        // 存储节点是否已经处理
        boolean[] visited = new boolean[n];
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];
            
            if (visited[node] || distance > distances[node]) {
                continue;
            }
            
            visited[node] = true;
            
            for (int[] edge : graph.get(node)) {
                int neighbor = edge[0];
                int weight = edge[1];
                
                if (!visited[neighbor] && distances[node] + weight < distances[neighbor]) {
                    distances[neighbor] = distances[node] + weight;
                    predecessors[neighbor] = node;
                    pq.offer(new int[]{neighbor, distances[neighbor]});
                }
            }
        }
        
        return new PathInfo(distances, predecessors);
    }
    
    /**
     * 内部类，存储Dijkstra算法的结果，包括距离和路径
     */
    public static class PathInfo {
        private final int[] distances;
        private final int[] predecessors;
        
        public PathInfo(int[] distances, int[] predecessors) {
            this.distances = distances;
            this.predecessors = predecessors;
        }
        
        /**
         * 获取从源节点到指定目标节点的最短距离
         */
        public int getDistance(int target) {
            return distances[target];
        }
        
        /**
         * 获取从源节点到指定目标节点的路径
         */
        public List<Integer> getPath(int target) {
            List<Integer> path = new ArrayList<>();
            for (int at = target; at != -1; at = predecessors[at]) {
                path.add(at);
            }
            Collections.reverse(path);
            return path;
        }
        
        /**
         * 检查从源节点到指定目标节点是否有路径
         */
        public boolean hasPath(int target) {
            return distances[target] != Integer.MAX_VALUE;
        }
    }
    
    /**
     * 构建邻接表表示的图
     */
    private static List<List<int[]>> buildAdjacencyList(int n, int[][] edges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            graph.get(from).add(new int[]{to, weight});
        }
        
        return graph;
    }
    
    /**
     * 构建邻接矩阵表示的图
     */
    private static int[][] buildAdjacencyMatrix(int n, int[][] edges) {
        int[][] graph = new int[n][n];
        
        // 初始化所有边为无穷大
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], Integer.MAX_VALUE);
            graph[i][i] = 0; // 自环为0
        }
        
        // 添加边
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            graph[from][to] = weight;
        }
        
        return graph;
    }
    
    /**
     * 打印距离和路径
     */
    private static void printResult(PathInfo pathInfo, int source, int n) {
        System.out.println("从节点 " + source + " 到各节点的最短路径：");
        for (int i = 0; i < n; i++) {
            if (i != source) {
                if (pathInfo.hasPath(i)) {
                    System.out.println("到节点 " + i + " 的距离: " + pathInfo.getDistance(i) + 
                                       ", 路径: " + pathInfo.getPath(i));
                } else {
                    System.out.println("到节点 " + i + " 无路径");
                }
            }
        }
    }
    
    // 测试方法
    public static void main(String[] args) {
        DijkstraShortestPath solution = new DijkstraShortestPath();
        
        // 测试用例1：简单有向图
        int n1 = 5;
        int[][] edges1 = {
            {0, 1, 10}, // 从节点0到节点1的边，权重为10
            {0, 4, 5},
            {1, 2, 1},
            {1, 4, 2},
            {2, 3, 4},
            {3, 2, 6},
            {3, 0, 7},
            {4, 1, 3},
            {4, 2, 9},
            {4, 3, 2}
        };
        
        System.out.println("测试用例1：简单有向图");
        
        // 使用邻接表和优先队列
        List<List<int[]>> graph1 = buildAdjacencyList(n1, edges1);
        int source1 = 0;
        int[] distances1 = solution.dijkstraWithPriorityQueue(graph1, source1, n1);
        
        System.out.println("使用优先队列的结果：");
        for (int i = 0; i < n1; i++) {
            System.out.println("从节点 " + source1 + " 到节点 " + i + " 的最短距离：" + 
                             (distances1[i] == Integer.MAX_VALUE ? "无法到达" : distances1[i]));
        }
        
        // 使用邻接矩阵
        int[][] matrixGraph1 = buildAdjacencyMatrix(n1, edges1);
        int[] matrixDistances1 = solution.dijkstraWithMatrix(matrixGraph1, source1);
        
        System.out.println("\n使用邻接矩阵的结果：");
        for (int i = 0; i < n1; i++) {
            System.out.println("从节点 " + source1 + " 到节点 " + i + " 的最短距离：" + 
                             (matrixDistances1[i] == Integer.MAX_VALUE ? "无法到达" : matrixDistances1[i]));
        }
        
        // 带路径记录的Dijkstra算法
        PathInfo pathInfo1 = solution.dijkstraWithPath(graph1, source1, n1);
        System.out.println("\n带路径记录的结果：");
        printResult(pathInfo1, source1, n1);
        
        // 测试用例2：不连通图
        int n2 = 4;
        int[][] edges2 = {
            {0, 1, 3},
            {1, 2, 5}
            // 节点3没有连接到任何节点
        };
        
        System.out.println("\n测试用例2：不连通图");
        
        List<List<int[]>> graph2 = buildAdjacencyList(n2, edges2);
        int source2 = 0;
        PathInfo pathInfo2 = solution.dijkstraWithPath(graph2, source2, n2);
        printResult(pathInfo2, source2, n2);
        
        // 测试用例3：负权边（Dijkstra不适用于负权边，这里仅作为演示）
        int n3 = 3;
        int[][] edges3 = {
            {0, 1, 5},
            {0, 2, 2},
            {1, 2, -3} // 负权边
        };
        
        System.out.println("\n测试用例3：含负权边的图（Dijkstra可能不正确）");
        
        List<List<int[]>> graph3 = buildAdjacencyList(n3, edges3);
        int source3 = 0;
        PathInfo pathInfo3 = solution.dijkstraWithPath(graph3, source3, n3);
        printResult(pathInfo3, source3, n3);
        
        // 测试用例4：复杂网络
        int n4 = 6;
        int[][] edges4 = {
            {0, 1, 7},
            {0, 2, 9},
            {0, 5, 14},
            {1, 2, 10},
            {1, 3, 15},
            {2, 3, 11},
            {2, 5, 2},
            {3, 4, 6},
            {4, 5, 9}
        };
        
        System.out.println("\n测试用例4：复杂网络");
        
        List<List<int[]>> graph4 = buildAdjacencyList(n4, edges4);
        int source4 = 0;
        PathInfo pathInfo4 = solution.dijkstraWithPath(graph4, source4, n4);
        printResult(pathInfo4, source4, n4);
    }
} 