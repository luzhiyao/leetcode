package src.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 322. 零钱兑换 (Coin Change)
 * 
 * 问题描述：
 * 给你一个整数数组 coins，表示不同面额的硬币；以及一个整数 amount，表示总金额。
 * 计算并返回可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 * 你可以认为每种硬币的数量是无限的。
 * 
 * 示例1：
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 * 
 * 示例2：
 * 输入：coins = [2], amount = 3
 * 输出：-1
 * 
 * 示例3：
 * 输入：coins = [1], amount = 0
 * 输出：0
 * 
 * 时间复杂度：O(amount * n) - n是硬币数量
 * 空间复杂度：O(amount)
 */
public class CoinChange {
    
    /**
     * 动态规划解法 - 自底向上
     * 
     * 思路：使用动态规划数组dp[i]表示凑成金额i所需的最少硬币数量
     * 状态转移方程：dp[i] = min(dp[i], dp[i - coin] + 1) 当 i >= coin
     */
    public int coinChange(int[] coins, int amount) {
        // 如果金额为0，不需要硬币
        if (amount == 0) return 0;
        
        // dp[i]表示凑成金额i所需的最少硬币数量
        int[] dp = new int[amount + 1];
        // 初始化为一个不可能的最大值
        Arrays.fill(dp, amount + 1);
        // 基本情况：金额为0不需要硬币
        dp[0] = 0;
        
        // 遍历所有可能的金额
        for (int i = 1; i <= amount; i++) {
            // 遍历所有可能使用的硬币
            for (int coin : coins) {
                // 如果当前硬币面额不超过当前金额
                if (coin <= i) {
                    // 使用当前硬币，更新最小硬币数
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        // 如果dp[amount]仍然是初始化的值，说明无法凑成amount
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    /**
     * 记忆化搜索解法 - 自顶向下
     * 
     * 思路：使用递归+缓存，避免重复计算
     */
    public int coinChangeMemo(int[] coins, int amount) {
        if (amount == 0) return 0;
        
        // 使用Map作为缓存
        return coinChangeMemoHelper(coins, amount, new HashMap<>());
    }
    
    private int coinChangeMemoHelper(int[] coins, int amount, Map<Integer, Integer> memo) {
        // 如果金额为0，不需要硬币
        if (amount == 0) return 0;
        // 如果金额为负，无法凑成，返回-1
        if (amount < 0) return -1;
        
        // 如果已经计算过，直接返回缓存结果
        if (memo.containsKey(amount)) {
            return memo.get(amount);
        }
        
        int minCoins = Integer.MAX_VALUE;
        
        // 尝试使用每一种硬币
        for (int coin : coins) {
            // 使用当前硬币后的剩余金额
            int remaining = amount - coin;
            int remainingCoins = coinChangeMemoHelper(coins, remaining, memo);
            
            // 如果剩余金额有解
            if (remainingCoins >= 0) {
                // 更新最小硬币数
                minCoins = Math.min(minCoins, remainingCoins + 1);
            }
        }
        
        // 如果没有找到解，设置为-1
        int result = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        // 缓存结果
        memo.put(amount, result);
        
        return result;
    }
    
    /**
     * BFS解法
     * 
     * 思路：将问题看作图论问题，从金额0开始，每次使用一枚硬币，看最少多少步到达amount
     */
    public int coinChangeBFS(int[] coins, int amount) {
        if (amount == 0) return 0;
        
        // 用于标记已经访问过的金额
        boolean[] visited = new boolean[amount + 1];
        visited[0] = true;
        
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.offer(0);
        
        int step = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++;
            
            // 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                
                // 尝试使用每一种硬币
                for (int coin : coins) {
                    int next = curr + coin;
                    
                    // 如果达到了目标金额
                    if (next == amount) {
                        return step;
                    }
                    
                    // 如果下一个金额有效且未访问过
                    if (next < amount && !visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        
        // 如果无法达到目标金额
        return -1;
    }
    
    /**
     * 贪心 + DFS解法（不一定最优，但在某些情况下效率高）
     * 
     * 思路：从大额硬币开始尝试，可能快速找到解，但不保证最优解，需要回溯验证
     */
    public int coinChangeGreedy(int[] coins, int amount) {
        // 按面额从大到小排序
        Arrays.sort(coins);
        int[] result = {Integer.MAX_VALUE};
        
        // 从大额硬币开始尝试
        dfsGreedy(coins, amount, coins.length - 1, 0, result);
        
        return result[0] == Integer.MAX_VALUE ? -1 : result[0];
    }
    
    private void dfsGreedy(int[] coins, int amount, int index, int count, int[] result) {
        // 基本情况：金额为0，更新最小硬币数
        if (amount == 0) {
            result[0] = Math.min(result[0], count);
            return;
        }
        
        // 如果已经超过了最小硬币数或没有硬币可用，直接返回
        if (index < 0 || count + Math.ceil((double) amount / coins[0]) >= result[0]) {
            return;
        }
        
        // 计算可以使用的当前面额硬币的最大数量
        int maxCount = amount / coins[index];
        
        // 从最大数量开始尝试，优先使用大额硬币
        for (int i = maxCount; i >= 0; i--) {
            // 剩余金额
            int remaining = amount - i * coins[index];
            // 递归处理剩余金额
            dfsGreedy(coins, remaining, index - 1, count + i, result);
        }
    }
    
    public static void main(String[] args) {
        CoinChange solution = new CoinChange();
        
        // 测试用例1
        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println("示例1 - DP解法: " + solution.coinChange(coins1, amount1)); // 输出：3
        System.out.println("示例1 - 记忆化搜索: " + solution.coinChangeMemo(coins1, amount1)); // 输出：3
        System.out.println("示例1 - BFS解法: " + solution.coinChangeBFS(coins1, amount1)); // 输出：3
        System.out.println("示例1 - 贪心+DFS解法: " + solution.coinChangeGreedy(coins1, amount1)); // 输出：3
        
        // 测试用例2
        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("示例2 - DP解法: " + solution.coinChange(coins2, amount2)); // 输出：-1
        System.out.println("示例2 - 记忆化搜索: " + solution.coinChangeMemo(coins2, amount2)); // 输出：-1
        System.out.println("示例2 - BFS解法: " + solution.coinChangeBFS(coins2, amount2)); // 输出：-1
        System.out.println("示例2 - 贪心+DFS解法: " + solution.coinChangeGreedy(coins2, amount2)); // 输出：-1
        
        // 测试用例3
        int[] coins3 = {1};
        int amount3 = 0;
        System.out.println("示例3 - DP解法: " + solution.coinChange(coins3, amount3)); // 输出：0
        System.out.println("示例3 - 记忆化搜索: " + solution.coinChangeMemo(coins3, amount3)); // 输出：0
        System.out.println("示例3 - BFS解法: " + solution.coinChangeBFS(coins3, amount3)); // 输出：0
        System.out.println("示例3 - 贪心+DFS解法: " + solution.coinChangeGreedy(coins3, amount3)); // 输出：0
        
        // 测试用例4：特殊情况
        int[] coins4 = {186, 419, 83, 408};
        int amount4 = 6249;
        System.out.println("示例4 - DP解法: " + solution.coinChange(coins4, amount4));
        System.out.println("示例4 - 记忆化搜索: " + solution.coinChangeMemo(coins4, amount4));
        System.out.println("示例4 - BFS解法: " + solution.coinChangeBFS(coins4, amount4));
        
        // 测试用例5：陷阱案例 - 贪心可能不是最优解
        int[] coins5 = {1, 3, 4};
        int amount5 = 6;
        System.out.println("示例5 - DP解法: " + solution.coinChange(coins5, amount5)); // 输出：2 (使用3+3)
        System.out.println("示例5 - 记忆化搜索: " + solution.coinChangeMemo(coins5, amount5)); // 输出：2
        System.out.println("示例5 - BFS解法: " + solution.coinChangeBFS(coins5, amount5)); // 输出：2
        System.out.println("示例5 - 贪心+DFS解法: " + solution.coinChangeGreedy(coins5, amount5)); // 输出：2
    }
} 