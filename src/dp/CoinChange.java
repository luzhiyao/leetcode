package src.dp;

import java.util.Arrays;

/**
 * LeetCode 322. 零钱兑换 (Coin Change)
 * 
 * 问题描述：
 * 给你一个整数数组 coins，表示不同面额的硬币；以及一个整数 amount，表示总金额。
 * 计算并返回可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 你可以认为每种硬币的数量是无限的。
 * 
 * 示例：
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * 
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * 
 * 输入：coins = [1], amount = 0
 * 输出：0
 * 
 * 时间复杂度：O(amount * n)，其中n是硬币的种类数
 * 空间复杂度：O(amount)
 */
public class CoinChange {
    
    /**
     * 动态规划（自底向上）
     * dp[i] 表示凑成金额i所需的最少硬币数
     */
    public int coinChange(int[] coins, int amount) {
        // 定义dp数组，dp[i]表示凑成金额i所需的最少硬币数
        int[] dp = new int[amount + 1];
        
        // 初始化dp数组，除了dp[0]=0外，其他初始化为一个较大的值
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        // 计算每个金额的最少硬币数
        for (int i = 1; i <= amount; i++) {
            // 尝试每种硬币
            for (int coin : coins) {
                // 如果当前硬币面值小于等于要凑的金额，可以使用这枚硬币
                if (coin <= i) {
                    // 状态转移方程：使用当前硬币后，所需的最少硬币数
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        // 如果dp[amount]仍然是初始值，说明无法凑出amount金额
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    /**
     * 动态规划（自顶向下，记忆化搜索）
     */
    public int coinChangeTopDown(int[] coins, int amount) {
        if (amount < 1) {
            return 0;
        }
        
        // 创建备忘录，记录已经计算过的金额对应的最少硬币数
        int[] memo = new int[amount + 1];
        
        return coinChangeHelper(coins, amount, memo);
    }
    
    private int coinChangeHelper(int[] coins, int remain, int[] memo) {
        // 基本情况
        if (remain < 0) {
            return -1; // 金额为负，无法凑出
        }
        if (remain == 0) {
            return 0; // 金额为0，不需要硬币
        }
        
        // 如果已经计算过，直接返回结果
        if (memo[remain] != 0) {
            return memo[remain];
        }
        
        // 初始化为一个较大的值
        int minCoins = Integer.MAX_VALUE;
        
        // 尝试每种硬币
        for (int coin : coins) {
            // 递归计算使用当前硬币后，剩余金额所需的最少硬币数
            int res = coinChangeHelper(coins, remain - coin, memo);
            
            // 如果可以凑出剩余金额，更新最少硬币数
            if (res >= 0 && res < minCoins) {
                minCoins = res + 1; // 加上当前使用的这枚硬币
            }
        }
        
        // 保存结果到备忘录
        memo[remain] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        return memo[remain];
    }
    
    /**
     * 广度优先搜索解法
     * 将问题看作是图中的最短路径问题
     */
    public int coinChangeBFS(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        
        // 使用队列进行BFS
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        boolean[] visited = new boolean[amount + 1];
        
        queue.offer(0); // 从金额0开始
        visited[0] = true;
        
        int level = 0; // 层级代表硬币数量
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            
            // 遍历当前层的所有节点
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                
                // 尝试添加每种硬币
                for (int coin : coins) {
                    int next = curr + coin;
                    
                    // 如果达到目标金额，返回当前层级（硬币数量）
                    if (next == amount) {
                        return level;
                    }
                    
                    // 如果超过目标金额或已访问过，跳过
                    if (next > amount || visited[next]) {
                        continue;
                    }
                    
                    // 标记为已访问，并加入队列
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        
        return -1; // 无法凑出指定金额
    }
    
    // 测试方法
    public static void main(String[] args) {
        CoinChange solution = new CoinChange();
        
        // 测试用例1
        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println("测试用例1: coins = " + Arrays.toString(coins1) + ", amount = " + amount1);
        System.out.println("动态规划（自底向上）: " + solution.coinChange(coins1, amount1)); // 预期输出: 3
        System.out.println("动态规划（自顶向下）: " + solution.coinChangeTopDown(coins1, amount1));
        System.out.println("BFS解法: " + solution.coinChangeBFS(coins1, amount1));
        
        // 测试用例2
        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("\n测试用例2: coins = " + Arrays.toString(coins2) + ", amount = " + amount2);
        System.out.println("动态规划（自底向上）: " + solution.coinChange(coins2, amount2)); // 预期输出: -1
        System.out.println("动态规划（自顶向下）: " + solution.coinChangeTopDown(coins2, amount2));
        System.out.println("BFS解法: " + solution.coinChangeBFS(coins2, amount2));
        
        // 测试用例3
        int[] coins3 = {1};
        int amount3 = 0;
        System.out.println("\n测试用例3: coins = " + Arrays.toString(coins3) + ", amount = " + amount3);
        System.out.println("动态规划（自底向上）: " + solution.coinChange(coins3, amount3)); // 预期输出: 0
        System.out.println("动态规划（自顶向下）: " + solution.coinChangeTopDown(coins3, amount3));
        System.out.println("BFS解法: " + solution.coinChangeBFS(coins3, amount3));
        
        // 测试用例4
        int[] coins4 = {1, 3, 4, 5};
        int amount4 = 7;
        System.out.println("\n测试用例4: coins = " + Arrays.toString(coins4) + ", amount = " + amount4);
        System.out.println("动态规划（自底向上）: " + solution.coinChange(coins4, amount4)); // 预期输出: 2 (3+4)
        System.out.println("动态规划（自顶向下）: " + solution.coinChangeTopDown(coins4, amount4));
        System.out.println("BFS解法: " + solution.coinChangeBFS(coins4, amount4));
        
        // 测试用例5 - 较大金额
        int[] coins5 = {186, 419, 83, 408};
        int amount5 = 6249;
        System.out.println("\n测试用例5: coins = " + Arrays.toString(coins5) + ", amount = " + amount5);
        System.out.println("动态规划（自底向上）: " + solution.coinChange(coins5, amount5)); 
        System.out.println("动态规划（自顶向下）: " + solution.coinChangeTopDown(coins5, amount5));
        // BFS对于大金额可能会超时，因此不测试
    }
}
