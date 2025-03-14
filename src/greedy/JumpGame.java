package src.greedy;

/**
 * LeetCode 55. 跳跃游戏 (Jump Game)
 * 
 * 问题描述：
 * 给定一个非负整数数组 nums，你最初位于数组的第一个下标。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标。
 * 
 * 示例：
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 * 
 * 输入：nums = [3,2,1,0,4]
 * 输出：false
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0，所以永远不可能到达最后一个下标。
 * 
 * 时间复杂度：O(n)，其中 n 是数组 nums 的长度
 * 空间复杂度：O(1)
 */
public class JumpGame {
    
    /**
     * 贪心算法
     * 
     * 算法思路：
     * 1. 维护一个变量 maxReach，表示能够到达的最远位置
     * 2. 遍历数组，在遍历过程中更新 maxReach = max(maxReach, i + nums[i])
     * 3. 如果发现当前位置 i 已经超过了 maxReach，则无法到达终点
     * 4. 如果 maxReach 大于等于数组最后一个位置，则可以到达
     * 
     * 贪心策略：
     * 我们不需要考虑具体的跳跃路径，只需要知道是否存在一条可行的路径即可。
     * 在遍历过程中，我们只关心能够到达的最远位置。
     * 
     * @param nums 跳跃能力数组
     * @return 是否能够到达最后一个下标
     */
    public boolean canJump(int[] nums) {
        // 能够到达的最远位置
        int maxReach = 0;
        
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 如果当前位置已经无法到达，则无法继续前进
            if (i > maxReach) {
                return false;
            }
            
            // 更新能够到达的最远位置
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // 如果已经可以到达最后一个位置，直接返回 true
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        // 遍历结束后可以到达最后一个位置
        return true;
    }
    
    /**
     * 从后向前的贪心算法
     * 
     * 算法思路：
     * 1. 从后往前遍历数组
     * 2. 维护一个变量 lastPos，表示可以到达终点的最后一个位置
     * 3. 如果从当前位置 i 可以到达 lastPos（即 i + nums[i] >= lastPos），则更新 lastPos 为 i
     * 4. 遍历结束后，如果 lastPos 为 0，则可以从起点到达终点
     * 
     * @param nums 跳跃能力数组
     * @return 是否能够到达最后一个下标
     */
    public boolean canJumpBackward(int[] nums) {
        int n = nums.length;
        // 初始时，可以到达终点的最后一个位置就是终点本身
        int lastPos = n - 1;
        
        // 从后往前遍历
        for (int i = n - 2; i >= 0; i--) {
            // 如果从当前位置可以到达 lastPos，则更新 lastPos
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        
        // 如果 lastPos 被更新为 0，说明可以从起点到达终点
        return lastPos == 0;
    }
    
    /**
     * 动态规划解法
     * 
     * 算法思路：
     * 1. 创建一个 dp 数组，dp[i] 表示是否能够到达位置 i
     * 2. 初始化 dp[0] = true，因为起点总是可达的
     * 3. 对于每个位置 i，遍历前面的所有位置 j，如果 dp[j] 为 true 且 j + nums[j] >= i，则 dp[i] 为 true
     * 
     * 注意：这种解法的时间复杂度为 O(n²)，不如贪心算法高效
     * 
     * @param nums 跳跃能力数组
     * @return 是否能够到达最后一个下标
     */
    public boolean canJumpDP(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;  // 起点总是可达的
        
        // 计算每个位置是否可达
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // 如果位置 j 可达，且从 j 可以跳到 i
                if (dp[j] && j + nums[j] >= i) {
                    dp[i] = true;
                    break;  // 一旦确定可达，则跳出内层循环
                }
            }
        }
        
        return dp[n - 1];
    }
    
    public static void main(String[] args) {
        JumpGame solution = new JumpGame();
        
        // 测试用例1
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("输入: [2,3,1,1,4]");
        System.out.println("贪心法(从前往后)输出: " + solution.canJump(nums1));
        System.out.println("贪心法(从后往前)输出: " + solution.canJumpBackward(nums1));
        System.out.println("动态规划法输出: " + solution.canJumpDP(nums1));
        System.out.println("期望输出: true");
        
        // 测试用例2
        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println("\n输入: [3,2,1,0,4]");
        System.out.println("贪心法(从前往后)输出: " + solution.canJump(nums2));
        System.out.println("贪心法(从后往前)输出: " + solution.canJumpBackward(nums2));
        System.out.println("动态规划法输出: " + solution.canJumpDP(nums2));
        System.out.println("期望输出: false");
        
        // 测试用例3 - 单个元素
        int[] nums3 = {0};
        System.out.println("\n输入: [0]");
        System.out.println("贪心法(从前往后)输出: " + solution.canJump(nums3));
        System.out.println("贪心法(从后往前)输出: " + solution.canJumpBackward(nums3));
        System.out.println("动态规划法输出: " + solution.canJumpDP(nums3));
        System.out.println("期望输出: true");
        
        // 测试用例4 - 全零数组（除起点外）
        int[] nums4 = {2, 0, 0, 0, 0};
        System.out.println("\n输入: [2,0,0,0,0]");
        System.out.println("贪心法(从前往后)输出: " + solution.canJump(nums4));
        System.out.println("贪心法(从后往前)输出: " + solution.canJumpBackward(nums4));
        System.out.println("动态规划法输出: " + solution.canJumpDP(nums4));
        System.out.println("期望输出: false");
    }
} 