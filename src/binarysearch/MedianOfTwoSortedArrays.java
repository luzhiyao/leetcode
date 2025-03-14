package src.binarysearch;

/**
 * LeetCode 4. 寻找两个正序数组的中位数 (Median of Two Sorted Arrays)
 * 
 * 问题描述：
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 * 请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * 
 * 示例：
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * 
 * 时间复杂度：O(log(min(m,n)))
 * 空间复杂度：O(1)
 */
public class MedianOfTwoSortedArrays {
    
    /**
     * 二分查找法求中位数
     * 
     * 核心思想：
     * 1. 中位数将一个排序数组分为两个等长的部分（或左边比右边多一个元素）
     * 2. 我们可以将这个问题转化为在两个排序数组中找到一个分割线，使得：
     *    - 分割线左边的所有元素 <= 分割线右边的所有元素
     *    - 分割线左边的元素个数等于右边的元素个数（或左边比右边多一个元素）
     * 3. 使用二分查找来找到这个分割线
     * 
     * 算法步骤：
     * 1. 确保 nums1 的长度小于等于 nums2 的长度，简化处理
     * 2. 在较短的数组 nums1 中进行二分查找，确定分割线的位置
     * 3. 根据分割线位置计算中位数
     * 
     * @param nums1 第一个排序数组
     * @param nums2 第二个排序数组
     * @return 两个数组的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保 nums1 的长度小于等于 nums2 的长度
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int x = nums1.length;
        int y = nums2.length;
        
        // 在 nums1 上二分查找
        int low = 0;
        int high = x;
        
        while (low <= high) {
            // nums1 的分割线位置
            int partitionX = (low + high) / 2;
            // nums2 的分割线位置
            int partitionY = (x + y + 1) / 2 - partitionX;
            
            // 分割线左边的最大值和右边的最小值
            int maxX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int maxY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            
            int minX = (partitionX == x) ? Integer.MAX_VALUE : nums1[partitionX];
            int minY = (partitionY == y) ? Integer.MAX_VALUE : nums2[partitionY];
            
            // 判断是否找到了正确的分割线
            if (maxX <= minY && maxY <= minX) {
                // 找到分割线，计算中位数
                if ((x + y) % 2 == 0) {
                    // 偶数个元素，中位数是两个中间值的平均值
                    return (Math.max(maxX, maxY) + Math.min(minX, minY)) / 2.0;
                } else {
                    // 奇数个元素，中位数是左半部分的最大值
                    return Math.max(maxX, maxY);
                }
            } else if (maxX > minY) {
                // 如果 nums1 左半部分的最大值大于 nums2 右半部分的最小值
                // 说明 nums1 分割线需要左移
                high = partitionX - 1;
            } else {
                // 否则 nums1 分割线需要右移
                low = partitionX + 1;
            }
        }
        
        // 理论上不会到达这里
        throw new IllegalArgumentException("输入数组不合法");
    }
    
    /**
     * 合并数组法 - O(m+n) 
     * 
     * 此方法不满足题目要求的 O(log(m+n)) 时间复杂度，
     * 但相对更直观，适合理解中位数的概念。
     * 
     * 算法思路：
     * 1. 合并两个排序数组为一个有序数组
     * 2. 找出合并后数组的中位数
     * 
     * @param nums1 第一个排序数组
     * @param nums2 第二个排序数组
     * @return 两个数组的中位数
     */
    public double findMedianSortedArraysMerge(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];
        
        // 合并两个排序数组
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        // 处理剩余元素
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        
        // 计算中位数
        int mid = (m + n) / 2;
        if ((m + n) % 2 == 0) {
            // 偶数个元素
            return (merged[mid - 1] + merged[mid]) / 2.0;
        } else {
            // 奇数个元素
            return merged[mid];
        }
    }
    
    /**
     * 优化的合并方法 - O(m+n) 但不需要额外空间
     * 
     * 此方法不满足题目要求的 O(log(m+n)) 时间复杂度，
     * 但它比完全合并方法更高效，因为只需要找到中间位置的元素。
     * 
     * 算法思路：
     * 1. 遍历两个数组，直到到达中间位置
     * 2. 记录中间位置的一个或两个元素（取决于总长度的奇偶性）
     * 
     * @param nums1 第一个排序数组
     * @param nums2 第二个排序数组
     * @return 两个数组的中位数
     */
    public double findMedianSortedArraysOptimized(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;
        
        int middle1 = 0, middle2 = 0;
        int i = 0, j = 0, count = 0;
        
        // 找到中间一个或两个元素
        while (count <= len / 2) {
            // 记录前一个中间值（对于偶数长度数组需要）
            middle1 = middle2;
            
            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                middle2 = nums1[i++];
            } else {
                middle2 = nums2[j++];
            }
            
            count++;
        }
        
        // 根据总长度的奇偶性返回中位数
        if (len % 2 == 0) {
            return (middle1 + middle2) / 2.0;
        } else {
            return middle2;
        }
    }
    
    public static void main(String[] args) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        
        // 测试用例1
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("输入: nums1 = [1,3], nums2 = [2]");
        System.out.println("二分查找法输出: " + solution.findMedianSortedArrays(nums1, nums2));
        System.out.println("合并数组法输出: " + solution.findMedianSortedArraysMerge(nums1, nums2));
        System.out.println("优化合并法输出: " + solution.findMedianSortedArraysOptimized(nums1, nums2));
        System.out.println("期望输出: 2.0");
        
        // 测试用例2
        int[] nums3 = {1, 2};
        int[] nums4 = {3, 4};
        System.out.println("\n输入: nums1 = [1,2], nums2 = [3,4]");
        System.out.println("二分查找法输出: " + solution.findMedianSortedArrays(nums3, nums4));
        System.out.println("合并数组法输出: " + solution.findMedianSortedArraysMerge(nums3, nums4));
        System.out.println("优化合并法输出: " + solution.findMedianSortedArraysOptimized(nums3, nums4));
        System.out.println("期望输出: 2.5");
        
        // 测试用例3 - 空数组
        int[] nums5 = {};
        int[] nums6 = {1};
        System.out.println("\n输入: nums1 = [], nums2 = [1]");
        System.out.println("二分查找法输出: " + solution.findMedianSortedArrays(nums5, nums6));
        System.out.println("合并数组法输出: " + solution.findMedianSortedArraysMerge(nums5, nums6));
        System.out.println("优化合并法输出: " + solution.findMedianSortedArraysOptimized(nums5, nums6));
        System.out.println("期望输出: 1.0");
        
        // 测试用例4 - 不相交的数组
        int[] nums7 = {1, 2, 3};
        int[] nums8 = {4, 5, 6};
        System.out.println("\n输入: nums1 = [1,2,3], nums2 = [4,5,6]");
        System.out.println("二分查找法输出: " + solution.findMedianSortedArrays(nums7, nums8));
        System.out.println("合并数组法输出: " + solution.findMedianSortedArraysMerge(nums7, nums8));
        System.out.println("优化合并法输出: " + solution.findMedianSortedArraysOptimized(nums7, nums8));
        System.out.println("期望输出: 3.5");
    }
} 