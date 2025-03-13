package src.dp;

/**
 * LeetCode 198. 打家劫舍 (House Robber)
 * 
 * 问题描述：
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下，一夜之内能够偷窃到的最高金额。
 * 
 * 示例：
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * 
 * 时间复杂度：O(n)，其中 n 是数组长度
 * 空间复杂度：O(n) 或 O(1)，取决于具体实现
 */
public class HouseRobber {
    
    /**
     * 动态规划解法 (使用数组)
     * 思路：对于每个房子，我们可以选择偷或不偷
     * dp[i] 表示偷到第i个房子能获得的最大金额
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        int[] dp = new int[nums.length];
        
        // 基本情况
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        // 填充dp数组
        for (int i = 2; i < nums.length; i++) {
            // 当前房子可以选择偷或不偷
            // 如果偷：当前房子的金额 + 前两个房子能偷到的最大金额
            // 如果不偷：前一个房子能偷到的最大金额
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        
        return dp[nums.length - 1];
    }
    
    /**
     * 动态规划解法 (空间优化版本)
     * 由于我们只需要前两个状态，所以可以使用两个变量代替数组
     */
    public int robOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        // 第一个房子的最大金额
        int first = nums[0];
        // 前两个房子中的最大金额
        int second = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < nums.length; i++) {
            // 计算当前位置的最大金额
            int current = Math.max(first + nums[i], second);
            // 更新状态
            first = second;
            second = current;
        }
        
        return second;
    }
    
    /**
     * 递归 + 记忆化解法
     */
    public int robMemo(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // 记忆数组，用于存储已计算的结果
        Integer[] memo = new Integer[nums.length];
        
        return robHelper(nums, nums.length - 1, memo);
    }
    
    private int robHelper(int[] nums, int i, Integer[] memo) {
        // 基本情况
        if (i < 0) {
            return 0;
        }
        
        // 如果结果已经计算过，直接返回
        if (memo[i] != null) {
            return memo[i];
        }
        
        // 计算结果：max(偷当前房子, 不偷当前房子)
        memo[i] = Math.max(robHelper(nums, i - 2, memo) + nums[i], robHelper(nums, i - 1, memo));
        
        return memo[i];
    }
    
    // 测试方法
    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();
        
        // 测试例子
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("输入: [1,2,3,1]");
        System.out.println("动态规划解法: " + solution.rob(nums1));
        System.out.println("优化解法: " + solution.robOptimized(nums1));
        System.out.println("记忆化递归解法: " + solution.robMemo(nums1));
        
        int[] nums2 = {2, 7, 9, 3, 1};
        System.out.println("\n输入: [2,7,9,3,1]");
        System.out.println("动态规划解法: " + solution.rob(nums2));
        System.out.println("优化解法: " + solution.robOptimized(nums2));
        System.out.println("记忆化递归解法: " + solution.robMemo(nums2));
        
        int[] nums3 = {2, 1, 1, 2};
        System.out.println("\n输入: [2,1,1,2]");
        System.out.println("动态规划解法: " + solution.rob(nums3));
        System.out.println("优化解法: " + solution.robOptimized(nums3));
        System.out.println("记忆化递归解法: " + solution.robMemo(nums3));
    }
} 