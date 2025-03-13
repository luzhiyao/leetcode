package src.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 15. 三数之和 (3Sum)
 * 
 * 问题描述：
 * 给你一个整数数组 nums，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j, i != k, j != k，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0。请返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * 
 * 示例：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * 
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组不满足要求，因为 nums[0] + nums[1] + nums[1] = 0 + 1 + 1 = 2 > 0。
 * 
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组满足要求，因为 nums[0] + nums[1] + nums[2] = 0。
 * 
 * 时间复杂度：O(n²)，其中 n 是数组的长度
 * 空间复杂度：O(1)，不考虑存储答案的空间
 */
public class ThreeSum {
    
    /**
     * 双指针解法
     * 1. 首先对数组进行排序
     * 2. 遍历数组，对于每个元素nums[i]，在其右侧使用双指针查找和为-nums[i]的两个数
     * 3. 注意跳过重复元素以避免重复解
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 对数组进行排序
        Arrays.sort(nums);
        
        // 遍历数组，每次固定一个数作为第一个数
        for (int i = 0; i < nums.length - 2; i++) {
            // 如果当前数大于0，则三数之和必定大于0，结束循环
            if (nums[i] > 0) {
                break;
            }
            
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 双指针查找满足条件的第二个数和第三个数
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum < 0) {
                    // 和小于0，增大左指针
                    left++;
                } else if (sum > 0) {
                    // 和大于0，减小右指针
                    right--;
                } else {
                    // 找到一组解
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复的第二个数
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // 跳过重复的第三个数
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 继续寻找其他解
                    left++;
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * 针对特殊输入的优化版本
     */
    public List<List<Integer>> threeSumOptimized(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 对数组进行排序
        Arrays.sort(nums);
        
        // 特殊情况：如果最小的元素大于0或最大的元素小于0，则不可能有解
        if (nums[0] > 0 || nums[nums.length - 1] < 0) {
            return result;
        }
        
        // 特殊情况：如果有3个0，则添加[0,0,0]为一个解
        if (nums[0] == 0 && nums[nums.length - 1] == 0) {
            result.add(Arrays.asList(0, 0, 0));
            return result;
        }
        
        // 正常处理逻辑
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复的第二个数和第三个数
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                }
            }
        }
        
        return result;
    }
    
    // 测试方法
    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();
        
        // 测试例子
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("输入: [-1,0,1,2,-1,-4]");
        System.out.println("输出: " + solution.threeSum(nums1));
        
        int[] nums2 = {0, 1, 1};
        System.out.println("\n输入: [0,1,1]");
        System.out.println("输出: " + solution.threeSum(nums2));
        
        int[] nums3 = {0, 0, 0};
        System.out.println("\n输入: [0,0,0]");
        System.out.println("输出: " + solution.threeSum(nums3));
        
        // 更复杂的测试例子
        int[] nums4 = {-2, -1, -1, 0, 1, 2, 2};
        System.out.println("\n输入: [-2,-1,-1,0,1,2,2]");
        System.out.println("输出: " + solution.threeSum(nums4));
        
        // 性能对比测试
        int[] largeInput = new int[3000];
        Arrays.fill(largeInput, 0);
        
        long startTime = System.currentTimeMillis();
        solution.threeSum(largeInput);
        long endTime = System.currentTimeMillis();
        System.out.println("\n标准版本处理3000个0的耗时: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        solution.threeSumOptimized(largeInput);
        endTime = System.currentTimeMillis();
        System.out.println("优化版本处理3000个0的耗时: " + (endTime - startTime) + "ms");
    }
} 