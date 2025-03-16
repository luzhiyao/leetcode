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
 * 解释：偷窃第1间房屋 (金额 = 1) ，然后偷窃第3间房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4。
 * 
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃第1间房屋 (金额 = 2), 偷窃第3间房屋 (金额 = 9)，接着偷窃第5间房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12。
 * 
 * 时间复杂度：O(n)，其中n是房屋的数量
 * 空间复杂度：O(1)（优化后）或 O(n)（基本动态规划）
 */
public class HouseRobber {
    
    /**
     * 动态规划解法
     * dp[i]表示偷窃前i个房屋能获得的最大金额
     * dp[i] = max(dp[i-1], dp[i-2] + nums[i-1])
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        
        for (int i = 2; i <= n; i++) {
            // dp[i-1]: 不偷第i间房子
            // dp[i-2] + nums[i-1]: 偷第i间房子
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        
        return dp[n];
    }
    
    /**
     * 空间优化的动态规划解法
     * 只需要两个变量记录前两个状态
     */
    public int robOptimized(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        
        int prev2 = 0;  // dp[i-2]
        int prev1 = nums[0];  // dp[i-1]
        int current = prev1;
        
        for (int i = 1; i < n; i++) {
            current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return current;
    }
    
    /**
     * 记忆化递归解法
     */
    public int robRecursive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] memo = new int[nums.length];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = -1;  // 初始化为-1表示未计算
        }
        
        return robHelper(nums, nums.length - 1, memo);
    }
    
    private int robHelper(int[] nums, int i, int[] memo) {
        if (i < 0) {
            return 0;
        }
        
        if (memo[i] != -1) {
            return memo[i];
        }
        
        // 选择不偷当前房子或偷当前房子
        memo[i] = Math.max(robHelper(nums, i - 1, memo), 
                           robHelper(nums, i - 2, memo) + nums[i]);
        return memo[i];
    }
    
    // 测试方法
    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();
        
        // 测试例子
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("输入: " + java.util.Arrays.toString(nums1));
        System.out.println("动态规划解法: " + solution.rob(nums1));
        System.out.println("空间优化解法: " + solution.robOptimized(nums1));
        System.out.println("记忆化递归解法: " + solution.robRecursive(nums1));
        
        int[] nums2 = {2, 7, 9, 3, 1};
        System.out.println("\n输入: " + java.util.Arrays.toString(nums2));
        System.out.println("动态规划解法: " + solution.rob(nums2));
        System.out.println("空间优化解法: " + solution.robOptimized(nums2));
        System.out.println("记忆化递归解法: " + solution.robRecursive(nums2));
        
        int[] nums3 = {2, 1, 1, 2};
        System.out.println("\n输入: " + java.util.Arrays.toString(nums3));
        System.out.println("动态规划解法: " + solution.rob(nums3));
        System.out.println("空间优化解法: " + solution.robOptimized(nums3));
        System.out.println("记忆化递归解法: " + solution.robRecursive(nums3));
    }
} 