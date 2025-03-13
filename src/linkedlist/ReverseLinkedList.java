package src.linkedlist;

/**
 * LeetCode 206. 反转链表 (Reverse Linked List)
 * 
 * 问题描述：
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * 
 * 示例：
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 * 
 * 时间复杂度：O(n)，其中 n 是链表的长度
 * 空间复杂度：O(1)
 */
public class ReverseLinkedList {
    // 链表节点定义
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
     * 迭代解法
     * 使用三个指针（prev, curr, next）来反转链表
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            // 保存下一个节点
            ListNode nextTemp = curr.next;
            
            // 反转当前节点的指针
            curr.next = prev;
            
            // 移动指针
            prev = curr;
            curr = nextTemp;
        }
        
        // 返回新的头节点
        return prev;
    }
    
    /**
     * 递归解法
     */
    public ListNode reverseListRecursive(ListNode head) {
        // 基本情况: 空链表或只有一个节点
        if (head == null || head.next == null) {
            return head;
        }
        
        // 递归反转剩余部分
        ListNode newHead = reverseListRecursive(head.next);
        
        // 反转当前节点与下一个节点的关系
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
    
    // 辅助方法：创建链表
    private static ListNode createList(int[] values) {
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
    
    // 辅助方法：打印链表
    private static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    // 测试方法
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();
        
        // 测试例子
        int[] values = {1, 2, 3, 4, 5};
        ListNode head = createList(values);
        
        System.out.println("原始链表:");
        printList(head);
        
        // 测试迭代方法
        ListNode reversedIterative = solution.reverseList(head);
        System.out.println("迭代反转后的链表:");
        printList(reversedIterative);
        
        // 重新创建链表以测试递归方法
        head = createList(values);
        ListNode reversedRecursive = solution.reverseListRecursive(head);
        System.out.println("递归反转后的链表:");
        printList(reversedRecursive);
    }
} 