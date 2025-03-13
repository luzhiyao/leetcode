package src.binarysearch;

/**
 * LeetCode 33. 搜索旋转排序数组 (Search in Rotated Sorted Array)
 * 
 * 问题描述：
 * 整数数组 nums 按升序排列，数组中的值互不相同。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标从 0 开始计数）。
 * 
 * 例如，[0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2]。
 * 
 * 给你旋转后的数组 nums 和一个整数 target，如果 nums 中存在这个目标值 target，则返回它的下标，否则返回 -1。
 * 
 * 要求：必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * 
 * 示例1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * 
 * 示例2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * 
 * 示例3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 * 
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 */
public class SearchInRotatedSortedArray {
    
    /**
     * 基础二分搜索解法 - 一次二分查找
     * 思路：在二分查找的同时判断哪部分是有序的，然后在有序部分查找target
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 找到目标值
            if (nums[mid] == target) {
                return mid;
            }
            
            // 左半部分有序
            if (nums[left] <= nums[mid]) {
                // 目标值在左半部分有序区间内
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    // 目标值在右半部分
                    left = mid + 1;
                }
            }
            // 右半部分有序
            else {
                // 目标值在右半部分有序区间内
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    // 目标值在左半部分
                    right = mid - 1;
                }
            }
        }
        
        return -1; // 未找到目标值
    }
    
    /**
     * 两步法：先找旋转点，再二分查找
     * 思路：先找到旋转点，然后确定目标值在哪个部分，再进行标准二分查找
     */
    public int searchTwoStep(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int n = nums.length;
        
        // 先找到旋转点（最小值的索引）
        int rotateIndex = findRotateIndex(nums);
        
        // 数组没有旋转或旋转了n次（相当于没旋转）
        if (rotateIndex == 0) {
            return binarySearch(nums, 0, n - 1, target);
        }
        
        // 确定target在哪个部分
        if (target >= nums[0]) {
            // target在左半部分
            return binarySearch(nums, 0, rotateIndex - 1, target);
        } else {
            // target在右半部分
            return binarySearch(nums, rotateIndex, n - 1, target);
        }
    }
    
    // 查找旋转点（最小值的索引）
    private int findRotateIndex(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // 数组没有旋转的情况
        if (nums[left] < nums[right]) {
            return 0;
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[mid + 1]) {
                return mid + 1;
            }
            
            if (nums[mid] < nums[left]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return 0;
    }
    
    // 标准二分查找
    private int binarySearch(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * 模版解法 - 更易于理解的写法
     */
    public int searchTemplate(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // 判断mid在哪个排序的部分
            boolean midInFirstHalf = nums[mid] >= nums[0];
            boolean targetInFirstHalf = target >= nums[0];
            
            if (midInFirstHalf == targetInFirstHalf) {
                // mid和target在同一部分，可以按普通二分查找处理
                if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // mid和target在不同部分
                if (midInFirstHalf) {
                    // mid在第一部分，target在第二部分
                    left = mid + 1;
                } else {
                    // mid在第二部分，target在第一部分
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();
        
        // 测试用例1
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        System.out.println("示例1 - 基础解法: " + solution.search(nums1, target1)); // 应该输出: 4
        System.out.println("示例1 - 两步法: " + solution.searchTwoStep(nums1, target1)); // 应该输出: 4
        System.out.println("示例1 - 模版解法: " + solution.searchTemplate(nums1, target1)); // 应该输出: 4
        
        // 测试用例2
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int target2 = 3;
        System.out.println("示例2 - 基础解法: " + solution.search(nums2, target2)); // 应该输出: -1
        System.out.println("示例2 - 两步法: " + solution.searchTwoStep(nums2, target2)); // 应该输出: -1
        System.out.println("示例2 - 模版解法: " + solution.searchTemplate(nums2, target2)); // 应该输出: -1
        
        // 测试用例3
        int[] nums3 = {1};
        int target3 = 0;
        System.out.println("示例3 - 基础解法: " + solution.search(nums3, target3)); // 应该输出: -1
        System.out.println("示例3 - 两步法: " + solution.searchTwoStep(nums3, target3)); // 应该输出: -1
        System.out.println("示例3 - 模版解法: " + solution.searchTemplate(nums3, target3)); // 应该输出: -1
        
        // 测试用例4：无旋转
        int[] nums4 = {1, 2, 3, 4, 5};
        int target4 = 3;
        System.out.println("示例4 - 基础解法: " + solution.search(nums4, target4)); // 应该输出: 2
        System.out.println("示例4 - 两步法: " + solution.searchTwoStep(nums4, target4)); // 应该输出: 2
        System.out.println("示例4 - 模版解法: " + solution.searchTemplate(nums4, target4)); // 应该输出: 2
        
        // 测试用例5：目标在旋转点
        int[] nums5 = {4, 5, 6, 7, 0, 1, 2};
        int target5 = 7;
        System.out.println("示例5 - 基础解法: " + solution.search(nums5, target5)); // 应该输出: 3
        System.out.println("示例5 - 两步法: " + solution.searchTwoStep(nums5, target5)); // 应该输出: 3
        System.out.println("示例5 - 模版解法: " + solution.searchTemplate(nums5, target5)); // 应该输出: 3
        
        // 测试用例6：旋转一个元素
        int[] nums6 = {2, 1};
        int target6 = 1;
        System.out.println("示例6 - 基础解法: " + solution.search(nums6, target6)); // 应该输出: 1
        System.out.println("示例6 - 两步法: " + solution.searchTwoStep(nums6, target6)); // 应该输出: 1
        System.out.println("示例6 - 模版解法: " + solution.searchTemplate(nums6, target6)); // 应该输出: 1
    }
} 