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
 * 时间复杂度：O(n!)，其中 n 是数组长度
 * 空间复杂度：O(n)，递归的深度最多为 n
 */
public class Permutations {
    
    /**
     * 回溯法生成全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length]; // 跟踪哪些元素已经被使用
        
        backtrack(nums, used, current, result);
        
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // 当前排列已经包含所有元素时，将其添加到结果中
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current)); // 创建当前排列的副本
            return;
        }
        
        // 尝试添加每个未使用的元素
        for (int i = 0; i < nums.length; i++) {
            // 如果当前元素已经被使用，则跳过
            if (used[i]) {
                continue;
            }
            
            // 添加当前元素到排列中
            current.add(nums[i]);
            used[i] = true;
            
            // 递归生成剩余元素的排列
            backtrack(nums, used, current, result);
            
            // 回溯：移除当前元素，尝试下一个选择
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * 通过交换元素生成全排列（另一种实现方式）
     */
    public List<List<Integer>> permuteBySwap(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(nums, 0, result);
        return result;
    }
    
    private void permuteHelper(int[] nums, int start, List<List<Integer>> result) {
        // 当所有位置都已经确定时，添加当前排列到结果中
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }
        
        // 尝试将每个元素放在当前位置
        for (int i = start; i < nums.length; i++) {
            // 交换当前位置和位置i的元素
            swap(nums, start, i);
            
            // 确定后续位置的元素
            permuteHelper(nums, start + 1, result);
            
            // 回溯：恢复原始顺序
            swap(nums, start, i);
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * 打印二维列表
     */
    private static void printResult(List<List<Integer>> result) {
        System.out.print("[");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    // 测试方法
    public static void main(String[] args) {
        Permutations solution = new Permutations();
        
        // 测试例子
        int[] nums1 = {1, 2, 3};
        System.out.println("输入: [1,2,3]");
        System.out.println("使用标准回溯法的输出:");
        printResult(solution.permute(nums1));
        
        System.out.println("\n使用交换法的输出:");
        printResult(solution.permuteBySwap(nums1));
        
        int[] nums2 = {0, 1};
        System.out.println("\n输入: [0,1]");
        System.out.println("使用标准回溯法的输出:");
        printResult(solution.permute(nums2));
    }
} 