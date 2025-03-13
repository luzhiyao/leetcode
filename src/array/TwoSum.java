package src.array;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 1. 两数之和 (Two Sum)
 * 
 * 问题描述：
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 * 
 * 示例：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
 * 
 * 时间复杂度：O(n)，其中 n 是数组的长度
 * 空间复杂度：O(n)
 */
public class TwoSum {
    
    public int[] twoSum(int[] nums, int target) {
        // 创建一个HashMap来存储数组元素和对应的索引
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            // 计算当前元素与目标值的差值
            int complement = target - nums[i];
            
            // 检查差值是否在map中，如果存在，则找到了答案
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            
            // 将当前元素和索引存入map
            map.put(nums[i], i);
        }
        
        // 如果没有找到答案，返回空数组
        return new int[0];
    }
    
    // 测试方法
    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        
        int[] result = solution.twoSum(nums, target);
        System.out.println("[" + result[0] + ", " + result[1] + "]"); // 应该输出 [0, 1]
        
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.println("[" + result2[0] + ", " + result2[1] + "]"); // 应该输出 [1, 2]
    }
} 