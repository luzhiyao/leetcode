package src.binarysearch;

/**
 * LeetCode 704. 二分查找 (Binary Search)
 * 
 * 问题描述：
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target，
 * 写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 * 
 * 示例：
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * 
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 * 
 * 时间复杂度：O(log n)，其中n是数组的长度
 * 空间复杂度：O(1)
 */
public class BinarySearch {
    
    /**
     * 标准二分查找（左闭右闭区间）
     * 搜索区间为 [left, right]
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        // 当left > right时，搜索区间为空，表示未找到target
        while (left <= right) {
            // 计算中间索引（避免整数溢出）
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid; // 找到目标值，返回索引
            } else if (nums[mid] < target) {
                left = mid + 1; // 目标值在右半部分，更新左边界
            } else {
                right = mid - 1; // 目标值在左半部分，更新右边界
            }
        }
        
        return -1; // 未找到目标值
    }
    
    /**
     * 二分查找（左闭右开区间）
     * 搜索区间为 [left, right)
     */
    public int searchLeftClosed(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length;
        
        // 当left == right时，搜索区间为空，表示未找到target
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid; // 注意这里不是mid-1，因为右边界是开区间
            }
        }
        
        return -1;
    }
    
    /**
     * 递归实现的二分查找
     */
    public int searchRecursive(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        // 基本情况：如果搜索区间为空，返回-1
        if (left > right) {
            return -1;
        }
        
        // 计算中间索引
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        } else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
    
    /**
     * 查找第一个等于目标值的元素
     * 适用于数组中可能有重复元素的情况
     */
    public int searchFirstEqual(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 找到目标值，但需要确认是否是第一个
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid; // 是第一个，返回
                } else {
                    right = mid - 1; // 不是第一个，继续在左半部分搜索
                }
            }
        }
        
        return -1;
    }
    
    /**
     * 查找最后一个等于目标值的元素
     * 适用于数组中可能有重复元素的情况
     */
    public int searchLastEqual(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 找到目标值，但需要确认是否是最后一个
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid; // 是最后一个，返回
                } else {
                    left = mid + 1; // 不是最后一个，继续在右半部分搜索
                }
            }
        }
        
        return -1;
    }
    
    // 测试方法
    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();
        
        // 测试用例1
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        
        System.out.println("测试用例1: nums = " + java.util.Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("标准二分查找: " + solution.search(nums1, target1));  // 预期输出: 4
        System.out.println("左闭右开二分查找: " + solution.searchLeftClosed(nums1, target1));
        System.out.println("递归二分查找: " + solution.searchRecursive(nums1, target1));
        
        // 测试用例2
        int[] nums2 = {-1, 0, 3, 5, 9, 12};
        int target2 = 2;
        
        System.out.println("\n测试用例2: nums = " + java.util.Arrays.toString(nums2) + ", target = " + target2);
        System.out.println("标准二分查找: " + solution.search(nums2, target2));  // 预期输出: -1
        System.out.println("左闭右开二分查找: " + solution.searchLeftClosed(nums2, target2));
        System.out.println("递归二分查找: " + solution.searchRecursive(nums2, target2));
        
        // 测试用例3（有重复元素）
        int[] nums3 = {1, 2, 2, 2, 3, 4};
        int target3 = 2;
        
        System.out.println("\n测试用例3（有重复元素）: nums = " + java.util.Arrays.toString(nums3) + ", target = " + target3);
        System.out.println("标准二分查找: " + solution.search(nums3, target3));  // 预期输出: 1, 2 或 3
        System.out.println("第一个等于目标值: " + solution.searchFirstEqual(nums3, target3));  // 预期输出: 1
        System.out.println("最后一个等于目标值: " + solution.searchLastEqual(nums3, target3));  // 预期输出: 3
        
        // 测试用例4（空数组）
        int[] nums4 = {};
        int target4 = 5;
        
        System.out.println("\n测试用例4（空数组）: nums = " + java.util.Arrays.toString(nums4) + ", target = " + target4);
        System.out.println("标准二分查找: " + solution.search(nums4, target4));  // 预期输出: -1
        System.out.println("左闭右开二分查找: " + solution.searchLeftClosed(nums4, target4));
        System.out.println("递归二分查找: " + solution.searchRecursive(nums4, target4));
        
        // 测试用例5（边界情况）
        int[] nums5 = {5};
        int target5 = 5;
        
        System.out.println("\n测试用例5（边界情况）: nums = " + java.util.Arrays.toString(nums5) + ", target = " + target5);
        System.out.println("标准二分查找: " + solution.search(nums5, target5));  // 预期输出: 0
        System.out.println("左闭右开二分查找: " + solution.searchLeftClosed(nums5, target5));
        System.out.println("递归二分查找: " + solution.searchRecursive(nums5, target5));
    }
} 