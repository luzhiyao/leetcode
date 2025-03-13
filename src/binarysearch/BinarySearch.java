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
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class BinarySearch {
    
    /**
     * 迭代实现的二分查找
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            // 计算中间索引
            // 使用(left + right) / 2可能导致整数溢出
            // 因此使用left + (right - left) / 2
            int mid = left + (right - left) / 2;
            
            // 找到目标值
            if (nums[mid] == target) {
                return mid;
            }
            // 目标在左侧
            else if (nums[mid] > target) {
                right = mid - 1;
            }
            // 目标在右侧
            else {
                left = mid + 1;
            }
        }
        
        // 目标不存在
        return -1;
    }
    
    /**
     * 递归实现的二分查找
     */
    public int searchRecursive(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        // 基本情况
        if (left > right) {
            return -1;
        }
        
        // 计算中间索引
        int mid = left + (right - left) / 2;
        
        // 找到目标值
        if (nums[mid] == target) {
            return mid;
        }
        // 目标在左侧
        else if (nums[mid] > target) {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
        // 目标在右侧
        else {
            return binarySearchRecursive(nums, target, mid + 1, right);
        }
    }
    
    /**
     * 查找第一个等于目标值的元素
     * 用于处理数组中有重复元素的情况
     */
    public int searchFirstEqual(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 找到目标值，但需要确认是否为第一个
                if (mid == 0 || nums[mid - 1] != target) {
                    return mid;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * 查找最后一个等于目标值的元素
     */
    public int searchLastEqual(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 找到目标值，但需要确认是否为最后一个
                if (mid == nums.length - 1 || nums[mid + 1] != target) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            }
        }
        
        return -1;
    }
    
    // 测试方法
    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();
        
        // 测试例子
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        System.out.println("查找 " + target1 + " 的索引: " + solution.search(nums1, target1));
        System.out.println("递归查找 " + target1 + " 的索引: " + solution.searchRecursive(nums1, target1));
        
        int target2 = 2;
        System.out.println("\n查找 " + target2 + " 的索引: " + solution.search(nums1, target2));
        System.out.println("递归查找 " + target2 + " 的索引: " + solution.searchRecursive(nums1, target2));
        
        // 测试查找第一个和最后一个等于目标值的元素
        int[] nums2 = {1, 2, 3, 3, 3, 4, 5};
        int target3 = 3;
        System.out.println("\n第一个等于 " + target3 + " 的索引: " + solution.searchFirstEqual(nums2, target3));
        System.out.println("最后一个等于 " + target3 + " 的索引: " + solution.searchLastEqual(nums2, target3));
    }
} 