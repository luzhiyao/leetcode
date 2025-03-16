package src.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 15. 三数之和 (3Sum)
 * 
 * 问题描述：
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 * 请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * 
 * 示例：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 
 * 输入：nums = []
 * 输出：[]
 * 
 * 输入：nums = [0]
 * 输出：[]
 * 
 * 时间复杂度：O(n²)，其中n是数组的长度
 * 空间复杂度：O(1)，不考虑存储答案所需的空间
 */
public class ThreeSum {
    
    /**
     * 排序 + 双指针解法
     * 先对数组排序，然后固定一个数，使用双指针寻找剩下两个数
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 排序，便于去重和使用双指针
        Arrays.sort(nums);
        
        int n = nums.length;
        
        // 遍历，固定第一个数
        for (int i = 0; i < n - 2; i++) {
            // 如果第一个数大于0，那么三数之和一定大于0，可以提前结束
            if (nums[i] > 0) {
                break;
            }
            
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 双指针寻找剩下两个数
            int left = i + 1;
            int right = n - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum < 0) {
                    // 和小于0，左指针右移
                    left++;
                } else if (sum > 0) {
                    // 和大于0，右指针左移
                    right--;
                } else {
                    // 找到一个三元组
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复的第二个数
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // 跳过重复的第三个数
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 继续寻找其他三元组
                    left++;
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * 使用哈希表的解法（不是最优）
     * 时间复杂度O(n²)，空间复杂度O(n)
     */
    public List<List<Integer>> threeSumWithHashSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 排序，便于去重
        Arrays.sort(nums);
        
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            // 如果第一个数大于0，那么三数之和一定大于0，可以提前结束
            if (nums[i] > 0) {
                break;
            }
            
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 使用哈希集合查找第三个数
            java.util.Set<Integer> seen = new java.util.HashSet<>();
            
            for (int j = i + 1; j < n; j++) {
                // 跳过重复的第二个数
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                // 计算第三个数的期望值
                int complement = -nums[i] - nums[j];
                
                if (seen.contains(complement)) {
                    result.add(Arrays.asList(nums[i], nums[j], complement));
                    
                    // 跳过接下来重复的第二个数
                    while (j + 1 < n && nums[j] == nums[j + 1]) {
                        j++;
                    }
                }
                
                // 将当前值加入集合
                seen.add(nums[j]);
            }
        }
        
        return result;
    }
    
    // 测试方法
    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();
        
        // 测试用例1
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result1 = solution.threeSum(nums1);
        
        System.out.println("测试用例1: " + Arrays.toString(nums1));
        System.out.println("双指针解法: " + result1);  // 预期输出: [[-1,-1,2],[-1,0,1]]
        System.out.println("哈希表解法: " + solution.threeSumWithHashSet(nums1));
        
        // 测试用例2: 空数组
        int[] nums2 = {};
        List<List<Integer>> result2 = solution.threeSum(nums2);
        
        System.out.println("\n测试用例2: " + Arrays.toString(nums2));
        System.out.println("双指针解法: " + result2);  // 预期输出: []
        System.out.println("哈希表解法: " + solution.threeSumWithHashSet(nums2));
        
        // 测试用例3: 单个元素
        int[] nums3 = {0};
        List<List<Integer>> result3 = solution.threeSum(nums3);
        
        System.out.println("\n测试用例3: " + Arrays.toString(nums3));
        System.out.println("双指针解法: " + result3);  // 预期输出: []
        System.out.println("哈希表解法: " + solution.threeSumWithHashSet(nums3));
        
        // 测试用例4: 全是零
        int[] nums4 = {0, 0, 0, 0};
        List<List<Integer>> result4 = solution.threeSum(nums4);
        
        System.out.println("\n测试用例4: " + Arrays.toString(nums4));
        System.out.println("双指针解法: " + result4);  // 预期输出: [[0,0,0]]
        System.out.println("哈希表解法: " + solution.threeSumWithHashSet(nums4));
        
        // 测试用例5: 复杂情况
        int[] nums5 = {-2, -1, -1, 0, 1, 2, 2};
        List<List<Integer>> result5 = solution.threeSum(nums5);
        
        System.out.println("\n测试用例5: " + Arrays.toString(nums5));
        System.out.println("双指针解法: " + result5);  // 预期输出: [[-2,0,2],[-1,-1,2],[-1,0,1]]
        System.out.println("哈希表解法: " + solution.threeSumWithHashSet(nums5));
    }
} 