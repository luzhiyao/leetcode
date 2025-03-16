package src.dp;

/**
 * LeetCode 70. 爬楼梯 (Climbing Stairs)
 * 
 * 问题描述：
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 
 * 示例：
 * 输入：n = 2
 * 输出：2
 * 解释：有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 * 
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)（优化后）或 O(n)（基本动态规划）
 */
public class ClimbingStairs {
    
    /**
     * 动态规划解法（自底向上）
     * dp[i]表示爬到第i阶楼梯的方法数
     * dp[i] = dp[i-1] + dp[i-2]
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        int[] dp = new int[n + 1];
        dp[1] = 1; // 爬到第1阶有1种方法
        dp[2] = 2; // 爬到第2阶有2种方法
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    /**
     * 空间优化的动态规划解法
     * 由于我们只需要前两个状态，所以可以只用两个变量代替数组
     */
    public int climbStairsOptimized(int n) {
        if (n <= 2) {
            return n;
        }
        
        int prev1 = 1; // 表示dp[i-2]
        int prev2 = 2; // 表示dp[i-1]
        int current = 0;
        
        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        
        return prev2;
    }
    
    /**
     * 递归解法（自顶向下）
     * 需要记忆化优化，否则会超时
     */
    public int climbStairsRecursive(int n) {
        int[] memo = new int[n + 1];
        return recursiveWithMemo(n, memo);
    }
    
    private int recursiveWithMemo(int n, int[] memo) {
        if (n <= 2) {
            return n;
        }
        
        if (memo[n] > 0) {
            return memo[n];
        }
        
        memo[n] = recursiveWithMemo(n - 1, memo) + recursiveWithMemo(n - 2, memo);
        return memo[n];
    }
    
    /**
     * 矩阵快速幂解法
     * 时间复杂度可以优化到O(log n)
     */
    public int climbStairsMatrix(int n) {
        if (n <= 2) {
            return n;
        }
        
        int[][] base = {{1, 1}, {1, 0}};
        int[][] result = matrixPow(base, n - 1);
        
        return result[0][0] + result[0][1];
    }
    
    private int[][] matrixPow(int[][] base, int power) {
        int[][] result = {{1, 0}, {0, 1}}; // 单位矩阵
        
        while (power > 0) {
            if ((power & 1) == 1) {
                result = matrixMultiply(result, base);
            }
            
            base = matrixMultiply(base, base);
            power >>= 1;
        }
        
        return result;
    }
    
    private int[][] matrixMultiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        c[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        c[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        c[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        c[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        return c;
    }
    
    // 测试方法
    public static void main(String[] args) {
        ClimbingStairs solution = new ClimbingStairs();
        
        // 测试例子
        int n1 = 2;
        System.out.println("输入: n = " + n1);
        System.out.println("动态规划解法: " + solution.climbStairs(n1));
        System.out.println("空间优化解法: " + solution.climbStairsOptimized(n1));
        System.out.println("记忆化递归解法: " + solution.climbStairsRecursive(n1));
        System.out.println("矩阵快速幂解法: " + solution.climbStairsMatrix(n1));
        
        int n2 = 3;
        System.out.println("\n输入: n = " + n2);
        System.out.println("动态规划解法: " + solution.climbStairs(n2));
        System.out.println("空间优化解法: " + solution.climbStairsOptimized(n2));
        System.out.println("记忆化递归解法: " + solution.climbStairsRecursive(n2));
        System.out.println("矩阵快速幂解法: " + solution.climbStairsMatrix(n2));
        
        int n3 = 10;
        System.out.println("\n输入: n = " + n3);
        System.out.println("动态规划解法: " + solution.climbStairs(n3));
        System.out.println("空间优化解法: " + solution.climbStairsOptimized(n3));
        System.out.println("记忆化递归解法: " + solution.climbStairsRecursive(n3));
        System.out.println("矩阵快速幂解法: " + solution.climbStairsMatrix(n3));
    }
} 