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
 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ，所以永远不可能到达最后一个下标。
 * 
 * 时间复杂度：O(n)，其中n是数组的长度
 * 空间复杂度：O(1)
 */
public class JumpGame {
    
    /**
     * 贪心算法
     * 跟踪能够到达的最远位置
     */
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        if (nums.length == 1) {
            return true; // 只有一个元素，已经在终点
        }
        
        int maxReach = 0; // 能够到达的最远位置
        
        for (int i = 0; i < nums.length; i++) {
            // 如果当前位置已经超过了能够到达的最远位置，说明无法继续前进
            if (i > maxReach) {
                return false;
            }
            
            // 更新能够到达的最远位置
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // 如果已经能够到达或者超过最后一个位置，返回成功
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        return false; // 遍历完整个数组，仍然无法到达最后一个位置
    }
    
    /**
     * 从后往前的贪心算法
     * 检查是否可以从当前位置到达上一个已知可以到达终点的位置
     */
    public boolean canJumpBackward(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        int lastPos = nums.length - 1; // 开始时，最后一个位置是终点
        
        // 从后往前遍历
        for (int i = nums.length - 2; i >= 0; i--) {
            // 如果当前位置可以跳到或超过lastPos
            if (i + nums[i] >= lastPos) {
                lastPos = i; // 更新lastPos为当前位置
            }
        }
        
        // 如果lastPos为0，说明可以从起点到达终点
        return lastPos == 0;
    }
    
    /**
     * 动态规划解法（不是最优，但提供不同的思路）
     * dp[i]表示是否能够到达位置i
     */
    public boolean canJumpDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true; // 起点总是可达的
        
        for (int i = 0; i < n; i++) {
            if (!dp[i]) {
                continue; // 如果当前位置不可达，跳过
            }
            
            // 从当前位置可以到达的所有位置
            for (int j = 1; j <= nums[i] && i + j < n; j++) {
                dp[i + j] = true;
            }
            
            // 如果已经可以到达终点，提前返回
            if (dp[n - 1]) {
                return true;
            }
        }
        
        return dp[n - 1];
    }
    
    // 测试方法
    public static void main(String[] args) {
        JumpGame solution = new JumpGame();
        
        // 测试用例1
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("测试用例1: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("贪心算法（从前往后）: " + solution.canJump(nums1));  // 预期输出: true
        System.out.println("贪心算法（从后往前）: " + solution.canJumpBackward(nums1));
        System.out.println("动态规划: " + solution.canJumpDP(nums1));
        
        // 测试用例2
        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println("\n测试用例2: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("贪心算法（从前往后）: " + solution.canJump(nums2));  // 预期输出: false
        System.out.println("贪心算法（从后往前）: " + solution.canJumpBackward(nums2));
        System.out.println("动态规划: " + solution.canJumpDP(nums2));
        
        // 测试用例3: 只有一个元素
        int[] nums3 = {0};
        System.out.println("\n测试用例3: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("贪心算法（从前往后）: " + solution.canJump(nums3));  // 预期输出: true
        System.out.println("贪心算法（从后往前）: " + solution.canJumpBackward(nums3));
        System.out.println("动态规划: " + solution.canJumpDP(nums3));
        
        // 测试用例4: 第一个元素为0
        int[] nums4 = {0, 2, 3};
        System.out.println("\n测试用例4: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("贪心算法（从前往后）: " + solution.canJump(nums4));  // 预期输出: false
        System.out.println("贪心算法（从后往前）: " + solution.canJumpBackward(nums4));
        System.out.println("动态规划: " + solution.canJumpDP(nums4));
        
        // 测试用例5: 较长的数组
        int[] nums5 = {2, 0, 0, 1, 4, 0, 0, 0, 1, 2, 0, 1};
        System.out.println("\n测试用例5: nums = " + java.util.Arrays.toString(nums5));
        System.out.println("贪心算法（从前往后）: " + solution.canJump(nums5));
        System.out.println("贪心算法（从后往前）: " + solution.canJumpBackward(nums5));
        System.out.println("动态规划: " + solution.canJumpDP(nums5));
    }
} 