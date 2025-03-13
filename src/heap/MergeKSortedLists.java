package src.heap;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * LeetCode 23. 合并K个升序链表 (Merge k Sorted Lists)
 * 
 * 问题描述：
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * 
 * 示例1：
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到：
 * 1->1->2->3->4->4->5->6
 * 
 * 示例2：
 * 输入：lists = []
 * 输出：[]
 * 
 * 示例3：
 * 输入：lists = [[]]
 * 输出：[]
 * 
 * 方法1: 优先队列（小顶堆）- 时间复杂度O(N log k)，空间复杂度O(k)
 * 方法2: 分治法 - 时间复杂度O(N log k)，空间复杂度O(log k)
 * 方法3: 顺序合并 - 时间复杂度O(kN)，空间复杂度O(1)
 */
public class MergeKSortedLists {
    
    /**
     * 链表节点定义
     */
    public static class ListNode {
        int val;
        ListNode next;
        
        ListNode() {}
        
        ListNode(int val) {
            this.val = val;
        }
        
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    /**
     * 方法1：使用优先队列（小顶堆）
     * 
     * 思路：将k个链表的头节点都加入小顶堆中，每次取出最小的节点加入结果链表，
     * 然后将该节点的下一个节点（如果有）加入堆中，重复此过程直到堆为空。
     * 
     * 时间复杂度：O(N log k)，其中N是所有节点的数量，k是链表数量
     * 空间复杂度：O(k)
     */
    public ListNode mergeKListsWithHeap(ListNode[] lists) {
        // 处理边界情况
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // 创建小顶堆，按节点值排序
        PriorityQueue<ListNode> heap = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.val)
        );
        
        // 将所有链表的头节点加入堆
        for (ListNode head : lists) {
            if (head != null) {
                heap.offer(head);
            }
        }
        
        // 创建哑节点作为结果链表的起点
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // 不断从堆中取出最小节点，并将其下一个节点加入堆
        while (!heap.isEmpty()) {
            ListNode minNode = heap.poll();
            current.next = minNode;
            current = current.next;
            
            if (minNode.next != null) {
                heap.offer(minNode.next);
            }
        }
        
