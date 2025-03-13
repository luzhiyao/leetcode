package src.array;

/**
 * LeetCode 121. 买卖股票的最佳时机 (Best Time to Buy and Sell Stock)
 * 
 * 问题描述：
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0。
 * 
 * 示例：
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * 
 * 输入：[7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 * 
 * 时间复杂度：O(n)，其中 n 是价格数组的长度
 * 空间复杂度：O(1)
 */
public class BestTimeToBuyAndSellStock {
    
    /**
     * 一次遍历解法
     * 记录遍历过程中的最低价格，并计算每天可能的最大利润
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int minPrice = Integer.MAX_VALUE; // 记录到目前为止的最低价格
        int maxProfit = 0; // 记录最大利润
        
        for (int price : prices) {
            // 更新最低价格
            if (price < minPrice) {
                minPrice = price;
            } 
            // 计算当天卖出的利润，并更新最大利润
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        
        return maxProfit;
    }
    
    /**
     * 动态规划解法
     * dp[i][0]表示第i天不持有股票的最大利润
     * dp[i][1]表示第i天持有股票的最大利润
     */
    public int maxProfitDP(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int n = prices.length;
        int[][] dp = new int[n][2];
        
        // 初始状态：第0天
        dp[0][0] = 0;           // 不持有股票，利润为0
        dp[0][1] = -prices[0];  // 持有股票，利润为-prices[0]
        
        for (int i = 1; i < n; i++) {
            // 第i天不持有股票的最大利润 = max(前一天不持有股票的最大利润, 前一天持有股票的最大利润+今天卖出的价格)
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            
            // 第i天持有股票的最大利润 = max(前一天持有股票的最大利润, -今天买入的价格)
            // 注意，因为只允许买入一次，所以买入时的利润就是-prices[i]
            dp[i][1] = Math.max(dp[i-1][1], -prices[i]);
        }
        
        // 最后一天不持有股票的状态就是答案
        return dp[n-1][0];
    }
    
    /**
     * 动态规划空间优化解法
     * 由于每一天的状态只依赖前一天的状态，可以使用两个变量代替二维数组
     */
    public int maxProfitDPOptimized(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        
        int notHold = 0;          // 不持有股票的最大利润
        int hold = -prices[0];    // 持有股票的最大利润
        
        for (int i = 1; i < prices.length; i++) {
            notHold = Math.max(notHold, hold + prices[i]);
            hold = Math.max(hold, -prices[i]);
        }
        
        return notHold;
    }
    
    // 测试方法
    public static void main(String[] args) {
        BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();
        
        // 测试例子
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("输入: [7,1,5,3,6,4]");
        System.out.println("一次遍历解法: " + solution.maxProfit(prices1));
        System.out.println("动态规划解法: " + solution.maxProfitDP(prices1));
        System.out.println("优化动态规划解法: " + solution.maxProfitDPOptimized(prices1));
        
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("\n输入: [7,6,4,3,1]");
        System.out.println("一次遍历解法: " + solution.maxProfit(prices2));
        System.out.println("动态规划解法: " + solution.maxProfitDP(prices2));
        System.out.println("优化动态规划解法: " + solution.maxProfitDPOptimized(prices2));
        
        int[] prices3 = {2, 4, 1, 7, 5, 10};
        System.out.println("\n输入: [2,4,1,7,5,10]");
        System.out.println("一次遍历解法: " + solution.maxProfit(prices3));
        System.out.println("动态规划解法: " + solution.maxProfitDP(prices3));
        System.out.println("优化动态规划解法: " + solution.maxProfitDPOptimized(prices3));
    }
} 