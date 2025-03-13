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
 * 空间复杂度：O(1) 使用优化后的方法
 */
public class ClimbingStairs {
    
    /**
     * 动态规划解法（自底向上）
     * 使用数组存储中间结果
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        // dp[i] 表示爬到第i阶的方法数
        int[] dp = new int[n + 1];
        
        // 基本情况
        dp[1] = 1; // 爬到第1阶有1种方法
        dp[2] = 2; // 爬到第2阶有2种方法
        
        // 填充dp数组
        for (int i = 3; i <= n; i++) {
            // 到达第i阶的方法 = 到达第(i-1)阶的方法 + 到达第(i-2)阶的方法
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    /**
     * 优化的动态规划解法，使用常数空间
     */
    public int climbStairsOptimized(int n) {
        if (n <= 2) {
            return n;
        }
        
        // 初始化前两个值
        int prev1 = 1; // dp[1]
        int prev2 = 2; // dp[2]
        int current = 0;
        
        // 从第3阶开始计算
        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        
        return prev2;
    }
    
    /**
     * 递归解法（自顶向下）- 带备忘录（记忆化搜索）
     */
    public int climbStairsRecursive(int n) {
        // 备忘录，用于存储已经计算过的结果
        int[] memo = new int[n + 1];
        return climbStairsHelper(n, memo);
    }
    
    private int climbStairsHelper(int n, int[] memo) {
        if (n <= 2) {
            return n;
        }
        
        // 如果已经计算过，直接返回
        if (memo[n] > 0) {
            return memo[n];
        }
        
        // 否则计算并存储结果
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
    }
    
    // 测试方法
    public static void main(String[] args) {
        ClimbingStairs solution = new ClimbingStairs();
        
        // 测试例子
        int n1 = 2;
        System.out.println("n = " + n1 + "，方法数: " + solution.climbStairs(n1));
        System.out.println("优化解法: " + solution.climbStairsOptimized(n1));
        System.out.println("递归解法: " + solution.climbStairsRecursive(n1));
        
        int n2 = 3;
        System.out.println("\nn = " + n2 + "，方法数: " + solution.climbStairs(n2));
        System.out.println("优化解法: " + solution.climbStairsOptimized(n2));
        System.out.println("递归解法: " + solution.climbStairsRecursive(n2));
        
        int n3 = 10;
        System.out.println("\nn = " + n3 + "，方法数: " + solution.climbStairs(n3));
        System.out.println("优化解法: " + solution.climbStairsOptimized(n3));
        System.out.println("递归解法: " + solution.climbStairsRecursive(n3));
    }
} 