        return dummy.next;
    }
    
    /**
     * 方法2：分治法
     * 
     * 思路：将k个链表分成两半，分别合并每部分的链表，然后将两部分合并的结果再合并。
     * 
     * 时间复杂度：O(N log k)，其中N是所有节点的数量，k是链表数量
     * 空间复杂度：O(log k)，递归栈的空间
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        return mergeLists(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeLists(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        
        if (start + 1 == end) {
            return mergeTwoLists(lists[start], lists[end]);
        }
        
        int mid = start + (end - start) / 2;
        ListNode left = mergeLists(lists, start, mid);
        ListNode right = mergeLists(lists, mid + 1, end);
        
        return mergeTwoLists(left, right);
    }
    
    /**
     * 方法3：顺序合并
     * 
     * 思路：从前往后，依次将每个链表与结果链表合并。
     * 
     * 时间复杂度：O(kN)，其中N是所有节点的数量，k是链表数量
     * 空间复杂度：O(1)
     */
    public ListNode mergeKListsSequential(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode result = null;
        for (ListNode list : lists) {
            result = mergeTwoLists(result, list);
        }
        
        return result;
    }
    
    /**
     * 合并两个有序链表（与LeetCode 21相同）
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 创建哑节点作为新链表的起点
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // 当两个链表都不为空时，比较并合并
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // 连接剩余的节点
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        
        return dummy.next;
    }
    
    /**
     * 创建链表
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * 打印链表
     */
    public static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(",");
            }
            current = current.next;
        }
        
        sb.append("]");
        System.out.println(sb.toString());
    }
    
    public static void main(String[] args) {
        MergeKSortedLists solution = new MergeKSortedLists();
        
        // 测试用例1
        ListNode[] lists1 = new ListNode[3];
        lists1[0] = createList(new int[]{1, 4, 5});
        lists1[1] = createList(new int[]{1, 3, 4});
        lists1[2] = createList(new int[]{2, 6});
        
        System.out.println("示例1：");
        System.out.println("输入链表：");
        for (int i = 0; i < lists1.length; i++) {
            System.out.print("List " + (i + 1) + ": ");
            printList(lists1[i]);
        }
        
        System.out.print("使用优先队列的输出：");
        ListNode result1Heap = solution.mergeKListsWithHeap(lists1.clone());
        printList(result1Heap);
        
        System.out.print("使用分治法的输出：");
        ListNode result1Divide = solution.mergeKLists(lists1.clone());
        printList(result1Divide);
        
        System.out.print("使用顺序合并的输出：");
        ListNode result1Sequential = solution.mergeKListsSequential(lists1.clone());
        printList(result1Sequential);
        
        // 测试用例2：空链表数组
        ListNode[] lists2 = new ListNode[0];
        
        System.out.println("\n示例2：");
        System.out.println("输入：空链表数组");
        
        System.out.print("使用优先队列的输出：");
        ListNode result2Heap = solution.mergeKListsWithHeap(lists2);
        printList(result2Heap);
        
        System.out.print("使用分治法的输出：");
        ListNode result2Divide = solution.mergeKLists(lists2);
        printList(result2Divide);
        
        System.out.print("使用顺序合并的输出：");
        ListNode result2Sequential = solution.mergeKListsSequential(lists2);
        printList(result2Sequential);
        
        // 测试用例3：只包含一个空链表的数组
        ListNode[] lists3 = new ListNode[1];
        lists3[0] = null;
        
        System.out.println("\n示例3：");
        System.out.println("输入：包含一个空链表的数组");
        
        System.out.print("使用优先队列的输出：");
        ListNode result3Heap = solution.mergeKListsWithHeap(lists3);
        printList(result3Heap);
        
        System.out.print("使用分治法的输出：");
        ListNode result3Divide = solution.mergeKLists(lists3);
        printList(result3Divide);
        
        System.out.print("使用顺序合并的输出：");
        ListNode result3Sequential = solution.mergeKListsSequential(lists3);
        printList(result3Sequential);
        
        // 测试用例4：链表长度不一
        ListNode[] lists4 = new ListNode[3];
        lists4[0] = createList(new int[]{1});
        lists4[1] = createList(new int[]{0, 2, 5});
        lists4[2] = createList(new int[]{7, 8, 9, 10});
        
        System.out.println("\n示例4：");
        System.out.println("输入链表：");
        for (int i = 0; i < lists4.length; i++) {
            System.out.print("List " + (i + 1) + ": ");
            printList(lists4[i]);
        }
        
        System.out.print("使用优先队列的输出：");
        ListNode result4Heap = solution.mergeKListsWithHeap(lists4.clone());
        printList(result4Heap);
        
        System.out.print("使用分治法的输出：");
        ListNode result4Divide = solution.mergeKLists(lists4.clone());
        printList(result4Divide);
        
        System.out.print("使用顺序合并的输出：");
        ListNode result4Sequential = solution.mergeKListsSequential(lists4.clone());
        printList(result4Sequential);
        
        // 测试用例5：特殊情况-大量链表
        int k = 10;
        ListNode[] lists5 = new ListNode[k];
        for (int i = 0; i < k; i++) {
            lists5[i] = createList(new int[]{i, i + k});
        }
        
        System.out.println("\n示例5：");
        System.out.println("输入：" + k + "个链表");
        
        System.out.print("使用优先队列的输出：");
        ListNode result5Heap = solution.mergeKListsWithHeap(lists5.clone());
        printList(result5Heap);
        
        System.out.print("使用分治法的输出：");
        ListNode result5Divide = solution.mergeKLists(lists5.clone());
        printList(result5Divide);
        
        System.out.print("使用顺序合并的输出：");
        ListNode result5Sequential = solution.mergeKListsSequential(lists5.clone());
        printList(result5Sequential);
    }
} 