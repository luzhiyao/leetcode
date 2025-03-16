package src.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 46. 全排列 (Permutations)
 * 
 * 问题描述：
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。你可以按任意顺序返回答案。
 * 
 * 示例：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 
 * 输入：nums = [1]
 * 输出：[[1]]
 * 
 * 时间复杂度：O(n * n!)，其中n是数组长度，共有n!个排列，每个排列需要O(n)的时间来构造
 * 空间复杂度：O(n)，递归调用栈的深度为n
 */
public class Permutations {
    
    /**
     * 回溯算法解法
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // 回溯法生成排列
        backtrack(nums, new ArrayList<>(), result, new boolean[nums.length]);
        return result;
    }
    
    /**
     * 回溯方法
     * @param nums 输入数组
     * @param current 当前排列
     * @param result 存储所有有效排列
     * @param used 标记数字是否已使用
     */
    private void backtrack(int[] nums, List<Integer> current, List<List<Integer>> result, boolean[] used) {
        // 递归终止条件：已经生成一个完整排列
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current)); // 需要创建一个新的列表来保存结果
            return;
        }
        
        // 遍历所有可能的选择
        for (int i = 0; i < nums.length; i++) {
            // 跳过已经使用过的数字
            if (used[i]) {
                continue;
            }
            
            // 做选择
            used[i] = true;
            current.add(nums[i]);
            
            // 递归，继续构建排列
            backtrack(nums, current, result, used);
            
            // 撤销选择（回溯）
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    /**
     * 交换法解决全排列问题
     */
    public List<List<Integer>> permuteBySwapping(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        List<Integer> numsList = new ArrayList<>();
        for (int num : nums) {
            numsList.add(num);
        }
        
        permuteBySwappingHelper(numsList, 0, result);
        return result;
    }
    
    /**
     * 交换法辅助方法
     * @param nums 数字列表
     * @param start 开始交换的位置
     * @param result 存储所有有效排列
     */
    private void permuteBySwappingHelper(List<Integer> nums, int start, List<List<Integer>> result) {
        // 递归终止条件：到达数组末尾
        if (start == nums.size()) {
            result.add(new ArrayList<>(nums));
            return;
        }
        
        // 从start位置开始，依次与后面的元素交换
        for (int i = start; i < nums.size(); i++) {
            // 交换
            swap(nums, start, i);
            
            // 递归下一个位置
            permuteBySwappingHelper(nums, start + 1, result);
            
            // 回溯，恢复原状
            swap(nums, start, i);
        }
    }
    
    /**
     * 交换列表中两个元素的位置
     */
    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }
    
    /**
     * 使用Heap算法生成排列
     * Heap算法是一种生成排列的高效算法
     */
    public List<List<Integer>> permuteHeap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        List<Integer> numsList = new ArrayList<>();
        for (int num : nums) {
            numsList.add(num);
        }
        
        heapPermutation(numsList, numsList.size(), numsList.size(), result);
        return result;
    }
    
    /**
     * Heap算法辅助方法
     * @param nums 数字列表
     * @param size 当前处理的数组大小
     * @param n 原始数组大小
     * @param result 存储所有有效排列
     */
    private void heapPermutation(List<Integer> nums, int size, int n, List<List<Integer>> result) {
        // 如果只剩下一个元素，记录当前排列
        if (size == 1) {
            result.add(new ArrayList<>(nums));
            return;
        }
        
        for (int i = 0; i < size; i++) {
            heapPermutation(nums, size - 1, n, result);
            
            // 如果size是偶数，交换第一个和最后一个元素
            // 如果size是奇数，交换第i个和最后一个元素
            int j = (size % 2 == 1) ? 0 : i;
            swap(nums, j, size - 1);
        }
    }
    
    // 测试方法
    public static void main(String[] args) {
        Permutations solution = new Permutations();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3};
        System.out.println("测试用例1: nums = " + java.util.Arrays.toString(nums1));
        System.out.println("回溯法结果: " + solution.permute(nums1));
        System.out.println("交换法结果: " + solution.permuteBySwapping(nums1));
        System.out.println("Heap算法结果: " + solution.permuteHeap(nums1));
        
        // 测试用例2
        int[] nums2 = {0, 1};
        System.out.println("\n测试用例2: nums = " + java.util.Arrays.toString(nums2));
        System.out.println("回溯法结果: " + solution.permute(nums2));
        System.out.println("交换法结果: " + solution.permuteBySwapping(nums2));
        System.out.println("Heap算法结果: " + solution.permuteHeap(nums2));
        
        // 测试用例3
        int[] nums3 = {1};
        System.out.println("\n测试用例3: nums = " + java.util.Arrays.toString(nums3));
        System.out.println("回溯法结果: " + solution.permute(nums3));
        System.out.println("交换法结果: " + solution.permuteBySwapping(nums3));
        System.out.println("Heap算法结果: " + solution.permuteHeap(nums3));
        
        // 测试用例4
        int[] nums4 = {1, 2, 3, 4};
        System.out.println("\n测试用例4: nums = " + java.util.Arrays.toString(nums4));
        System.out.println("回溯法结果数量: " + solution.permute(nums4).size() + " (预期: 24)");
        System.out.println("交换法结果数量: " + solution.permuteBySwapping(nums4).size() + " (预期: 24)");
        System.out.println("Heap算法结果数量: " + solution.permuteHeap(nums4).size() + " (预期: 24)");
    }
} 