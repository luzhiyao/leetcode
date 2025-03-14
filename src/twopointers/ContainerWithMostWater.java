package src.twopointers;

/**
 * LeetCode 11. 盛最多水的容器 (Container With Most Water)
 * 
 * 问题描述：
 * 给定一个长度为 n 的整数数组 height，有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * 
 * 示例：
 * 输入：height = [1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，
 * 容器能够容纳水的最大值为 49（容器底边为 8-1=7，高度为 min(8,7)=7，面积为 7*7=49）。
 * 
 * 输入：height = [1,1]
 * 输出：1
 * 
 * 时间复杂度：O(n)，其中 n 是数组 height 的长度
 * 空间复杂度：O(1)
 */
public class ContainerWithMostWater {
    
    /**
     * 双指针解法
     * 
     * 算法思路：
     * 1. 使用两个指针，初始分别指向数组的左右两端
     * 2. 计算当前两个指针所形成的容器的容量
     * 3. 移动较短的那条边对应的指针（因为如果移动较长的边，新容器的高度仍然会受限于较短的边，且宽度变小）
     * 4. 不断更新最大容量，直到两个指针相遇
     * 
     * 算法正确性证明：
     * - 初始状态下，两个指针之间的宽度最大
     * - 在移动过程中，我们总是舍弃了一些可能的状态，但可以证明舍弃的状态不会包含最优解
     * - 当左指针 i 和右指针 j 确定时，容器的容量取决于 min(height[i], height[j]) * (j - i)
     * - 如果 height[i] < height[j]，移动 j 只会使容量更小（宽度减小，高度不变或更低）
     * - 同理，如果 height[i] > height[j]，移动 i 只会使容量更小
     * - 所以我们总是移动指向较短边的指针，以期望找到更高的边
     * 
     * @param height 代表每个位置高度的数组
     * @return 可以容纳的最大水量
     */
    public int maxArea(int[] height) {
        int maxWater = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            // 计算当前容器的水量：宽度 * 高度（取两边较短的那个）
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            int currentWater = width * currentHeight;
            
            // 更新最大水量
            maxWater = Math.max(maxWater, currentWater);
            
            // 移动较短的那边
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxWater;
    }
    
    /**
     * 暴力解法（O(n²)） - 仅用于理解问题
     * 
     * 算法思路：
     * 1. 检查所有可能的垂线对组合
     * 2. 计算每对垂线形成的容器的容量
     * 3. 返回最大容量
     * 
     * 注意：此方法在大型输入下会超时，仅用于教学目的
     */
    public int maxAreaBruteForce(int[] height) {
        int maxWater = 0;
        int n = height.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 计算当前容器的水量
                int width = j - i;
                int currentHeight = Math.min(height[i], height[j]);
                int currentWater = width * currentHeight;
                
                // 更新最大水量
                maxWater = Math.max(maxWater, currentWater);
            }
        }
        
        return maxWater;
    }
    
    /**
     * 计算两个指针位置形成的容器面积
     */
    private int calculateArea(int[] height, int i, int j) {
        return Math.min(height[i], height[j]) * (j - i);
    }
    
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        
        // 测试用例1
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("输入: [1,8,6,2,5,4,8,3,7]");
        System.out.println("输出: " + solution.maxArea(height1));
        System.out.println("期望输出: 49");
        
        // 测试用例2
        int[] height2 = {1, 1};
        System.out.println("\n输入: [1,1]");
        System.out.println("输出: " + solution.maxArea(height2));
        System.out.println("期望输出: 1");
        
        // 测试用例3 - 高度递增
        int[] height3 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("\n输入: [1,2,3,4,5,6,7,8,9]");
        System.out.println("输出: " + solution.maxArea(height3));
        System.out.println("期望输出: 20"); // min(1,9) * 8 = 8 或 min(4,8) * 4 = 16 或 min(5,9) * 4 = 20
        
        // 测试用例4 - 高度递减
        int[] height4 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n输入: [9,8,7,6,5,4,3,2,1]");
        System.out.println("输出: " + solution.maxArea(height4));
        System.out.println("期望输出: 20"); // min(9,1) * 8 = 8 或 min(8,2) * 6 = 12 或 min(9,5) * 4 = 20
    }
} 