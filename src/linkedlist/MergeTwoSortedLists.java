package src.linkedlist;

/**
 * LeetCode 21. 合并两个有序链表 (Merge Two Sorted Lists)
 * 
 * 问题描述：
 * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 
 * 示例1：
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * 
 * 示例2：
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * 
 * 示例3：
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 * 
 * 时间复杂度：O(n+m)，其中n和m分别为两个链表的长度
 * 空间复杂度：O(1)（迭代法）或O(n+m)（递归法，取决于递归调用栈的深度）
 */
public class MergeTwoSortedLists {
    
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
     * 迭代法解决合并两个有序链表
     * 
     * 思路：使用哑节点作为起点，迭代比较两个链表的节点值，将较小的节点添加到结果链表中
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 创建哑节点作为新链表的起点
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        
        // 当两个链表都不为空时，比较并合并
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // 连接剩余的节点（只有一个链表有剩余）
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return dummy.next;
    }
    
    /**
     * 递归解法
     * 
     * 思路：递归比较两个链表头节点，将较小的节点与合并后的其余部分连接
     */
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // 如果有一个链表为空，返回另一个链表
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        
        // 比较两个链表头节点，选择较小的节点作为合并后的头节点
        if (list1.val <= list2.val) {
            // list1的头节点值较小，将list1的头节点与合并后的其余部分连接
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            // list2的头节点值较小，将list2的头节点与合并后的其余部分连接
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }
    
    /**
     * 原地合并（常量空间）
     * 
     * 思路：直接在原链表上操作，不创建新节点
     */
    public ListNode mergeTwoListsInPlace(ListNode list1, ListNode list2) {
        // 如果有一个链表为空，返回另一个链表
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // 确定头节点
        ListNode head;
        if (list1.val <= list2.val) {
            head = list1;
            list1 = list1.next;
        } else {
            head = list2;
            list2 = list2.next;
        }
        
        ListNode current = head;
        
        // 合并剩余节点
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // 连接剩余的节点
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return head;
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
        MergeTwoSortedLists solution = new MergeTwoSortedLists();
        
        // 测试用例1
        int[] values1 = {1, 2, 4};
        int[] values2 = {1, 3, 4};
        ListNode list1 = createList(values1);
        ListNode list2 = createList(values2);
        
        System.out.println("示例1：");
        System.out.print("输入 list1 = ");
        printList(list1);
        System.out.print("输入 list2 = ");
        printList(list2);
        
        System.out.print("迭代法输出：");
        printList(solution.mergeTwoLists(createList(values1), createList(values2)));
        
        System.out.print("递归法输出：");
        printList(solution.mergeTwoListsRecursive(createList(values1), createList(values2)));
        
        System.out.print("原地合并输出：");
        printList(solution.mergeTwoListsInPlace(createList(values1), createList(values2)));
        
        // 测试用例2
        System.out.println("\n示例2：");
        System.out.println("输入 list1 = []");
        System.out.println("输入 list2 = []");
        
        System.out.print("迭代法输出：");
        printList(solution.mergeTwoLists(null, null));
        
        // 测试用例3
        System.out.println("\n示例3：");
        System.out.println("输入 list1 = []");
        System.out.print("输入 list2 = ");
        ListNode list3 = createList(new int[]{0});
        printList(list3);
        
        System.out.print("迭代法输出：");
        printList(solution.mergeTwoLists(null, createList(new int[]{0})));
        
        // 测试用例4：不同长度的链表
        System.out.println("\n示例4：");
        int[] values4 = {1, 3, 5, 7, 9};
        int[] values5 = {2, 4, 6};
        ListNode list4 = createList(values4);
        ListNode list5 = createList(values5);
        
        System.out.print("输入 list1 = ");
        printList(list4);
        System.out.print("输入 list2 = ");
        printList(list5);
        
        System.out.print("迭代法输出：");
        printList(solution.mergeTwoLists(createList(values4), createList(values5)));
        
        // 测试用例5：一个链表为空
        System.out.println("\n示例5：");
        int[] values6 = {1, 3, 5};
        ListNode list6 = createList(values6);
        
        System.out.print("输入 list1 = ");
        printList(list6);
        System.out.println("输入 list2 = null");
        
        System.out.print("迭代法输出：");
        printList(solution.mergeTwoLists(createList(values6), null));
    }
} 