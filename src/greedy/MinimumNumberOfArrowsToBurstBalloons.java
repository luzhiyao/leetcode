package src.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 452. 用最少数量的箭引爆气球 (Minimum Number of Arrows to Burst Balloons)
 * 
 * 问题描述：
 * 有一些球形气球贴在一堵墙上。每个气球都标有一个坐标，表示气球在水平方向的开始和结束位置。
 * 你可以沿着 x 轴的任意位置射出一支箭。
 * 如果你在 x 轴上射出一支箭，这支箭会沿着 y 轴向上飞行并且刺破所有在射出位置 x 处的气球。
 * 给定气球的开始和结束坐标，返回能够引爆所有气球的最小箭数。
 * 
 * 示例：
 * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
 * 输出：2
 * 解释：对于该样例，x = 6 可以引爆 [2,8],[1,6] 两个气球，以及 x = 11 可以引爆 [10,16],[7,12] 两个气球。
 * 
 * 输入：points = [[1,2],[3,4],[5,6],[7,8]]
 * 输出：4
 * 解释：每个气球需要射出一支箭。
 * 
 * 输入：points = [[1,2],[2,3],[3,4],[4,5]]
 * 输出：2
 * 解释：可以用一支箭射爆 [1,2] 和 [2,3]，另一支箭射爆 [3,4] 和 [4,5]
 * 
 * 时间复杂度：O(n log n)，其中 n 是气球的数量，排序需要 O(n log n) 的时间
 * 空间复杂度：O(log n)，为排序所需的空间
 */
public class MinimumNumberOfArrowsToBurstBalloons {
    
    /**
     * 贪心算法解法
     * 1. 对气球按照结束位置排序
     * 2. 放置箭在第一个气球的结束位置
     * 3. 移除所有能被该箭刺破的气球
     * 4. 重复步骤2-3，直到所有气球都被刺破
     */
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        // 对气球按照结束位置升序排序
        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
        
        int arrows = 1; // 至少需要一支箭
        int arrowPos = points[0][1]; // 第一支箭的位置在第一个气球的结束位置
        
        // 遍历所有气球
        for (int i = 1; i < points.length; i++) {
            // 如果当前气球的开始位置大于箭的位置，说明需要新的箭
            if (points[i][0] > arrowPos) {
                arrows++;
                arrowPos = points[i][1]; // 更新箭的位置为当前气球的结束位置
            }
        }
        
        return arrows;
    }
    
    /**
     * 另一种贪心解法 - 按照开始位置排序
     */
    public int findMinArrowShots2(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        // 对气球按照开始位置升序排序
        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));
        
        int arrows = 1;
        // 初始化重叠区间为第一个气球
        int end = points[0][1];
        
        for (int i = 1; i < points.length; i++) {
            // 如果当前气球的开始位置大于重叠区间的结束位置
            if (points[i][0] > end) {
                // 需要一支新箭
                arrows++;
                end = points[i][1];
            } else {
                // 更新重叠区间的结束位置（取较小值确保箭能射穿所有重叠的气球）
                end = Math.min(end, points[i][1]);
            }
        }
        
        return arrows;
    }
    
    /**
     * 处理整数溢出的解法（避免整数比较中的潜在问题）
     */
    public int findMinArrowShotsSafe(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        // 使用自定义比较器避免整数溢出问题
        Arrays.sort(points, (a, b) -> {
            if (a[1] < b[1]) return -1;
            if (a[1] > b[1]) return 1;
            return 0;
        });
        
        int arrows = 1;
        int arrowPos = points[0][1];
        
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > arrowPos) {
                arrows++;
                arrowPos = points[i][1];
            }
        }
        
        return arrows;
    }
    
    // 测试方法
    public static void main(String[] args) {
        MinimumNumberOfArrowsToBurstBalloons solution = new MinimumNumberOfArrowsToBurstBalloons();
        
        // 测试用例1
        int[][] points1 = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        System.out.println("测试用例1: " + Arrays.deepToString(points1));
        System.out.println("方法1输出: " + solution.findMinArrowShots(points1)); // 预期输出: 2
        System.out.println("方法2输出: " + solution.findMinArrowShots2(points1));
        System.out.println("安全方法输出: " + solution.findMinArrowShotsSafe(points1));
        
        // 测试用例2
        int[][] points2 = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        System.out.println("\n测试用例2: " + Arrays.deepToString(points2));
        System.out.println("方法1输出: " + solution.findMinArrowShots(points2)); // 预期输出: 4
        System.out.println("方法2输出: " + solution.findMinArrowShots2(points2));
        System.out.println("安全方法输出: " + solution.findMinArrowShotsSafe(points2));
        
        // 测试用例3
        int[][] points3 = {{1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println("\n测试用例3: " + Arrays.deepToString(points3));
        System.out.println("方法1输出: " + solution.findMinArrowShots(points3)); // 预期输出: 2
        System.out.println("方法2输出: " + solution.findMinArrowShots2(points3));
        System.out.println("安全方法输出: " + solution.findMinArrowShotsSafe(points3));
        
        // 测试用例4 - 极端情况：整数范围边界
        int[][] points4 = {{-2147483646, -2147483645}, {2147483646, 2147483647}};
        System.out.println("\n测试用例4: " + Arrays.deepToString(points4));
        System.out.println("方法1输出: " + solution.findMinArrowShots(points4)); // 预期输出: 2
        System.out.println("方法2输出: " + solution.findMinArrowShots2(points4));
        System.out.println("安全方法输出: " + solution.findMinArrowShotsSafe(points4));
        
        // 测试用例5 - 所有气球重叠
        int[][] points5 = {{1, 10}, {2, 9}, {3, 8}, {4, 7}, {5, 6}};
        System.out.println("\n测试用例5: " + Arrays.deepToString(points5));
        System.out.println("方法1输出: " + solution.findMinArrowShots(points5)); // 预期输出: 1
        System.out.println("方法2输出: " + solution.findMinArrowShots2(points5));
        System.out.println("安全方法输出: " + solution.findMinArrowShotsSafe(points5));
    }
} 