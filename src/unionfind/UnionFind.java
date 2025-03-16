package src.unionfind;

/**
 * 并查集 (Union-Find) 实现
 * 
 * 问题描述：
 * 并查集是一种数据结构，用于高效地解决动态连通性问题。它支持以下操作：
 * 1. 查找(Find)：确定元素属于哪一个子集
 * 2. 合并(Union)：将两个子集合并成一个子集
 * 
 * 应用：
 * 1. 检测图中的环
 * 2. 最小生成树算法（如Kruskal算法）
 * 3. 判断网络连接状态
 * 4. 求连通分量
 * 5. 集合合并与查询
 * 
 * 时间复杂度：
 * - 初始化：O(n)
 * - Find：近乎 O(1) (路径压缩后的均摊复杂度)
 * - Union：近乎 O(1) (按秩合并+路径压缩后的均摊复杂度)
 * 
 * 空间复杂度：O(n)
 */
public class UnionFind {
    
    // 父节点数组
    private int[] parent;
    // 秩数组（代表以当前节点为根的树的高度上限）
    private int[] rank;
    // 连通分量的数量
    private int count;
    
    /**
     * 初始化并查集
     * 
     * @param n 元素个数
     */
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        
        // 初始时，每个元素的父节点是自己
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0; // 初始秩为0
        }
    }
    
    /**
     * 查找元素x所属的集合（即根节点）
     * 使用路径压缩优化，将查找路径上的所有节点直接连接到根节点
     * 
     * @param x 要查找的元素
     * @return 元素x所属集合的根节点
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // 路径压缩
        }
        return parent[x];
    }
    
    /**
     * 合并元素x和元素y所在的集合
     * 使用按秩合并优化，总是将较小的树连接到较大的树上
     * 
     * @param x 第一个元素
     * @param y 第二个元素
     * @return 如果x和y原本不在同一集合中，则返回true；否则返回false
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) {
            // x和y已经在同一个集合中
            return false;
        }
        
        // 按秩合并，将较小的树连接到较大的树上
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            // 如果两棵树的秩相同，则将y的根节点连接到x的根节点，并增加x的秩
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        
        // 合并集合后，连通分量数减1
        count--;
        return true;
    }
    
    /**
     * 判断元素x和元素y是否属于同一个集合
     * 
     * @param x 第一个元素
     * @param y 第二个元素
     * @return 如果x和y在同一个集合中，则返回true；否则返回false
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    /**
     * 获取连通分量的数量
     * 
     * @return 连通分量的数量
     */
    public int getCount() {
        return count;
    }
    
    /**
     * 获取元素x所在集合的大小
     * 
     * @param x 元素
     * @return 元素x所在集合的大小
     */
    public int getSize(int x) {
        int root = find(x);
        int size = 0;
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == root) {
                size++;
            }
        }
        return size;
    }
    
    /**
     * 获取所有集合的大小
     * 
     * @return 包含所有集合大小的数组
     */
    public java.util.Map<Integer, Integer> getAllSetSizes() {
        java.util.Map<Integer, Integer> sizes = new java.util.HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            sizes.put(root, sizes.getOrDefault(root, 0) + 1);
        }
        return sizes;
    }
    
    /**
     * 重置并查集为初始状态
     */
    public void reset() {
        count = parent.length;
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    // 测试代码
    public static void main(String[] args) {
        int n = 10;
        UnionFind uf = new UnionFind(n);
        
        // 测试连接
        System.out.println("=== 测试连接 ===");
        System.out.println("初始连通分量数: " + uf.getCount());  // 10
        
        System.out.println("连接 1 和 2: " + uf.union(1, 2));   // true
        System.out.println("连接 3 和 4: " + uf.union(3, 4));   // true
        System.out.println("连接 5 和 6: " + uf.union(5, 6));   // true
        System.out.println("连接 1 和 5: " + uf.union(1, 5));   // true
        System.out.println("连接 7 和 8: " + uf.union(7, 8));   // true
        System.out.println("连接 2 和 6: " + uf.union(2, 6));   // false，因为1和2已经连接，5和6已经连接，1和5已经连接
        
        System.out.println("连通分量数: " + uf.getCount());     // 5
        
        // 测试查找
        System.out.println("\n=== 测试查找 ===");
        System.out.println("元素1的根节点: " + uf.find(1));
        System.out.println("元素6的根节点: " + uf.find(6));
        System.out.println("1和6是否连通: " + uf.connected(1, 6));  // true
        System.out.println("1和9是否连通: " + uf.connected(1, 9));  // false
        
        // 测试集合大小
        System.out.println("\n=== 测试集合大小 ===");
        System.out.println("元素1所在集合的大小: " + uf.getSize(1));  // 4 (1, 2, 5, 6)
        System.out.println("元素3所在集合的大小: " + uf.getSize(3));  // 2 (3, 4)
        System.out.println("元素9所在集合的大小: " + uf.getSize(9));  // 1 (9)
        
        // 测试所有集合的大小
        System.out.println("\n=== 测试所有集合的大小 ===");
        java.util.Map<Integer, Integer> sizes = uf.getAllSetSizes();
        System.out.println("所有集合的大小: " + sizes);
        
        // 测试重置
        System.out.println("\n=== 测试重置 ===");
        uf.reset();
        System.out.println("重置后连通分量数: " + uf.getCount());  // 10
        System.out.println("重置后1和6是否连通: " + uf.connected(1, 6));  // false
        
        // 应用：检测图中是否存在环
        System.out.println("\n=== 应用：检测图中是否存在环 ===");
        int[][] edges1 = {{0, 1}, {1, 2}, {2, 3}};  // 无环图
        int[][] edges2 = {{0, 1}, {1, 2}, {2, 0}};  // 有环图
        
        System.out.println("图1是否有环: " + hasCycle(edges1, 4));  // false
        System.out.println("图2是否有环: " + hasCycle(edges2, 3));  // true
        
        // 应用：求图的连通分量
        System.out.println("\n=== 应用：求图的连通分量 ===");
        int[][] edges3 = {{0, 1}, {1, 2}, {3, 4}, {5, 6}};
        UnionFind ufGraph = new UnionFind(7);
        for (int[] edge : edges3) {
            ufGraph.union(edge[0], edge[1]);
        }
        System.out.println("图3的连通分量数: " + ufGraph.getCount());  // 4
    }
    
    /**
     * 检测无向图中是否存在环
     * 
     * @param edges 边集合
     * @param n 节点数量
     * @return 如果存在环，则返回true；否则返回false
     */
    private static boolean hasCycle(int[][] edges, int n) {
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            
            // 如果两个顶点已经在同一集合中，则添加这条边会形成环
            if (uf.connected(x, y)) {
                return true;
            }
            
            // 合并两个顶点所在的集合
            uf.union(x, y);
        }
        return false;
    }
} 