package src.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 200. 岛屿数量 (Number of Islands)
 * 
 * 问题描述：
 * 给你一个由 '1'（陆地）和 '0'（水）组成的二维网格，请你计算网格中岛屿的数量。
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
 * 时间复杂度：O(M×N)，其中 M 和 N 分别为行数和列数
 * 空间复杂度：O(min(M,N))，最坏情况下的递归深度
 */
public class NumberOfIslands {
    
    /**
     * DFS解法
     * 思路：遍历网格，对于每个值为'1'的单元格，执行DFS并将所有相连的'1'标记为已访问
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
                // 如果找到一个陆地（'1'），开始DFS，并增加岛屿计数
                if (grid[r][c] == '1') {
                    count++;
                    dfs(grid, r, c);
                }
            }
        }
        
        return count;
    }
    
    // DFS递归方法，将相连的陆地标记为已访问（将'1'改为'0'）
    private void dfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 边界检查：如果索引越界或者当前单元格不是陆地，直接返回
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] == '0') {
            return;
        }
        
        // 标记当前单元格为已访问（将'1'改为'0'）
        grid[r][c] = '0';
        
        // 递归搜索上、下、左、右四个方向
        dfs(grid, r - 1, c); // 上
        dfs(grid, r + 1, c); // 下
        dfs(grid, r, c - 1); // 左
        dfs(grid, r, c + 1); // 右
    }
    
    /**
     * BFS解法
     * 思路：遍历网格，对于每个值为'1'的单元格，使用BFS探索所有相连的陆地
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
    
    // BFS方法，使用队列实现
    private void bfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 定义方向数组：上、下、左、右
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        grid[r][c] = '0'; // 标记为已访问
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            // 探索上、下、左、右四个方向
            for (int i = 0; i < 4; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];
                
                // 检查边界和是否是陆地
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == '1') {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0'; // 标记为已访问
                }
            }
        }
    }
    
    /**
     * 并查集解法
     * 思路：使用并查集将相连的陆地合并为一个集合，最终集合的数量就是岛屿的数量
     */
    public int numIslandsUnionFind(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 创建并查集
        UnionFind uf = new UnionFind(grid);
        
        // 定义方向数组：上、下、左、右
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    // 标记当前单元格为已访问
                    grid[r][c] = '0';
                    
                    // 探索上、下、左、右四个方向，将相邻的陆地合并
                    for (int i = 0; i < 4; i++) {
                        int newRow = r + dr[i];
                        int newCol = c + dc[i];
                        
                        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == '1') {
                            uf.union(r * cols + c, newRow * cols + newCol);
                        }
                    }
                }
            }
        }
        
        return uf.getCount();
    }
    
    // 并查集实现
    class UnionFind {
        private int count; // 集合数量
        private int[] parent; // 父节点
        private int[] rank; // 秩（树高）
        
        public UnionFind(char[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;
            
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            count = 0;
            
            // 初始化
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '1') {
                        parent[r * cols + c] = r * cols + c; // 自己是自己的父节点
                        count++; // 每个陆地单元格都是一个独立的集合
                    }
                }
            }
        }
        
        // 查找操作，带路径压缩
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        // 合并操作，按秩合并
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
                count--; // 合并后，集合数量减1
            }
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // 用于显示岛屿数量的辅助方法
    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    // 创建网格副本
    private static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
        }
        return copy;
    }
    
    // 测试方法
    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        
        // 测试例子1
        char[][] grid1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        
        System.out.println("输入网格1:");
        printGrid(grid1);
        
        char[][] grid1Copy1 = copyGrid(grid1);
        System.out.println("DFS结果: " + solution.numIslands(grid1Copy1));
        
        char[][] grid1Copy2 = copyGrid(grid1);
        System.out.println("BFS结果: " + solution.numIslandsBFS(grid1Copy2));
        
        char[][] grid1Copy3 = copyGrid(grid1);
        System.out.println("并查集结果: " + solution.numIslandsUnionFind(grid1Copy3));
        
        // 测试例子2
        char[][] grid2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        
        System.out.println("\n输入网格2:");
        printGrid(grid2);
        
        char[][] grid2Copy1 = copyGrid(grid2);
        System.out.println("DFS结果: " + solution.numIslands(grid2Copy1));
        
        char[][] grid2Copy2 = copyGrid(grid2);
        System.out.println("BFS结果: " + solution.numIslandsBFS(grid2Copy2));
        
        char[][] grid2Copy3 = copyGrid(grid2);
        System.out.println("并查集结果: " + solution.numIslandsUnionFind(grid2Copy3));
        
        // 测试例子3：复杂的岛屿结构
        char[][] grid3 = {
            {'1', '1', '1', '0', '1'},
            {'1', '0', '0', '0', '0'},
            {'0', '0', '1', '0', '1'},
            {'1', '0', '1', '1', '1'}
        };
        
        System.out.println("\n输入网格3:");
        printGrid(grid3);
        
        char[][] grid3Copy1 = copyGrid(grid3);
        System.out.println("DFS结果: " + solution.numIslands(grid3Copy1));
        
        char[][] grid3Copy2 = copyGrid(grid3);
        System.out.println("BFS结果: " + solution.numIslandsBFS(grid3Copy2));
        
        char[][] grid3Copy3 = copyGrid(grid3);
        System.out.println("并查集结果: " + solution.numIslandsUnionFind(grid3Copy3));
    }
} 