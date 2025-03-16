package src.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 141. 环形链表 (Linked List Cycle)
 * 
 * 问题描述：
 * 给你一个链表的头节点 head ，判断链表中是否有环。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 
 * 示例：
 * 输入：head = [3,2,0,-4]，pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 * 
 * 时间复杂度：O(n)，其中n是链表的长度
 * 空间复杂度：哈希表解法O(n)，快慢指针解法O(1)
 */
public class LinkedListCycle {
    
    // 链表节点定义
    public static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    
    /**
     * 快慢指针解法
     * 使用两个指针，一个快指针（每次移动两步），一个慢指针（每次移动一步）
     * 如果存在环，两个指针最终会相遇
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;          // 慢指针每次移动一步
            fast = fast.next.next;     // 快指针每次移动两步
            
            if (slow == fast) {        // 如果两个指针相遇，说明存在环
                return true;
            }
        }
        
        return false;  // 如果快指针到达链表末尾，说明不存在环
    }
    
    /**
     * 哈希表解法
     * 使用一个哈希集合记录已经访问过的节点
     * 如果遇到已经访问过的节点，说明存在环
     */
    public boolean hasCycleHashSet(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        
        ListNode current = head;
        while (current != null) {
            if (visited.contains(current)) {
                return true;  // 如果当前节点已经访问过，说明存在环
            }
            
            visited.add(current);  // 将当前节点加入已访问集合
            current = current.next;
        }
        
        return false;  // 如果遍历完整个链表，说明不存在环
    }
    
    /**
     * 计算环的长度（如果存在环）
     */
    public int cycleLength(ListNode head) {
        if (head == null || head.next == null) {
            return 0;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // 检测是否存在环
        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        // 如果不存在环，返回0
        if (!hasCycle) {
            return 0;
        }
        
        // 计算环的长度
        int length = 1;
        fast = slow.next;
        
        while (fast != slow) {
            fast = fast.next;
            length++;
        }
        
        return length;
    }
    
    /**
     * 找到环的入口节点（如果存在环）
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // 检测是否存在环
        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        // 如果不存在环，返回null
        if (!hasCycle) {
            return null;
        }
        
        // 找到环的入口
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    // 测试方法
    public static void main(String[] args) {
        LinkedListCycle solution = new LinkedListCycle();
        
        // 创建一个有环的链表：3 -> 2 -> 0 -> -4 -> 2（环）
        ListNode head = new ListNode(3);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(0);
        ListNode fourth = new ListNode(-4);
        
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = second;  // 创建环，连接回第二个节点
        
        // 测试是否存在环
        System.out.println("快慢指针法检测环: " + solution.hasCycle(head));
        System.out.println("哈希表法检测环: " + solution.hasCycleHashSet(head));
        
        // 如果存在环，计算环的长度
        int length = solution.cycleLength(head);
        System.out.println("环的长度: " + length);
        
        // 如果存在环，找到环的入口节点
        ListNode entranceNode = solution.detectCycle(head);
        if (entranceNode != null) {
            System.out.println("环的入口节点值: " + entranceNode.val);
        }
        
        // 创建一个没有环的链表
        ListNode noLoop = new ListNode(1);
        noLoop.next = new ListNode(2);
        noLoop.next.next = new ListNode(3);
        noLoop.next.next.next = new ListNode(4);
        
        System.out.println("\n无环链表测试:");
        System.out.println("快慢指针法检测环: " + solution.hasCycle(noLoop));
        System.out.println("哈希表法检测环: " + solution.hasCycleHashSet(noLoop));
    }
} 