package src.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 78. 子集 (Subsets)
 * 
 * 问题描述：
 * 给你一个整数数组 nums，数组中的元素互不相同。返回该数组所有可能的子集（幂集）。
 * 解集不能包含重复的子集，你可以按任意顺序返回解集。
 * 
 * 示例1：
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * 
 * 示例2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * 
 * 时间复杂度：O(n * 2^n) - 生成所有子集需要 O(2^n)，复制每个子集需要 O(n)
 * 空间复杂度：O(n * 2^n) - 存储所有子集
 */
public class Subsets {
    
    /**
     * 回溯法
     * 
     * 思路：通过回溯算法，考虑每个元素是否选择，构建所有可能的子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // 将当前构建的子集添加到结果中
        result.add(new ArrayList<>(current));
        
        // 遍历可选元素，从start开始避免重复
        for (int i = start; i < nums.length; i++) {
            // 选择当前元素
            current.add(nums[i]);
            // 递归处理下一个位置，注意下一次从i+1开始
            backtrack(nums, i + 1, current, result);
            // 撤销选择（回溯）
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * 迭代法（二进制表示）
     * 
     * 思路：用二进制数的每一位表示对应元素是否在子集中
     * 例如，对于数组[1,2,3]，二进制000表示空集，二进制111表示完整集合[1,2,3]
     */
    public List<List<Integer>> subsetsIterative(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        
        // 子集数量为2^n
        int subsetCount = 1 << n; // 2^n
        
        // 遍历所有可能的子集
        for (int i = 0; i < subsetCount; i++) {
            List<Integer> subset = new ArrayList<>();
            
            // 检查i的二进制表示中每一位是否为1
            for (int j = 0; j < n; j++) {
                // 如果第j位是1，则包含nums[j]
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            
            result.add(subset);
        }
        
        return result;
    }
    
    /**
     * 增量构造法
     * 
     * 思路：从空集开始，每次考虑一个新元素，将已有的所有子集与该元素组合产生新的子集
     */
    public List<List<Integer>> subsetsIncremental(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 初始包含空集
        result.add(new ArrayList<>());
        
        // 遍历每个元素
        for (int num : nums) {
            int size = result.size();
            
            // 遍历现有的所有子集
            for (int i = 0; i < size; i++) {
                // 复制现有子集
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                // 添加当前元素
                newSubset.add(num);
                // 将新子集添加到结果中
                result.add(newSubset);
            }
        }
        
        return result;
    }
    
    /**
     * 处理包含重复元素的数组 - 子集 II (LeetCode 90)
     * 
     * 思路：先排序，然后在回溯过程中跳过重复元素
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 先排序，使相同元素相邻
        Arrays.sort(nums);
        backtrackWithDup(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackWithDup(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // 将当前构建的子集添加到结果中
        result.add(new ArrayList<>(current));
        
        // 遍历可选元素，从start开始避免重复
        for (int i = start; i < nums.length; i++) {
            // 跳过重复元素，确保相同数字只会被选择一次
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 选择当前元素
            current.add(nums[i]);
            // 递归处理下一个位置
            backtrackWithDup(nums, i + 1, current, result);
            // 撤销选择（回溯）
            current.remove(current.size() - 1);
        }
    }
    
    /**
     * 组合数（LeetCode 77）：找出所有大小为k的子集
     * 
     * 思路：修改回溯算法，只在子集大小为k时添加到结果中
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombine(n, k, 1, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrackCombine(int n, int k, int start, List<Integer> current, List<List<Integer>> result) {
        // 如果当前子集大小达到k，添加到结果中
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // 剪枝优化：如果剩余元素不足以构成大小为k的子集，直接返回
        if (current.size() + (n - start + 1) < k) {
            return;
        }
        
        // 遍历可选元素
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrackCombine(n, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // 打印子集
    private static void printSubsets(List<List<Integer>> subsets) {
        System.out.print("[");
        for (int i = 0; i < subsets.size(); i++) {
            System.out.print(subsets.get(i));
            if (i < subsets.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        Subsets solution = new Subsets();
        
        // 测试用例1
        int[] nums1 = {1, 2, 3};
        System.out.println("示例1 - 输入: " + Arrays.toString(nums1));
        System.out.println("回溯法结果:");
        printSubsets(solution.subsets(nums1));
        
        System.out.println("迭代法(二进制)结果:");
        printSubsets(solution.subsetsIterative(nums1));
        
        System.out.println("增量构造法结果:");
        printSubsets(solution.subsetsIncremental(nums1));
        
        // 测试用例2
        int[] nums2 = {0};
        System.out.println("\n示例2 - 输入: " + Arrays.toString(nums2));
        System.out.println("回溯法结果:");
        printSubsets(solution.subsets(nums2));
        
        // 测试包含重复元素的情况
        int[] nums3 = {1, 2, 2};
        System.out.println("\n含重复元素示例 - 输入: " + Arrays.toString(nums3));
        System.out.println("结果 (处理重复元素):");
        printSubsets(solution.subsetsWithDup(nums3));
        
        // 测试组合数问题
        int n = 4, k = 2;
        System.out.println("\n组合数示例 - 输入: n = " + n + ", k = " + k);
        System.out.println("结果 (大小为k的子集):");
        printSubsets(solution.combine(n, k));
        
        // 测试大数据
        int[] nums4 = {1, 2, 3, 4, 5};
        System.out.println("\n大数据示例 - 输入: " + Arrays.toString(nums4));
        System.out.println("子集数量: " + solution.subsets(nums4).size()); // 应该是 2^5 = 32 个子集
    }
} 