package src.greedy;

import java.util.*;

/**
 * LeetCode 621. 任务调度器 (Task Scheduler)
 * 
 * 问题描述：
 * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。
 * 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。
 * 在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
 * 
 * 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
 * 
 * 你需要计算完成所有任务所需要的 最短时间 。
 * 
 * 示例：
 * 输入：tasks = ["A","A","A","B","B","B"], n = 2
 * 输出：8
 * 解释：A -> B -> idle -> A -> B -> idle -> A -> B
 *      1   2    3      4    5     6     7    8
 * 
 * 输入：tasks = ["A","A","A","B","B","B"], n = 0
 * 输出：6
 * 解释：在这种情况下，任务之间不存在冷却时间，所以可以连续执行相同的任务。
 *      A -> A -> A -> B -> B -> B
 *      1   2    3    4    5    6
 * 
 * 输入：tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
 * 输出：16
 * 解释：
 * 一种可能的解决方案是：
 *      A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 * 
 * 时间复杂度：O(n)，其中 n 是任务的总数
 * 空间复杂度：O(1)，存储任务频率只需要固定大小的空间（26个字母）
 */
public class TaskScheduler {
    
    /**
     * 贪心算法解法
     * 
     * 核心思想：
     * 频率最高的任务会决定最小总时间。我们可以将任务安排成多个块，
     * 每个块由 n+1 个槽位组成，每个块填入不同任务，优先填入剩余次数最多的任务。
     */
    public int leastInterval(char[] tasks, int n) {
        // 统计每个任务出现的次数
        int[] frequencies = new int[26];
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }
        
        // 对频率进行排序
        Arrays.sort(frequencies);
        
        // 找出出现次数最多的任务
        int maxFreq = frequencies[25];
        
        // 计算因为冷却时间导致的空闲时间
        // 公式为：(maxFreq - 1) * n
        int idleTime = (maxFreq - 1) * n;
        
        // 从第二高频率开始，每个任务都可以插入到空闲时间中
        for (int i = 24; i >= 0 && frequencies[i] > 0; i--) {
            // 每个任务最多可以占用 maxFreq - 1 个空闲位置
            // 因为最后一次执行任务时不需要考虑冷却时间
            idleTime -= Math.min(maxFreq - 1, frequencies[i]);
        }
        
        // 如果空闲时间为负，说明任务可以无需额外空闲直接执行完
        idleTime = Math.max(0, idleTime);
        
        // 总时间 = 任务数 + 空闲时间
        return tasks.length + idleTime;
    }
    
    /**
     * 数学公式解法
     * 
     * 设出现最多次数的任务出现了 maxFreq 次，有 maxCount 个任务出现了 maxFreq 次。
     * 则完成所有任务的最短时间为：
     * max((maxFreq - 1) * (n + 1) + maxCount, taskCount)
     */
    public int leastIntervalMath(char[] tasks, int n) {
        // 统计每个任务出现的次数
        int[] frequencies = new int[26];
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }
        
        // 找出最高频率
        int maxFreq = 0;
        for (int freq : frequencies) {
            maxFreq = Math.max(maxFreq, freq);
        }
        
        // 计算有多少个任务出现了最高频率
        int maxCount = 0;
        for (int freq : frequencies) {
            if (freq == maxFreq) {
                maxCount++;
            }
        }
        
        // 应用公式
        int result = Math.max(
            (maxFreq - 1) * (n + 1) + maxCount, 
            tasks.length
        );
        
        return result;
    }
    
    /**
     * 使用优先队列和贪心的模拟解法
     */
    public int leastIntervalPQ(char[] tasks, int n) {
        // 统计任务频率
        Map<Character, Integer> counts = new HashMap<>();
        for (char task : tasks) {
            counts.put(task, counts.getOrDefault(task, 0) + 1);
        }
        
        // 使用优先队列（大顶堆）按频率排序
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.addAll(counts.values());
        
        int time = 0;
        // 当队列不为空时，表示还有任务需要处理
        while (!pq.isEmpty()) {
            int cycle = n + 1;  // 一个周期的长度
            List<Integer> temp = new ArrayList<>(); // 存储一个周期内处理后的任务
            
            // 处理一个周期
            while (cycle > 0 && !pq.isEmpty()) {
                int freq = pq.poll(); // 取出频率最高的任务
                if (freq > 1) {
                    temp.add(freq - 1); // 如果任务还有剩余，保存起来
                }
                time++;
                cycle--;
            }
            
            // 把剩余次数大于0的任务重新加入队列
            pq.addAll(temp);
            
            // 如果队列为空，所有任务都完成了
            if (pq.isEmpty()) {
                break;
            }
            
            // 如果一个周期未满，但队列不为空，需要等待冷却时间
            time += cycle;
        }
        
        return time;
    }
    
    // 测试方法
    public static void main(String[] args) {
        TaskScheduler solution = new TaskScheduler();
        
        // 测试用例1
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        System.out.println("测试用例1: tasks = " + Arrays.toString(tasks1) + ", n = " + n1);
        System.out.println("贪心算法: " + solution.leastInterval(tasks1, n1)); // 预期输出: 8
        System.out.println("数学公式解法: " + solution.leastIntervalMath(tasks1, n1));
        System.out.println("优先队列解法: " + solution.leastIntervalPQ(tasks1, n1));
        
        // 测试用例2
        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        System.out.println("\n测试用例2: tasks = " + Arrays.toString(tasks2) + ", n = " + n2);
        System.out.println("贪心算法: " + solution.leastInterval(tasks2, n2)); // 预期输出: 6
        System.out.println("数学公式解法: " + solution.leastIntervalMath(tasks2, n2));
        System.out.println("优先队列解法: " + solution.leastIntervalPQ(tasks2, n2));
        
        // 测试用例3
        char[] tasks3 = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int n3 = 2;
        System.out.println("\n测试用例3: tasks = " + Arrays.toString(tasks3) + ", n = " + n3);
        System.out.println("贪心算法: " + solution.leastInterval(tasks3, n3)); // 预期输出: 16
        System.out.println("数学公式解法: " + solution.leastIntervalMath(tasks3, n3));
        System.out.println("优先队列解法: " + solution.leastIntervalPQ(tasks3, n3));
        
        // 测试用例4 - 冷却时间较大
        char[] tasks4 = {'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'C', 'D', 'D', 'E'};
        int n4 = 3;
        System.out.println("\n测试用例4: tasks = " + Arrays.toString(tasks4) + ", n = " + n4);
        System.out.println("贪心算法: " + solution.leastInterval(tasks4, n4));
        System.out.println("数学公式解法: " + solution.leastIntervalMath(tasks4, n4));
        System.out.println("优先队列解法: " + solution.leastIntervalPQ(tasks4, n4));
        
        // 测试用例5 - 所有任务类型相同
        char[] tasks5 = {'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A'};
        int n5 = 2;
        System.out.println("\n测试用例5: tasks = " + Arrays.toString(tasks5) + ", n = " + n5);
        System.out.println("贪心算法: " + solution.leastInterval(tasks5, n5));
        System.out.println("数学公式解法: " + solution.leastIntervalMath(tasks5, n5));
        System.out.println("优先队列解法: " + solution.leastIntervalPQ(tasks5, n5));
    }
} 