package src.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 200. 岛屿数量 (Number of Islands)
 * 
 * 问题描述：
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 * 
 * 示例：
 * 输入：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * 输出：1
 * 
 * 输入：grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * 输出：3
 * 
 * 时间复杂度：O(m*n)，其中m和n分别为行数和列数
 * 空间复杂度：O(m*n)
 */
public class NumberOfIslands {
    
    /**
     * DFS解法
     * 使用深度优先搜索标记已访问的陆地
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    dfs(grid, r, c);
                }
            }
        }
        
        return count;
    }
    
    private void dfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 边界检查或已访问（标记为'0'）
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] == '0') {
            return;
        }
        
        // 标记当前陆地为已访问
        grid[r][c] = '0';
        
        // 访问四个相邻位置
        dfs(grid, r - 1, c); // 上
        dfs(grid, r + 1, c); // 下
        dfs(grid, r, c - 1); // 左
        dfs(grid, r, c + 1); // 右
    }
    
    /**
     * BFS解法
     * 使用广度优先搜索标记已访问的陆地
     */
    public int numIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    bfs(grid, r, c);
                }
            }
        }
        
        return count;
    }
    
    private void bfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        grid[r][c] = '0'; // 标记起始位置为已访问
        
        // 定义四个方向：上、下、左、右
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            // 探索四个方向
            for (int[] dir : directions) {
                int newR = current[0] + dir[0];
                int newC = current[1] + dir[1];
                
                // 检查新位置是否有效且是陆地
                if (newR >= 0 && newC >= 0 && newR < rows && newC < cols && grid[newR][newC] == '1') {
                    queue.offer(new int[]{newR, newC});
                    grid[newR][newC] = '0'; // 标记为已访问
                }
            }
        }
    }
    
    /**
     * 并查集解法
     * 使用并查集连接相邻的陆地
     */
    public int numIslandsUnionFind(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 创建并查集
        UnionFind uf = new UnionFind(grid);
        
        // 定义四个方向：右、下（只需要向右和向下合并，因为向左和向上的合并会被其他位置的向右和向下覆盖）
        int[][] directions = {{0, 1}, {1, 0}};
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    // 标记当前陆地为已访问，避免重复计算
                    grid[r][c] = '0';
                    
                    // 检查右边和下边是否也是陆地，如果是则合并
                    for (int[] dir : directions) {
                        int newR = r + dir[0];
                        int newC = c + dir[1];
                        
                        if (newR >= 0 && newC >= 0 && newR < rows && newC < cols && grid[newR][newC] == '1') {
                            uf.union(r * cols + c, newR * cols + newC);
                        }
                    }
                }
            }
        }
        
        return uf.getCount();
    }
    
    // 并查集实现
    class UnionFind {
        int[] parent;  // 父节点数组
        int[] rank;    // 秩数组，用于按秩合并
        int count;     // 连通分量数量
        
        public UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            
            // 初始化并查集
            count = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '1') {
                        parent[r * cols + c] = r * cols + c; // 初始时父节点指向自己
                        count++; // 发现一个新岛屿
                    }
                }
            }
        }
        
        // 查找操作（带路径压缩）
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }
        
        // 合并操作（按秩合并）
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--; // 两个岛屿合并为一个
            }
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // 测试方法
    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        
        // 测试用例1
        char[][] grid1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        
        // 复制原始网格，因为解法会修改输入
        char[][] grid1Copy1 = copyGrid(grid1);
        char[][] grid1Copy2 = copyGrid(grid1);
        
        System.out.println("测试用例1:");
        System.out.println("DFS解法: " + solution.numIslands(grid1));  // 预期输出: 1
        System.out.println("BFS解法: " + solution.numIslandsBFS(grid1Copy1));
        System.out.println("并查集解法: " + solution.numIslandsUnionFind(grid1Copy2));
        
        // 测试用例2
        char[][] grid2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        
        // 复制原始网格
        char[][] grid2Copy1 = copyGrid(grid2);
        char[][] grid2Copy2 = copyGrid(grid2);
        
        System.out.println("\n测试用例2:");
        System.out.println("DFS解法: " + solution.numIslands(grid2));  // 预期输出: 3
        System.out.println("BFS解法: " + solution.numIslandsBFS(grid2Copy1));
        System.out.println("并查集解法: " + solution.numIslandsUnionFind(grid2Copy2));
        
        // 测试用例3: 空网格
        char[][] grid3 = {};
        
        System.out.println("\n测试用例3 (空网格):");
        System.out.println("DFS解法: " + solution.numIslands(grid3));  // 预期输出: 0
        System.out.println("BFS解法: " + solution.numIslandsBFS(grid3));
        System.out.println("并查集解法: " + solution.numIslandsUnionFind(grid3));
        
        // 测试用例4: 所有位置都是水
        char[][] grid4 = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        
        System.out.println("\n测试用例4 (全是水):");
        System.out.println("DFS解法: " + solution.numIslands(grid4));  // 预期输出: 0
        System.out.println("BFS解法: " + solution.numIslandsBFS(grid4));
        System.out.println("并查集解法: " + solution.numIslandsUnionFind(grid4));
    }
    
    // 辅助方法：复制二维网格
    private static char[][] copyGrid(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return new char[0][0];
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        char[][] copy = new char[rows][cols];
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                copy[r][c] = grid[r][c];
            }
        }
        
        return copy;
    }
} 