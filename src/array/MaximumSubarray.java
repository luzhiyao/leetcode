package src.array;

/**
 * LeetCode 53. 最大子数组和 (Maximum Subarray)
 * 
 * 问题描述：
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组是数组中的一个连续部分。
 * 
 * 示例：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 
 * 输入：nums = [1]
 * 输出：1
 * 
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 * 
 * 时间复杂度：O(n)，其中 n 是数组的长度
 * 空间复杂度：O(1)
 */
public class MaximumSubarray {
    
    /**
     * 动态规划解法（Kadane算法）
     * 关键思想：在每一步，我们考虑以当前元素结尾的最大子数组和
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // 全局最大和
        int maxSum = nums[0];
        // 当前子数组的最大和（以当前位置结尾）
        int currentSum = nums[0];
        
        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 对于每个位置，我们有两个选择：
            // 1. 将当前元素加入到已存在的子数组中
            // 2. 以当前元素开始一个新的子数组
            // 我们选择产生较大和的那个选项
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            // 更新全局最大和
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * 分治法解法（用于练习分治思想）
     * 时间复杂度：O(n log n)
     */
    public int maxSubArrayDivideConquer(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return divideAndConquer(nums, 0, nums.length - 1);
    }
    
    private int divideAndConquer(int[] nums, int left, int right) {
        // 基本情况：只有一个元素
        if (left == right) {
            return nums[left];
        }
        
        // 找到数组的中点
        int mid = left + (right - left) / 2;
        
        // 递归计算左半部分的最大子数组和
        int leftMax = divideAndConquer(nums, left, mid);
        // 递归计算右半部分的最大子数组和
        int rightMax = divideAndConquer(nums, mid + 1, right);
        // 计算跨越中点的最大子数组和
        int crossMax = maxCrossingSum(nums, left, mid, right);
        
        // 返回三种情况中的最大值
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private int maxCrossingSum(int[] nums, int left, int mid, int right) {
        // 计算左半部分以mid结尾的最大和
        int leftSum = 0;
        int maxLeftSum = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            leftSum += nums[i];
            maxLeftSum = Math.max(maxLeftSum, leftSum);
        }
        
        // 计算右半部分以mid+1开始的最大和
        int rightSum = 0;
        int maxRightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            rightSum += nums[i];
            maxRightSum = Math.max(maxRightSum, rightSum);
        }
        
        // 返回跨越中点的最大子数组和
        return maxLeftSum + maxRightSum;
    }
    
    // 测试方法
    public static void main(String[] args) {
        MaximumSubarray solution = new MaximumSubarray();
        
        // 测试例子
        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("输入: [-2,1,-3,4,-1,2,1,-5,4]");
        System.out.println("动态规划解法: " + solution.maxSubArray(nums1));
        System.out.println("分治法解法: " + solution.maxSubArrayDivideConquer(nums1));
        
        int[] nums2 = {1};
        System.out.println("\n输入: [1]");
        System.out.println("动态规划解法: " + solution.maxSubArray(nums2));
        System.out.println("分治法解法: " + solution.maxSubArrayDivideConquer(nums2));
        
        int[] nums3 = {5, 4, -1, 7, 8};
        System.out.println("\n输入: [5,4,-1,7,8]");
        System.out.println("动态规划解法: " + solution.maxSubArray(nums3));
        System.out.println("分治法解法: " + solution.maxSubArrayDivideConquer(nums3));
    }
} 